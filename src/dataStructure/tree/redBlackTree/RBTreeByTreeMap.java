package dataStructure.tree.redBlackTree;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * 根据TreeMap源码重写红黑树
 * TreeMap 红黑树 的 实现 相对比较好看懂
 * @author 李雅翔
 * @date 2017年9月29日
 */
public class RBTreeByTreeMap<K extends Comparable<K>, V> extends AbstractMap<K, V> {

	private transient Entry<K, V> root;

	private transient int size = 0;

	// private transient int modCount = 0;
	// 和HashMap红黑树 红黑标志正好相反
	private static final boolean RED = false;
	private static final boolean BLACK = true;

	public V put(K key, V value) {
		Entry<K, V> t = root;
		if (t == null) {
			root = new Entry<>(key, value, null);
			size = 1;
			return null;
		}
		int cmp;// 用于存放比较的大小
		Entry<K, V> parent;
		if (key == null)
			throw new NullPointerException();
		do {
			parent = t;
			cmp = key.compareTo(t.key);
			if (cmp < 0)
				t = t.left;
			else if (cmp > 0)
				t = t.right;
			else
				return t.setValue(value);
		} while (t != null);
		Entry<K, V> e = new Entry<>(key, value, parent);
		if (cmp < 0)
			parent.left = e;
		else
			parent.right = e;
		fixAfterInsertion(e);
		size++;
		return null;
	}

	public V remove(Object key) {
		Entry<K, V> p = getEntry(key);
		if (p == null)
			return null;

		V oldValue = p.value;
		deleteEntry(p);
		return oldValue;
	}

