package sourceCode.set.MyHashSetAndMyLinkedHashSet;

import java.util.Set;

//默认长度16
//继承自HashSet
//通过调用父类构造器，得到一个LinkedHashMap对象
public class MyLinkedHashSet<E> extends MyHashSet<E> implements Set<E>, Cloneable, java.io.Serializable {

	private static final long serialVersionUID = 6671623289830870965L;

	/**
	 * 
	 * @param initialCapacity
	 *            链接散列集的初始容量
	 * @param loadFactor
	 *            链接哈希集的负载因子
	 */
	public MyLinkedHashSet(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor, true);
	}

	public MyLinkedHashSet(int initialCapacity) {
		super(initialCapacity, .75f, true);
	}

	public MyLinkedHashSet() {
        super(16, .75f, true);
    }
}
