package sourceCode.set.MyHashSetAndMyLinkedHashSet;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Spliterator;

//默认初始化容量是16
//内部实现都是调用了HashMap
public class MyHashSet<E> extends AbstractSet<E> implements Set<E>, Cloneable, java.io.Serializable {

	private static final long serialVersionUID = -6919405986822376708L;

	// 内部实现是HashMap<key,value>
	private transient HashMap<E, Object> map;

	// 由于HashSet只使用了map的key字段，所以value值都是固定的默认值 PRESENT
	private static final Object PRESENT = new Object();

	public MyHashSet() {
		map = new HashMap<>();
	}

	public MyHashSet(Collection<? extends E> c) {
		map = new HashMap<>(Math.max((int) (c.size() / .75f) + 1, 16));
		addAll(c);
	}

	public MyHashSet(int initialCapacity) {
		map = new HashMap<>(initialCapacity);
	}

	public MyHashSet(int initialCapacity, float loadFactor) {
		map = new HashMap<>(initialCapacity, loadFactor);
	}

	/**
	 * 
	 * @param initialCapacity  初始容量
	 * @param loadFactor	链接哈希 set 的初始加载因子 
	 * @param dummy	没用，就是为了和其他构造方法区分
	 */
	MyHashSet(int initialCapacity, float loadFactor, boolean dummy) {
		//主要用来让子类LinkedHashSet调用
		map = new LinkedHashMap<>(initialCapacity, loadFactor);
	}

	// 迭代器
	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	public boolean add(E e) {
		return map.put(e, PRESENT) == null;
	}

	public boolean remove(Object o) {
		return map.remove(o) == PRESENT;
	}

	public void clear() {
		map.clear();
	}

	/**
	 * Returns a shallow copy of this <tt>HashSet</tt> instance: the elements
	 * themselves are not cloned.
	 *
	 * @return a shallow copy of this set
	 */
	@SuppressWarnings("unchecked")
	public Object clone() {
		try {
			MyHashSet<E> newSet = (MyHashSet<E>) super.clone();
			newSet.map = (HashMap<E, Object>) map.clone();
			return newSet;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
	//提供并行访问接口
	public Spliterator<E> spliterator() {
		// return new HashMap.KeySpliterator<E, Object>(map, 0, -1, 0, 0);
		return null;
	}
}
