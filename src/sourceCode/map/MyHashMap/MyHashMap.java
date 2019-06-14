package sourceCode.map.MyHashMap;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MyHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {

	private static final long serialVersionUID = 2739908726344019121L;
	// AbstractMap 抽象类中的包内部方法
	transient Set<K> keySet;
	transient Collection<V> values;

	// 系统默认初始容量
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

	// 系统默认最大容量
	static final int MAXIMUM_CAPACITY = 1 << 30;

	// 系统默认负载因子
	static final float DEFAULT_LOAD_FACTOR = 0.75f;

	// 当链表长度大于TREEIFY_THRESHOLD（值为8）时会转换为红黑树来提高查询效率。
	static final int TREEIFY_THRESHOLD = 8;

	// 由树转换成链表的阈值UNTREEIFY_THRESHOLD
	static final int UNTREEIFY_THRESHOLD = 6;
	// 树的最小的容量
	static final int MIN_TREEIFY_CAPACITY = 64;

	static class Node<K, V> implements Map.Entry<K, V> {
		final int hash;
		final K key;
		V value;
		Node<K, V> next;

		Node(int hash, K key, V value, Node<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public final K getKey() {
			return key;
		}

		public final V getValue() {
			return value;
		}

		public final String toString() {
			return key + "=" + value;
		}

		public final int hashCode() {
			return Objects.hashCode(key) ^ Objects.hashCode(value);
		}

		public final V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		public final boolean equals(Object o) {
			if (o == this)
				return true;
			// 判断o是否是Entry类型
			if (o instanceof Map.Entry) {
				Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
				if (Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue()))
					return true;
			}
			return false;
		}
	}

	// 得到元素存放数组中的位置
	static final int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}
	
	public static void main(String[] args) {
		tableSizeFor(20);
	}

	// 返回给定目标容量的两次幂。
	// 该方法保证总是返回大于cap并且是2的倍数的值，比如传入999 返回1024
	static final int tableSizeFor(int cap) {
		int n = cap - 1;
		System.out.println(Integer.toBinaryString(n));
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	// 存储元素的数组
	transient Node<K, V>[] table;

	// 拥有缓存的entryset()。请注意，是用抽象的领域对于keyset()和values()。 机翻
	transient Set<Map.Entry<K, V>> entrySet;

	transient int size;// 元素个数

	transient int modCount;// 操作数

	// 临界值 当实际大小(容量*填充因子)超过临界值时，会进行扩容
	int threshold;// 所能容纳的key-value对极限

	final float loadFactor;// 哈希表的加载因子。

	public MyHashMap(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)// 传入容量小于0
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		if (initialCapacity > MAXIMUM_CAPACITY)// 传入容量大于最大容量，取，最大容量
			initialCapacity = MAXIMUM_CAPACITY;
		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
		this.loadFactor = loadFactor;// 哈希表的加载因子。
		this.threshold = tableSizeFor(initialCapacity);// 得到临界值
	}

	public MyHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);// initialCapacity,0.75
	}

	public MyHashMap() {
		this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
	}

	final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
		int s = m.size();
		if (s > 0) {
			if (table == null) { // pre-size
				float ft = ((float) s / loadFactor) + 1.0F;
				int t = ((ft < (float) MAXIMUM_CAPACITY) ? (int) ft : MAXIMUM_CAPACITY);
				if (t > threshold)
					threshold = tableSizeFor(t);
			} else if (s > threshold)
				resize();
			for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
				K key = e.getKey();
				V value = e.getValue();
				putVal(hash(key), key, value, false, evict);
			}
		}
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public V get(Object key) {
		Node<K, V> e;
		return (e = getNode(hash(key), key)) == null ? null : e.value;
	}

	// 读取元素算法
	final Node<K, V> getNode(int hash, Object key) {
		Node<K, V>[] tab;// 存储元素数组
		Node<K, V> first, e; // first
		int n; // 数组长度
		K k;
		// 数组不为空,，长度不为负数 ,first不为null
		//// hash & length-1 定位数组下标
		if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
			// 如果第一个就是需要的值就直接返回
			if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k)))) // always check first
																									// // node
				return first;
			if ((e = first.next) != null) {
				// 如果是树，遍历红黑树复杂度是O(log(n))，得到节点值
				// if (e instanceof TreeNode) {
				// return ((TreeNode<K,V>)first).getTreeNode(hash, key);
				// }
				do {
					// 判断 键 是否相同
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
						return e;
					}
				} while ((e = e.next) != null);
			}
		}
		return null;
	}

	public V put(K key, V value) {
		return putVal(hash(key), key, value, false, true);
	}

	/**
	 * 
	 * @param hash
	 * @param key
	 * @param value
	 * @param onlyIfAbsent
	 *            如果是真的，不要改变现有的值。 当键相同，onlyIfAbsent=true则不修改值
	 * @param evict
	 *            如果为false，则该表处于创建模式。
	 * @return 以前的值
	 */
	@SuppressWarnings("unused")
	final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
		Node<K, V>[] tab;
		Node<K, V> p;
		int n, i;
		// 如果table为空或者长度为0，则进行初始化，初始化在下面进行分析
		if ((tab = table) == null || (n = tab.length) == 0) {
			n = (tab = resize()).length;
		}

		if ((p = tab[i = (n - 1) & hash]) == null) {
			// 数组对应的下标位置没有值,就直接将新值添加到数组
			tab[i] = newNode(hash, key, value, null);
		} else {
			// 数组对应的下标位置为不为空
			Node<K, V> e;// 如果不为空，就说明有节点对应的key与传入的key相同，并且e中就是节点信息
			K k;
			if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
				// 如果已经存在相同的key，则将原来的元素赋值为e
				e = p;
			} else {
				// 以上都不符合的时候进行最常规添加操作：循环链表，想最后插入新节点元素
				// 寻找index位置最后一个节点
				for (int binCount = 0;; ++binCount) {
					if ((e = p.next) == null) {
						// 如果是最后一个节点,就在最后节点的next中放入新节点
						p.next = newNode(hash, key, value, null);
						// 此处应该还有红黑树
						break;
					}
					// 如果在链表的某个环节碰到相同的key则停止循环
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
						break;
					}
				}
				p = e;
			}
			// 已经存在相同的key，只需将新的value赋值给老的value即可
			if (e != null) {
				V oldValue = e.value;
				if (!onlyIfAbsent || oldValue == null) {
					e.value = value;
				}
				return oldValue;
			}
		}
		++modCount;
		// 最终判断容器table的大小+1是否超过table的极限值threshold，如果超过则进行扩容
		// 扩容的方式是newThr=threshold<<1，相当于threshold*2
		if (++size > threshold) {
			resize();
		}
		return null;
	}

	// 新建一个节点
	private Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
		return new Node<>(hash, key, value, next);
	}

	// 扩容兼初始化
	final Node<K, V>[] resize() {
		Node<K, V>[] oldTab = table;
		int oldCap = (oldTab == null) ? 0 : oldTab.length;// 数组长度
		int oldThr = threshold;// 临界值
		int newCap, newThr = 0;
		if (oldCap > 0) {
			// 扩容
			if (oldCap >= MAXIMUM_CAPACITY) {
				// 原数组长度大于最大容量(1073741824) 则将threshold设为Integer.MAX_VALUE=2147483647
				// 接近MAXIMUM_CAPACITY的两倍
				threshold = Integer.MAX_VALUE;
				return oldTab;
			} else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY) {
				// 新数组长度 是原来的2倍，
				// 临界值也扩大为原来2倍
				newThr = oldThr << 1;
			}
		} else if (oldThr > 0) {
			// 如果原来的thredshold大于0则将容量设为原来的thredshold
			// 在第一次带参数初始化时候会有这种情况
			newCap = oldThr;
		} else {
			// 在默认无参数初始化会有这种情况
			newCap = DEFAULT_INITIAL_CAPACITY;// 16
			newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);// 0.75*16=12
		}
		if (newThr == 0) {
			// 如果新 的容量 ==0
			float ft = (float) newCap * loadFactor;// loadFactor 哈希加载因子 默认0.75,可在初始化时传入,16*0.75=12 可以放12个键值对
			newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ? (int) ft : Integer.MAX_VALUE);
		}
		threshold = newThr;// 将临界值设置为新临界值
		@SuppressWarnings({"unchecked" })
		// 扩容
		Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
		table = newTab;
		// 如果原来的table有数据，则将数据复制到新的table中
		if (oldTab != null) {
			// 根据容量进行循环整个数组，将非空元素进行复制
			for (int j = 0; j < oldCap; ++j) {
				Node<K, V> e;
				// 获取数组的第j个元素
				if ((e = oldTab[j]) != null) {
					oldTab[j] = null;// 便于GC
					// 如果链表只有一个，则进行直接赋值
					if (e.next == null)
						// e.hash & (newCap - 1) 确定元素存放位置
						newTab[e.hash & (newCap - 1)] = e;
					// 此处省略红黑树
					else {
						// 进行链表复制
						// 方法比较特殊： 它并没有重新计算元素在数组中的位置
						// 而是采用了 原始位置加原数组长度的方法计算得到位置
						Node<K, V> loHead = null, loTail = null;
						Node<K, V> hiHead = null, hiTail = null;
						Node<K, V> next;
						do {
							/*********************************************/
							/**
							 * 注: e本身就是一个链表的节点，它有 自身的值和next(链表的值)，但是因为next值对节点扩容没有帮助，
							 * 所有在下面讨论中，我近似认为 e是一个只有自身值，而没有next值的元素。
							 */
							/*********************************************/
							next = e.next;
							// 注意：不是(e.hash & (oldCap-1));而是(e.hash & oldCap)

							// (e.hash & oldCap) 得到的是 元素的在数组中的位置是否需要移动,示例如下
							// 示例1：
							// e.hash=10 0000 1010
							// oldCap=16 0001 0000
							// &     = 0 0000 0000 比较高位的第一位 0
							// 结论：元素位置在扩容后数组中的位置没有发生改变

							// 示例2：
							// e.hash=17 0001 0001
							// oldCap=16 0001 0000
							// &     = 1 0001 0000 比较高位的第一位 1
							// 结论：元素位置在扩容后数组中的位置发生了改变，新的下标位置是原下标位置+原数组长度

							// (e.hash & (oldCap-1)) 得到的是下标位置,示例如下
							// e.hash  =10 0000 1010
							// oldCap-1=15 0000 1111
							// &       =10 0000 1010

							// e.hash  =17 0001 0001
							// oldCap-1=15 0000 1111
							// &       = 1 0000 0001

							// 新下标位置
							// e.hash  =17 0001 0001
							// newCap-1=31 0001 1111 newCap=32
							// &       =17 0001 0001 1+oldCap = 1+16

							// 元素在重新计算hash之后，因为n变为2倍，那么n-1的mask范围在高位多1bit(红色)，因此新的index就会发生这样的变化：
							// https://blog.csdn.net/login_sonata/article/details/76598675
							// 0000 0001->0001 0001

							if ((e.hash & oldCap) == 0) {
								// 如果原元素位置没有发生变化
								if (loTail == null)
									loHead = e;// 确定首元素
								// 第一次进入时 e -> aa ; loHead-> aa
								else
									loTail.next = e;
								// 第二次进入时 loTail-> aa ; e -> bb ; loTail.next ->bb;
								// 而loHead和loTail是指向同一块内存的，所以loHead.next 地址为 bb
								// 第三次进入时 loTail-> bb ; e -> cc ; loTail.next 地址为 cc;loHead.next.next = cc
								loTail = e;
								// 第一次进入时 e -> aa ; loTail-> aa loTail指向了和 loHead相同的内存空间
								// 第二次进入时 e -> bb ; loTail-> bb loTail指向了和 loTail.next（loHead.next）相同的内存空间
								// loTail=loTail.next
								// 第三次进入时 e -> cc ; loTail-> cc loTail指向了和 loTail.next(loHead.next.next)相同的内存
							} else {
								// 与上面同理

								if (hiTail == null)
									hiHead = e;
								else
									hiTail.next = e;
								hiTail = e;
							}
						} while ((e = next) != null);// 这一块就是 旧链表迁移新链表
						
						// 总结：1.8中 旧链表迁移新链表 链表元素相对位置没有变化; 实际是对对象的内存地址进行操作
						// 在1.7中 旧链表迁移新链表 如果在新表的数组索引位置相同，则链表元素会倒置
						if (loTail != null) {
							loTail.next = null;// 将链表的尾节点 的next 设置为空
							newTab[j] = loHead;
						}
						if (hiTail != null) {
							hiTail.next = null;// 将链表的尾节点 的next 设置为空
							newTab[j + oldCap] = hiHead;
						}
					}
				}
			}
		}
		return newTab;
	}

	// 删除元素
	public V remove(Object key) {
		Node<K, V> e;
		return (e = removeNode(hash(key), key, null, false, true)) == null ? null : e.value;
	}

	/**
	 * 
	 * @param hash
	 * @param key
	 * @param value
	 * @param matchValue
	 *            if true only remove if value is equal true:值相同时删除
	 * @param movable
	 *            if false do not move other nodes while removing
	 * @return
	 */
	final Node<K, V> removeNode(int hash, Object key, Object value, boolean matchValue, boolean movable) {
		Node<K, V>[] tab;
		Node<K, V> p; // 删除key所在数组的下标位置元素
		int n, index;
		if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) {
			Node<K, V> node = null, e;
			K k;
			V v;
			if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
				// 如果删除元素的key就在数组中
				node = p;
			} else if ((e = p.next) != null) {
				// 查找key所在位置
				do {
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
						node = e;
						break;
					}
					p = e;// 当删除key所在节点时，需要它的前一个节点和后一节节点进行连接 p中存放的就是前一个节点
				} while ((e = e.next) != null);
			}
			if (node != null && (!matchValue || (v = node.value) == value || (value != null && value.equals(v)))) {
				if (node == p) {
					tab[index] = node.next;
				} else {
					p.next = node.next;
				}
				++modCount;
				--size;
				afterNodeRemoval(node);
				return node;
			}
		}
		return null;
	}

	public void clear() {
		Node<K, V>[] tab;
		modCount++;
		if ((tab = table) != null && size > 0) {
			size = 0;
			for (int i = 0; i < tab.length; ++i) {
				tab[i] = null;
			}

		}
	}

	// HahMap中是否包含指定的值
	public boolean containsValue(Object value) {
		Node<K, V>[] tab;
		V v;
		if ((tab = table) != null && size > 0) {
			for (int i = 0; i < tab.length; ++i) {
				for (Node<K, V> e = tab[i]; e != null; e = e.next) {
					if ((v = e.value) == value || (value != null && value.equals(v))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// 得到key的集合
	public Set<K> keySet() {
		Set<K> ks = keySet;
		if (ks == null) {
			ks = new KeySet();
			keySet = ks;
		}
		return ks;
	}

	final class KeySet extends AbstractSet<K> {
		public final int size() {
			return size;
		}

		public final void clear() {
			MyHashMap.this.clear();
		}

		public final Iterator<K> iterator() {
			return new KeyIterator();
		}

		public final boolean contains(Object o) {
			return containsKey(o);
		}

		public final boolean remove(Object key) {
			return removeNode(hash(key), key, null, false, true) != null;
		}

		// 用于 并行控制 1.8新加
		// public final Spliterator<K> spliterator() {
		// return new KeySpliterator<>(HashMap.this, 0, -1, 0, 0);
		// }

		// foreach方法参数是一个Consumer函数式接口
		public final void forEach(Consumer<? super K> action) {
			// 遍历所有的key
			Node<K, V>[] tab;
			if (action == null)
				throw new NullPointerException();
			if (size > 0 && (tab = table) != null) {
				int mc = modCount;
				for (int i = 0; i < tab.length; ++i) {
					for (Node<K, V> e = tab[i]; e != null; e = e.next)
						action.accept(e.key);
				}
				if (modCount != mc)
					throw new ConcurrentModificationException();
			}

		}
	}

	public Collection<V> values() {
		Collection<V> vs = values;
		if (vs == null) {
			//只进来一次
			vs = new Values();
			values = vs;
		}
		return vs;
	}

	final class Values extends AbstractCollection<V> {
		public final int size() {
			return size;
		}

		public final void clear() {
			MyHashMap.this.clear();
		}

		public final Iterator<V> iterator() {
			return new ValueIterator();
		}

		public final boolean contains(Object o) {
			return containsValue(o);
		}

		// public final Spliterator<V> spliterator() {
		// return new ValueSpliterator<>(HashMap.this, 0, -1, 0, 0);
		// }

		public final void forEach(Consumer<? super V> action) {
			// 遍历所有的value
			Node<K, V>[] tab;
			if (action == null)
				throw new NullPointerException();
			if (size > 0 && (tab = table) != null) {
				int mc = modCount;
				for (int i = 0; i < tab.length; ++i) {
					for (Node<K, V> e = tab[i]; e != null; e = e.next)
						action.accept(e.value);
				}
				if (modCount != mc)
					throw new ConcurrentModificationException();
			}
		}
	}

	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> es;
		return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
	}

	final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		public final int size() {
			return size;
		}

		public final void clear() {
			MyHashMap.this.clear();
		}

		public final Iterator<Map.Entry<K, V>> iterator() {
			return new EntryIterator();
		}

		public final boolean contains(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			Object key = e.getKey();
			Node<K, V> candidate = getNode(hash(key), key);
			return candidate != null && candidate.equals(e);
		}

		public final boolean remove(Object o) {
			if (o instanceof Map.Entry) {
				Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
				Object key = e.getKey();
				Object value = e.getValue();
				return removeNode(hash(key), key, value, true, true) != null;
			}
			return false;
		}

		// public final Spliterator<Map.Entry<K, V>> spliterator() {
		// return new EntrySpliterator<>(HashMap.this, 0, -1, 0, 0);
		// }

		public final void forEach(Consumer<? super Map.Entry<K, V>> action) {
			Node<K, V>[] tab;
			if (action == null)
				throw new NullPointerException();
			if (size > 0 && (tab = table) != null) {
				int mc = modCount;
				for (int i = 0; i < tab.length; ++i) {
					for (Node<K, V> e = tab[i]; e != null; e = e.next)
						action.accept(e);
				}
				if (modCount != mc)
					throw new ConcurrentModificationException();
			}
		}
	}

	// 替换元素
	public V replace(K key, V value) {
		Node<K, V> e;
		if ((e = getNode(hash(key), key)) != null) {
			V oldValue = e.value;
			e.value = value;
			afterNodeAccess(e);
			return oldValue;
		}
		return null;
	}

	@Override
	// 代表了一个接受两个输入参数的操作，并且不返回任何结果
	public void forEach(BiConsumer<? super K, ? super V> action) {
		Node<K, V>[] tab;
		if (action == null)
			throw new NullPointerException();
		if (size > 0 && (tab = table) != null) {
			int mc = modCount;
			for (int i = 0; i < tab.length; ++i) {
				for (Node<K, V> e = tab[i]; e != null; e = e.next)
					action.accept(e.key, e.value);
			}
			if (modCount != mc)
				throw new ConcurrentModificationException();
		}
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		MyHashMap<K, V> result;
		try {
			result = (MyHashMap<K, V>) super.clone();
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError(e);
		}
		result.reinitialize();
		result.putMapEntries(this, false);
		return result;

	}

	abstract class HashIterator {
		Node<K, V> next; // next entry to return
		Node<K, V> current; // 当前 entry
		int expectedModCount; // for fast-fail
		int index; // 当前 位置

		HashIterator() {
			expectedModCount = modCount;
			Node<K, V>[] t = table;
			current = next = null;// 当前所在节点
			index = 0;// 下标索引
			if (t != null && size > 0) { // advance to first entry
				do {
				} while (index < t.length && (next = t[index++]) == null);
			}
		}

		public final boolean hasNext() {
			return next != null;
		}

		final Node<K, V> nextNode() {
			Node<K, V>[] t;
			Node<K, V> e = next;
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			if (e == null)
				throw new NoSuchElementException();
			if ((next = (current = e).next) == null && (t = table) != null) {
				do {
					//如果为空 就跳过 查看下一个
				} while (index < t.length && (next = t[index++]) == null);
			}
			return e;
		}

		public final void remove() {
			Node<K, V> p = current;
			if (p == null)
				throw new IllegalStateException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			current = null;
			K key = p.key;
			removeNode(hash(key), key, null, false, false);
			expectedModCount = modCount;
		}
	}

	final class KeyIterator extends HashIterator implements Iterator<K> {
		public final K next() {
			return nextNode().key;
		}
	}

	final class ValueIterator extends HashIterator implements Iterator<V> {
		public final V next() {
			return nextNode().value;
		}
	}

	final class EntryIterator extends HashIterator implements Iterator<Map.Entry<K, V>> {
		public final Map.Entry<K, V> next() {
			return nextNode();
		}
	}
	
    final int capacity() {
        return (table != null) ? table.length :
            (threshold > 0) ? threshold :
						DEFAULT_INITIAL_CAPACITY;
	}

	// Callbacks to allow LinkedHashMap post-actions
	// LinkedHashMap 回调
	void afterNodeAccess(Node<K, V> p) {
	}

	void afterNodeInsertion(boolean evict) {
	}

	void afterNodeRemoval(Node<K, V> p) {
	}

	// 初始化参数
	void reinitialize() {
		table = null;
		entrySet = null;
		keySet = null;
		values = null;
		modCount = 0;
		threshold = 0;
		size = 0;
	}

}