	final Entry<K, V> getEntry(Object key) {
		// Offload comparator-based version for sake of performance
		if (key == null)
			throw new NullPointerException();
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) key;
		Entry<K, V> p = root;
		while (p != null) {
			int cmp = k.compareTo(p.key);
			if (cmp < 0)
				p = p.left;
			else if (cmp > 0)
				p = p.right;
			else
				return p;
		}
		return null;
	}

	private void deleteEntry(Entry<K, V> p) {
		size--;
		if (p.left != null && p.right != null) {

			Entry<K, V> s = successor(p);// 返回后继节点
			// 用后继节点 替换 删除节点
			p.key = s.key;
			p.value = s.value;
			// 将p指向后继节点
			p = s;
		} // p has 2 children

		// 设置更换节点
		Entry<K, V> replacement = (p.left != null ? p.left : p.right);

		if (replacement != null) {
			// replacement = 后继节点的右子
			// P[replacement] <- P[p] 将后继节点的父节点指向 后继节点的右子节点
			replacement.parent = p.parent;
			if (p.parent == null)
				root = replacement;
			else if (p == p.parent.left)
				p.parent.left = replacement;
			else
				p.parent.right = replacement;

			p.left = p.right = p.parent = null;// 便于GC

			if (p.color == BLACK)
				// 如果替换节点是红色，则不会对红黑树的性质造成影响
				fixAfterDeletion(replacement);
		} else if (p.parent == null) {
			// 如果删除的是根节点,根节点直接置空
			root = null;
		} else {
			// replacement = null
			// 有两种情况;1:后继节点没有子节点（叶子） 2.删除节点没有子节点
			if (p.color == BLACK)
				fixAfterDeletion(p);
			// 将p节点从树中删除
			if (p.parent != null) {
				if (p == p.parent.left)
					p.parent.left = null;
				else if (p == p.parent.right)
					p.parent.right = null;
				p.parent = null;
			}
		}
	}

	/**
	 * 返回节点的后继节点
	 */
	static <K, V> Entry<K, V> successor(Entry<K, V> t) {
		if (t == null)
			return null;
		else if (t.right != null) {
			Entry<K, V> p = t.right;
			while (p.left != null)
				p = p.left;
			return p;
		}
		return null;
	}

	/******************** 平衡树 *************************/
	/** From CLR */
	private void rotateLeft(Entry<K, V> p) {
		if (p != null) {
			Entry<K, V> r = p.right;
			p.right = r.left;
			if (r.left != null)
				r.left.parent = p;
			r.parent = p.parent;
			if (p.parent == null)
				root = r;
			else if (p.parent.left == p)
				p.parent.left = r;
			else
				p.parent.right = r;
			r.left = p;
			p.parent = r;
		}
	}

	/** From CLR */
	private void rotateRight(Entry<K, V> p) {
		if (p != null) {
			Entry<K, V> l = p.left;
			p.left = l.right;
			if (l.right != null)
				l.right.parent = p;
			l.parent = p.parent;
			if (p.parent == null)
				root = l;
			else if (p.parent.right == p)
				p.parent.right = l;
			else
				p.parent.left = l;
			l.right = p;
			p.parent = l;
		}
	}

	private void fixAfterInsertion(Entry<K, V> x) {
		x.color = RED;
		while (x != null && x != root && x.parent.color == RED) {
			if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
				Entry<K, V> ppr = rightOf(parentOf(parentOf(x)));
				if (colorOf(ppr) == RED) {
					// 叔叔节点是红色
					setColor(parentOf(x), BLACK);// 父节点设为黑
					setColor(ppr, BLACK);// 叔叔设为黑
					setColor(parentOf(parentOf(x)), RED);// 祖父设为红
					x = parentOf(parentOf(x));// 将节点上移到祖父节点继续修改颜色
				} else {
					if (x == rightOf(parentOf(x))) {
						// x是父节点的右子 case 2
						x = parentOf(x);
						rotateLeft(x);
					}
					// 父节点设为 黑，祖父节点设为红 以祖父节点右旋
					setColor(parentOf(x), BLACK);
					setColor(parentOf(parentOf(x)), RED);
					rotateRight(parentOf(parentOf(x)));
				}
			} else {
				Entry<K, V> y = leftOf(parentOf(parentOf(x)));
				if (colorOf(y) == RED) {
					setColor(parentOf(x), BLACK);
					setColor(y, BLACK);
					setColor(parentOf(parentOf(x)), RED);
					x = parentOf(parentOf(x));
				} else {
					if (x == leftOf(parentOf(x))) {
						x = parentOf(x);
						rotateRight(x);
					}
					setColor(parentOf(x), BLACK);
					setColor(parentOf(parentOf(x)), RED);
					rotateLeft(parentOf(parentOf(x)));
				}
			}
		}
		root.color = BLACK;
	}

	// 删除节点以后重新着色
	private void fixAfterDeletion(Entry<K, V> x) {
		while (x != root && colorOf(x) == BLACK) {
			if (x == leftOf(parentOf(x))) {
				// x是父节点的左子 ， 进入情况 ：
				// 1: 删除节点如果是叶子节点,且他是 父亲的左子
				// 2: 删除节点 只有左子没有右子  
				// 3： 替换节点如果存在
				Entry<K, V> brother = rightOf(parentOf(x));
				if (colorOf(brother) == RED) {
					// case 1
					// 兄弟节点  是红色
					// 兄弟节点设为黑，父节点设为红
					// 以便在 旋转以后与祖父节点颜色不冲突
					setColor(brother, BLACK);
					setColor(parentOf(x), RED);
					rotateLeft(parentOf(x));
					brother =  rightOf(parentOf(x));//重新定位兄弟节点
				}
				if (colorOf(leftOf(brother))  == BLACK &&
                    colorOf(rightOf(brother)) == BLACK) {
					// case 2
					// 兄弟节点 左子是黑色 右子是黑色
					// 兄弟节点设为红色
					setColor(brother, RED);
					x = parentOf(x);//将节点上移 继续修改
				}else {
					if (colorOf(rightOf(brother)) == BLACK) {
						//case 3 
						// 兄弟节点   左子是红色 右子是黑色
						//兄弟节点 左子设为黑色，兄弟节点设为红色 对兄弟节点右旋
						setColor(leftOf(brother), BLACK);
                        setColor(brother, RED);
                        rotateRight(brother);
                        brother = rightOf(parentOf(x));//重新定位兄弟节点
					}
					// case 4 
					// 兄弟节点   左子任意色 右子是红色
					// 将兄弟节点颜色设为 父节点颜色 --> 因为 兄弟节点是父节点的 右子，左旋以后，兄弟节点变为父节点
					// 保证了 旋转以后兄弟节点 与 祖父节点 颜色不冲突
					// 父节点设为 黑  ，兄弟节点右子设为 黑色
					setColor(brother, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(brother), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;//直接返回
				}
			}else {//与上面处理相同
				// x是父节点的右子 ， 进入情况 ：
				// 1: 删除节点 只有右子没有左子
				// 2. 上面处理完后，将节点上移继续处理
				Entry<K,V> brother = leftOf(parentOf(x));

                if (colorOf(brother) == RED) {
                    setColor(brother, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    brother = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(brother)) == BLACK &&
                    colorOf(leftOf(brother)) == BLACK) {
                    setColor(brother, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(brother)) == BLACK) {
                        setColor(rightOf(brother), BLACK);
                        setColor(brother, RED);
                        rotateLeft(brother);
                        brother = leftOf(parentOf(x));
                    }
                    setColor(brother, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(brother), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
			}
		}
		setColor(x, BLACK);
	}

	private static <K, V> boolean colorOf(Entry<K, V> p) {
		return (p == null ? BLACK : p.color);
	}

	private static <K, V> Entry<K, V> parentOf(Entry<K, V> p) {
		return (p == null ? null : p.parent);
	}

	private static <K, V> void setColor(Entry<K, V> p, boolean c) {
		if (p != null)
			p.color = c;
	}

	private static <K, V> Entry<K, V> leftOf(Entry<K, V> p) {
		return (p == null) ? null : p.left;
	}

	private static <K, V> Entry<K, V> rightOf(Entry<K, V> p) {
		return (p == null) ? null : p.right;
	}

	/****************** 内部类 *************************/
	static final class Entry<K, V> {
		K key;
		V value;
		Entry<K, V> left;
		Entry<K, V> right;
		Entry<K, V> parent;
		boolean color = BLACK;

		/**
		 * Make a new cell with given key, value, and parent, and with {@code null}
		 * child links, and BLACK color.
		 */
		Entry(K key, V value, Entry<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		/**
		 * Returns the key.
		 *
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns the value associated with the key.
		 *
		 * @return the value associated with the key
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Replaces the value currently associated with the key with the given value.
		 *
		 * @return the value associated with the key before this method was called
		 */
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof Entry))
				return false;
			Entry<?, ?> e = (Entry<?, ?>) o;

			return valEquals(key, e.getKey()) && valEquals(value, e.getValue());
		}

		public int hashCode() {
			int keyHash = (key == null ? 0 : key.hashCode());
			int valueHash = (value == null ? 0 : value.hashCode());
			return keyHash ^ valueHash;
		}

		public String toString() {
			return key + "=" + value + "=" + (color ? "黑" : "红");
		}
	}

	static final boolean valEquals(Object o1, Object o2) {
		return (o1 == null ? o2 == null : o1.equals(o2));
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return null;
	}

}
