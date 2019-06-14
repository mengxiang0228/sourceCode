package sourceCode.list.MyArrayList;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/**
 * RandomAccess
 * 在遍历时可以判断是否属于RandomAccess(如ArrayList)还是SequenceAccess(如LinkedList） Cloneable
 * 实现clone方法
 * 
 * @author 李雅翔
 * @date 2017年8月29日
 * @param <E>
 */
//默认创建长度为10,扩容1.5
public class MyArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

	private static final long serialVersionUID = -6978905611108914647L;
	// 默认初始的容量
	private static final int DEFAULT_CAPACITY = 10;
	// 准备一个空的数组
	private static final Object[] EMPTY_ELEMENTDATA = {};
	// 默认的空数组
	private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
	// 数组缓冲区中的数组的元素存储。
	transient Object[] elementData;
	// ArrayList的元素个数
	private int size;

	public MyArrayList(int initialCapacity) {
		if (initialCapacity > 0) {
			this.elementData = new Object[initialCapacity];
		} else if (initialCapacity == 0) {
			this.elementData = EMPTY_ELEMENTDATA;// 初始化等于0，将空数组赋给elementData
		} else {
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		}
	}

	public MyArrayList() {
		// 构建一个初始容量为空的数组。
		this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
	}

	public MyArrayList(Collection<? extends E> c) {
		elementData = c.toArray();// 返回包含c所有元素的数组
		if ((size = elementData.length) != 0) {
			// c.toArray might (incorrectly) not return Object[] (see 6260652)
			if (elementData.getClass() != Object[].class)
				elementData = Arrays.copyOf(elementData, size, Object[].class);// 复制指定数组，使elementData具有指定长度
		} else {
			// replace with empty array.
			this.elementData = EMPTY_ELEMENTDATA;
		}
	}

	// 将当前容量值设为当前实际元素大小
	public void trimToSize() {
		// 此列表已在结构上修改的次数。 官方
		modCount++;// 初始化为0,记录修改次数吧
		if (size < elementData.length) {
			elementData = (size == 0) ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData, size);
		}
	}

	/**
	 * 扩容
	 * 
	 * @param minCapacity
	 *            最小期望容量。 本方法主要用来判断传入大小是否大于0，如果数组为初始化数组，则判断传入容量是否比现在数组大
	 */
	public void ensureCapacity(int minCapacity) {
		int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) ? 0// 任意大小，如果不是默认元素表
				: DEFAULT_CAPACITY;
		// 确保扩容至少等于规定的最低。 minExpand=0,10
		if (minCapacity > minExpand) {
			ensureExplicitCapacity(minCapacity);
		}
	}

	// 内部方法扩容调用的
	// 不用判断 传入容量是否大于0
	private void ensureCapacityInternal(int minCapacity) {
		// 如果数组为初始化数组，则取默认容量和传入容量较大的
		// 不是初始化数组，则直接进入判断是否扩容模块
		if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
			minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
		}

		ensureExplicitCapacity(minCapacity);
	}

	// 判断扩容的大小是否比扩容前的大
	private void ensureExplicitCapacity(int minCapacity) {
		modCount++;// 记录修改次数
		// 扩容的大小比原数组大
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	// 设置最大扩容大小,与StringBuild的扩容原理相同
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	// 开始扩容
	private void grow(int minCapacity) {
		int oldCapacity = elementData.length;
		// 扩容后的大小是 原大小的1.5倍 oldCapacity >> 1=oldCapacity/2
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		// 如果默认扩容大小比 传入的容量小就取传入的容量
		if (newCapacity - minCapacity < 0) {
			newCapacity = minCapacity;
		}
		if (newCapacity - MAX_ARRAY_SIZE > 0) {
			// 如果实际扩容的大小超过了扩容限制，就会进行大容量操作，同StringBuild的扩容
			// newCapacity = hugeCapacity(minCapacity);
		}
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	// 返回元素个数
	public int size() {
		return size;
	}

	// 判断ArrayList是否为空
	public boolean isEmpty() {
		return size == 0;
	}

	// 判断ArrayList中是否包含Object(o)
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	// 正向查找，返回ArrayList中元素Object(o)的索引位置
	// lastIndexOf 反向查下找
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++) {
				if (elementData[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < size; i++)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	// 返回此 ArrayList实例的浅拷贝。
	public Object clone() {
		try {
			// 调用父类(翻看源码可见是Object类)的clone方法得到一个ArrayList副本
			MyArrayList<?> v = (MyArrayList<?>) super.clone();
			v.elementData = Arrays.copyOf(elementData, size);// 复制数组
			v.modCount = 0;// 操作数置0
			return v;
		} catch (CloneNotSupportedException e) {
			// 这不应该发生，因为我们是可克隆的
			throw new InternalError(e);
		}
	}

	// 返回ArrayList的数组
	public Object[] toArray() {
		return Arrays.copyOf(elementData, size);
	}

	@SuppressWarnings("unchecked")
	E elementData(int index) {
		return (E) elementData[index];
	}

	// 返回指定位置的元素
	public E get(int index) {
		// 检查数组是否越界
		rangeCheck(index);
		return elementData(index);
	}

	// 将指定索引上的值替换为新值，并返回旧值
	public E set(int index, E element) {
		rangeCheck(index);
		E oldValue = elementData(index);
		elementData[index] = element;
		return oldValue;
	}

	// 将指定的元素添加到此列表的尾部
	public boolean add(E e) {
		ensureCapacityInternal(size + 1);
		elementData[size++] = e;
		return true;
	}

	// 将element添加到ArrayList的指定位置
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		// size元素个数
		ensureCapacityInternal(size + 1);// 扩容前先判断size + 1是否比现有数组大,否则不进行扩容
		// 将index元素向后移动一位，此时index的位置放的还是原来的元素，
		// index+1放的是index的元素，后面元素都向后推一格
		// 如 第二个2位置插入 9 :[1,2,3,4,,,,,]->不进行扩容,复制数组->[1,2,3,3,4]->插入元素->[1,2,9,3,4]
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}

	// 删除ArrayList指定位置的元素
	// 如 删除第二个2位置 :[1,2,3,4,5]->复制数组->[1,2,4,5,5]->size-1位置数据置null->[1,2,4,5,null]
	public E remove(int index) {
		rangeCheck(index);
		modCount++;
		E oldValue = elementData(index);
		int numMoved = size - index - 1;// 5-2-1 = 2 index后面的元素个数
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null;
		return oldValue;
	}

	// 移除ArrayList中首次出现的指定元素(如果存在)。
	public boolean remove(Object o) {
		if (o == null) {
			for (int index = 0; index < size; index++)
				if (elementData[index] == null) {
					fastRemove(index);
					return true;
				}
		} else {
			for (int index = 0; index < size; index++)
				if (o.equals(elementData[index])) {
					fastRemove(index);
					return true;
				}
		}
		return false;
	}

	// 快速删除指定位置的元素
	private void fastRemove(int index) {
		modCount++;
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null;
	}

	// 清空ArrayList，将全部的元素设为null
	public void clear() {
		modCount++;
		for (int i = 0; i < size; i++) {
			elementData[i] = null;
		}
		size = 0;
	}

	// 截取两个位置之间的长度List
	public List<E> subList(int fromIndex, int toIndex) {
		subListRangeCheck(fromIndex, toIndex, size);
		return new SubList(this, 0, fromIndex, toIndex);
	}

	// 检查
	static void subListRangeCheck(int fromIndex, int toIndex, int size) {
		if (fromIndex < 0)
			throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
		if (toIndex > size)
			throw new IndexOutOfBoundsException("toIndex = " + toIndex);
		if (fromIndex > toIndex)
			throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
	}

	// 检查索引是否越界
	private void rangeCheck(int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private void rangeCheckForAdd(int index) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}
	// 调用内部类 Iterator,ListIterator

	public Iterator<E> iterator() {
		return new Itr();
	}

	public ListIterator<E> listIterator(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Index: " + index);
		return new ListItr(index);
	}

	public ListIterator<E> listIterator() {
		return new ListItr(0);
	}

	// 其中的Itr是实现了Iterator接口，同时重写了里面的hasNext()，next()，remove()等方法；
	// 只能读取不本分
	private class Itr implements Iterator<E> {

		int cursor; // 要返回的下一个元素的索引
		int lastRet = -1; // 返回的最后一个元素的索引；如果没有 -1
		int expectedModCount = modCount;// 检查器变量

		@Override
		public boolean hasNext() {
			// 判断是否到达最后一个元素
			return cursor != size;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			// 检测数组是否被改动过
			checkForComodification();
			int i = cursor;
			//会检查是否越界
			if (i >= size) {
				throw new NoSuchElementException();
			}
			// 将外部类的 数组元素传进来
			/**
			 * MyArrayList.this.elementData这样写的原因： 由于外部类和内部类都使用了相同的变量名，为使编译器能知道到底调用的是外部类
			 * 变量还是内部类变量，采用上述写法
			 */
			Object[] elementData = MyArrayList.this.elementData;
			if (i >= elementData.length) {
				throw new ConcurrentModificationException();
			}
			// 记录下一个操作位置
			cursor = i + 1;
			return (E) elementData[lastRet = i];
		}

		// 内部居然提供了删除方法
		// 实际上还是调用了外部类的remove方法
		public void remove() {
			if (lastRet < 0)
				throw new IllegalStateException();
			checkForComodification();

			try {
				MyArrayList.this.remove(lastRet);
				// 将下一个操作位置置为 当前位置（因为当前位置元素已经删除）
				cursor = lastRet;
				// 不允许连续调用remove方法，避免误操作
				lastRet = -1;
				// 同步外部类的 操作数，保证两个同步
				expectedModCount = modCount;
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}

		}

		/**
		 * 对Vector、ArrayList在迭代的时候如果同时对其进行修改就会抛出
		 * java.util.ConcurrentModificationException异常。
		 */
		final void checkForComodification() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}

	}

	// 与Itr内部类相似，但是提供了 修改、添加反向遍历的操作
	private class ListItr extends Itr implements ListIterator<E> {
		ListItr(int index) {
			super();
			cursor = index;
		}

		@Override
		// 如果以逆向遍历列表，列表迭代器有多个元素，则返回 true。
		public boolean hasPrevious() {
			return cursor != 0;
		}

		@SuppressWarnings("unchecked")
		@Override
		// 返回列表中的前一个元素。
		public E previous() {
			checkForComodification();
			int i = cursor - 1;
			if (i < 0) {
				throw new NoSuchElementException();
			}
			Object[] elementData = MyArrayList.this.elementData;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i;
			return (E) elementData[lastRet = i];
		}

		@Override
		public int nextIndex() {
			return cursor;
		}

		@Override
		public int previousIndex() {
			return cursor - 1;
		}

		@Override
		public void set(E e) {
			if (lastRet < 0) {
				throw new IllegalStateException();
			}
			checkForComodification();
			try {
				MyArrayList.this.set(lastRet, e);
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}

		@Override
		public void add(E e) {
			checkForComodification();
			try {
				int i = cursor;
				MyArrayList.this.add(i, e);
				cursor = i + 1;
				// 添加完成后如果不进行其他操作，不允许修改当前位置值
				lastRet = -1;
				expectedModCount = modCount;
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}

		}

	}

	// 类内部实现了对子序列的增删改查等方法
	// 但它同时也充分利用了内部类的优点，就是共享ArrayList的全局变量，
	// 例如检查器变量modCount，数组elementData等，所以SubList进行的增删改查操作都是对ArrayList的数组进行的，并没有创建新的数组。
	@SuppressWarnings("unused")
	private class SubList extends AbstractList<E> implements RandomAccess {

		
		private final AbstractList<E> parent;
		private final int parentOffset;
		private final int offset;// 原List偏移
		int size;

		SubList(AbstractList<E> parent, int offset, int fromIndex, int toIndex) {
			this.parent = parent;
			this.parentOffset = fromIndex;// 当前List偏移
			this.offset = offset + fromIndex;// 原List偏移
			this.size = toIndex - fromIndex;// 当前List字符数
			this.modCount = MyArrayList.this.modCount;
		}

		public E set(int index, E e) {
			// 省略检查异常
			E oldValue = MyArrayList.this.elementData(offset + index);
			MyArrayList.this.elementData[offset + index] = e;
			return oldValue;
		}

		@Override
		public E get(int index) {
			// 省略检查异常
			return MyArrayList.this.elementData(offset + index);
		}

		@Override
		public int size() {
			// 省略检查异常
			return this.size;
		}
		// 此处省略n多操作方法 如 add remove 同时其内部还有内部类 如 ListIterator (迭代器)
		// 还有检查方法 checkForComodification（检查操作数是否一致）
	}
}
