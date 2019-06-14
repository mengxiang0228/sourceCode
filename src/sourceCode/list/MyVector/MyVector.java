package sourceCode.list.MyVector;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Spliterator;

//默认创建长度为10,扩容2
//这玩意居然有3种删除方法，remove()->removeElement()->removeElementAt()
//removeElementAt(index)和remove(int index)两方法居然一样，而且都同步，这是在弄啥
public class MyVector<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

	private static final long serialVersionUID = 8308377653556277215L;

	protected Object[] elementData;// 存储元素数组

	protected int elementCount;// 元素个数

	// 容量增长系数
	protected int capacityIncrement;

	public MyVector(int initialCapacity, int capacityIncrement) {
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		this.elementData = new Object[initialCapacity];
		this.capacityIncrement = capacityIncrement;
	}

	public MyVector(int initialCapacity) {
		this(initialCapacity, 0);
	}

	public MyVector() {
		this(10);
	}

	// 将指定数组复制到当前集合
	public synchronized void copyInto(Object[] anArray) {
		System.arraycopy(elementData, 0, anArray, 0, elementCount);
	}

	// 将数组容量修改为实际使用容量
	public synchronized void trimToSize() {
		modCount++;
		// 原容量大小
		int oldCapacity = elementData.length;
		if (elementCount < oldCapacity) {
			elementData = Arrays.copyOf(elementData, elementCount);
		}
	}

	/**
	 * 扩容 最简单判断 输入容量是否大于0，内部调用不走这步，直接进下面
	 * 
	 * @param minCapacity
	 */
	public synchronized void ensureCapacity(int minCapacity) {
		if (minCapacity > 0) {
			modCount++;// 只要满足，操作数就增加，不管实际是否扩容
			ensureCapacityHelper(minCapacity);
		}
	}

	// 判断传入容量是否比现在容量大
	private void ensureCapacityHelper(int minCapacity) {
		if (minCapacity - elementData.length > 0) {
			grow(minCapacity);
		}
	}

	// 最大扩容大小 比Int的最大值少8
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	// 扩容核心
	private void grow(int minCapacity) {
		int oldCapacity = elementData.length;
		// 默认新容量是原容量的2倍
		int newCapacity = oldCapacity + ((capacityIncrement > 0) ? capacityIncrement : oldCapacity);
		if (newCapacity - minCapacity < 0) {
			newCapacity = minCapacity;// 判断理论扩容大小和传入扩容大小 取大的
		}
		if (newCapacity - MAX_ARRAY_SIZE > 0) {
			// 扩容大小超过了 理论最大值 调用更大的方法
			// hugeCapacity()
		}
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	// 设置容量大小,修改了实际元素个数了
	public synchronized void setSize(int newSize) {
		modCount++;
		if (newSize > elementCount) {
			ensureCapacityHelper(newSize);
		} else {
			// 将后面为空的设置为null?
			for (int i = newSize; i < elementCount; i++) {
				elementData[i] = null;
			}
		}
		elementCount = newSize;
	}

	// 查看容量大小
	public synchronized int capacity() {
		return elementData.length;
	}

	// 查看元素个数
	public synchronized int size() {
		return elementCount;
	}

	public synchronized boolean isEmpty() {
		return elementCount == 0;
	}

	// 返回“Vector中全 部元素对应的Enumeration”
	// 与迭代器用法相同
	public Enumeration<E> elements() {
		return new Enumeration<E>() {
			int count = 0;

			@Override
			public boolean hasMoreElements() {
				return count < elementCount;
			}

			@Override
			public E nextElement() {
				synchronized (MyVector.this) {
					if (count < elementCount) {
						return elementData(count++);
					}
				}
				throw new NoSuchElementException("Vector Enumeration");
			}
		};

	}

	// 查看从指定索引开始指定位置的元素的索引位置 indexOf(Object o),也是调用了这个方法
	public synchronized int indexOf(Object o, int index) {
		if (o == null) {
			for (int i = index; i < elementCount; i++)
				if (elementData[i] == null)
					return i;
		} else {
			for (int i = index; i < elementCount; i++)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	public synchronized int lastIndexOf(Object o, int index) {
		// indexOf没有进行判断，由于 indexOf 中 i < elementCount
		// 已经进行了控制，如果index<0,直接报错index>elementCount不进行循环
		if (index >= elementCount)
			throw new IndexOutOfBoundsException(index + " >= " + elementCount);

		if (o == null) {
			for (int i = index; i >= 0; i--)
				if (elementData[i] == null)
					return i;
		} else {
			for (int i = index; i >= 0; i--)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	// 删除指定位置的元素
	/**
	 * [1,2,3,4,5,6,7,8,9,10] elementCount=10 index = 2:(3) j= 7
	 * System.arraycopy(elementData, 3, elementData, 2, 7);
	 */
	public synchronized void removeElementAt(int index) {
		modCount++;
		if (index >= elementCount) {
			throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
		} else if (index < 0) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		int j = elementCount - index - 1;// 获取index后面的元素个数
		if (j > 0) {

			System.arraycopy(elementData, index + 1, elementData, index, j);
		}
		elementCount--;
		elementData[elementCount] = null;// 便于GC回收
	}

	// 指定位置插入元素
	public synchronized void insertElementAt(E obj, int index) {
		modCount++;
		if (index > elementCount) {
			throw new ArrayIndexOutOfBoundsException(index + " > " + elementCount);
		}
		ensureCapacityHelper(elementCount + 1);
		System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
		elementData[index] = obj;
		elementCount++;
	}

	// 添加元素
	public synchronized void addElement(E obj) {
		modCount++;
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount++] = obj;
	}

	public synchronized boolean removeElement(Object obj) {
		modCount++;
		int i = indexOf(obj);
		if (i >= 0) {
			removeElementAt(i);
			return true;
		}
		return false;
	}

	// 删除所有元素
	public synchronized void removeAllElements() {
		modCount++;
		// Let gc do its work
		for (int i = 0; i < elementCount; i++)
			elementData[i] = null;

		elementCount = 0;
	}

	// 克隆数组
	@SuppressWarnings("unchecked")
	public synchronized Object clone() {

		try {
			MyVector<E> vector = (MyVector<E>) super.clone();
			// 重新创建一个对象，而不是传递地址
			vector.elementData = Arrays.copyOf(elementData, elementCount);
			vector.modCount = 0;
			return vector;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	// 转数组,重新创建对象，放置误操作把原数组修改了
	public synchronized Object[] toArray() {
		return Arrays.copyOf(elementData, elementCount);
	}

	// 得带指定位置的元素,内部方法不用同步
	@SuppressWarnings("unchecked")
	E elementData(int index) {
		return (E) elementData[index];
	}

	public synchronized E get(int index) {
		if (index >= elementCount)
			throw new ArrayIndexOutOfBoundsException(index);

		return elementData(index);
	}

	// 套路一样
	public synchronized E set(int index, E element) {
		if (index >= elementCount)
			throw new ArrayIndexOutOfBoundsException(index);

		E oldValue = elementData(index);
		elementData[index] = element;
		return oldValue;
	}

	// 与addElement()方法居然一样，一个返回操作结果，一个什么都不返回
	// 而且这俩居然都是同步方法
	public synchronized boolean add(E e) {
		modCount++;
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount++] = e;// 元素数比位置索引多1
		return true;
	}

	// 删除元素
	public boolean remove(Object o) {
		return removeElement(o);
	}

	// 添加元素
	public void add(int index, E element) {
		insertElementAt(element, index);
	}

	// 删除方法 同步 1.2
	public synchronized E remove(int index) {
		modCount++;
		if (index >= elementCount)
			throw new ArrayIndexOutOfBoundsException(index);
		E oldValue = elementData(index);

		int numMoved = elementCount - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--elementCount] = null; // 便于GC，比removeElementAt()简单
		return oldValue;
	}

	public void clear() {
		removeAllElements();
	}

	public synchronized boolean equals(Object o) {
		return super.equals(o);
	}

	public synchronized int hashCode() {
		return super.hashCode();
	}

	public synchronized String toString() {
		return super.toString();
	}

	/******************************************/
	// 内部类
	public synchronized ListIterator<E> listIterator() {
		return new ListItr(0);
	}

	public synchronized Iterator<E> iterator() {
		return new Itr();
	}

	// 迭代器
	private class Itr implements Iterator<E> {
		int cursor; // index of next element to return
		int lastRet = -1; // index of last element returned; -1 if no such
		int expectedModCount = modCount;

		public boolean hasNext() {
			return cursor != elementCount;
		}

		public E next() {
			synchronized (MyVector.this) {
				checkForComodification();
				int i = cursor;
				if (i >= elementCount)
					throw new NoSuchElementException();
				cursor = i + 1;
				return elementData(lastRet = i);
			}
		}

		public void remove() {
			if (lastRet == -1)
				throw new IllegalStateException();
			synchronized (MyVector.this) {
				checkForComodification();
				MyVector.this.remove(lastRet);
				expectedModCount = modCount;
			}
			cursor = lastRet;
			lastRet = -1;
		}

		final void checkForComodification() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}

	}

	// 提供正向遍历和反向遍历
	final class ListItr extends Itr implements ListIterator<E> {
		ListItr(int index) {
			super();
			cursor = index;
		}

		// 反向遍历
		public boolean hasPrevious() {
			return cursor != 0;
		}

		public int nextIndex() {
			return cursor;
		}

		public int previousIndex() {
			return cursor - 1;
		}

		public E previous() {
			synchronized (MyVector.this) {
				checkForComodification();
				int i = cursor - 1;
				if (i < 0)
					throw new NoSuchElementException();
				cursor = i;
				return elementData(lastRet = i);
			}
		}

		public void set(E e) {
			if (lastRet == -1) {
				throw new IllegalStateException();
			}
			synchronized (MyVector.this) {
				checkForComodification();
				MyVector.this.set(lastRet, e);
			}
		}

		public void add(E e) {
			int i = cursor;
			synchronized (MyVector.this) {
				checkForComodification();
				MyVector.this.add(i, e);
				expectedModCount = modCount;
			}
			// 添加完成后 指向下一个元素
			cursor = i + 1;
			lastRet = -1;

		}
		// 先写出常用方法吧
	}

	@Override
	//1.8新加的内部类 用于并行访问
	public Spliterator<E> spliterator() {
//		return new VectorSpliterator<>(this, null, 0, -1, 0);
		return null;
	}

}
