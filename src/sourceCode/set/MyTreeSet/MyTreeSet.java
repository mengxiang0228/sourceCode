package sourceCode.set.MyTreeSet;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeMap;

public class MyTreeSet<E> extends AbstractSet<E> implements NavigableSet<E>, Cloneable, java.io.Serializable {

	private static final long serialVersionUID = 40468661510943500L;

	// NavigableMap 扩展了SortedMap 本质上添加了搜索选项到接口
	private transient NavigableMap<E, Object> m;

	private static final Object PRESENT = new Object();

	MyTreeSet(NavigableMap<E, Object> m) {
		this.m = m;
	}

	public MyTreeSet() {
		this(new TreeMap<E, Object>());
	}

	// 带比较器的构造函数。
	public MyTreeSet(Comparator<? super E> comparator) {
		this(new TreeMap<>(comparator));
	}

	// 创建TreeSet，并将集合c中的全部元素都添加到TreeSet中
	public MyTreeSet(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	// 创建TreeSet，并将s中的全部元素都添加到TreeSet中
	public MyTreeSet(SortedSet<E> s) {
		this(s.comparator());
		addAll(s);
	}

	// 顺序迭代器
	public Iterator<E> iterator() {
		return m.navigableKeySet().iterator();
	}

	// 返回TreeSet的逆序排列的迭代器。
	// 因为TreeSet时TreeMap实现的，所以这里实际上时返回TreeMap的“键集”对应的迭代器
	public Iterator<E> descendingIterator() {
		return m.descendingKeySet().iterator();
	}

	// 返回此 set 中所包含元素的逆序视图。
	public NavigableSet<E> descendingSet() {
		return new MyTreeSet<>(m.descendingMap());
	}

	public int size() {
		return m.size();
	}

	public boolean isEmpty() {
		return m.isEmpty();
	}

	public boolean contains(Object o) {
		return m.containsKey(o);
	}

	public boolean add(E e) {
		return m.put(e, PRESENT) == null;
	}

	public boolean remove(Object o) {
		return m.remove(o) == PRESENT;
	}

	public void clear() {
		m.clear();
	}

	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	@Override
	public Comparator<? super E> comparator() {
		
		return null;
	}

	@Override
	public E first() {
		
		return null;
	}

	@Override
	public E last() {
		
		return null;
	}

	@Override
	public E lower(E e) {
		
		return null;
	}

	@Override
	public E floor(E e) {
		
		return null;
	}

	@Override
	public E ceiling(E e) {
		
		return null;
	}

	@Override
	public E higher(E e) {
		
		return null;
	}

	@Override
	public E pollFirst() {
		
		return null;
	}

	@Override
	public E pollLast() {
		
		return null;
	}

	@Override
	public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
		
		return null;
	}

	@Override
	public NavigableSet<E> headSet(E toElement, boolean inclusive) {
		
		return null;
	}

	@Override
	public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
		
		return null;
	}

	@Override
	public SortedSet<E> subSet(E fromElement, E toElement) {
		
		return null;
	}

	@Override
	public SortedSet<E> headSet(E toElement) {
		
		return null;
	}

	@Override
	public SortedSet<E> tailSet(E fromElement) {
		
		return null;
	}

}
