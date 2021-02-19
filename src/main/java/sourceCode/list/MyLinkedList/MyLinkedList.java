package sourceCode.list.MyLinkedList;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class MyLinkedList<E> extends AbstractSequentialList<E>
		implements List<E>, Deque<E>, Cloneable, java.io.Serializable {

	private static final long serialVersionUID = -4447254707257157318L;

	transient int size = 0;

	transient Node<E> first;// 头结点,不存储数据

	transient Node<E> last;// 尾节点

	public MyLinkedList() {
	}

	public MyLinkedList(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	// 使用对应参数作为第一个节点，内部使用
	private void linkFirst(E e) {
		final Node<E> f = first;
		final Node<E> newNode = new Node<>(null, e, f);
		first = newNode;
		if (f == null) {
			last = newNode;// 如果之前首节点为空(size==0)，那么尾节点就是首节点
		} else {
			f.prev = newNode;// 如果之前首节点不为空，之前的首节点的前一个节点为当前首节点
		}
		size++;
		modCount++;
	}

	// 使用对应参数作为尾节点
	void linkLast(E e) {
		// last存储的信息就是尾节点信息
		final Node<E> l = last;// l是之前尾节点
		final Node<E> newNode = new Node<>(l, e, null);
		last = newNode;
		if (l == null) {
			first = newNode;
		} else {
			l.next = newNode;// 现在l已经不是尾节点了，尾节点之前的一个节点 l.next是尾节点
		}
		size++;
		modCount++;
	}

	// 在指定节点前插入节点，节点succ不能为空
	void linkBefore(E e, Node<E> succ) {
		final Node<E> pred = succ.prev;// 当前节点前一节点的值
		final Node<E> newNode = new Node<E>(pred, e, succ);
		succ.prev = newNode;
		if (pred == null) {
			// 如果succ是头节点
			// first只记录头位置
			first = newNode;
		} else {
			pred.next = newNode;// 将前一节点的next设置为newNode
		}
		size++;
		modCount++;

	}

	// 删除首节点并返回删除前首节点的值，内部使用
	private E unlinkFirst(Node<E> f) {
		// assert f == first && f != null;
		final E element = f.item;
		final Node<E> next = f.next;
		f.item = null;
		f.next = null; // help GC
		first = next;
		if (next == null) {
			// 没有元素了
			last = null;
		} else {
			// 将第二个节点设置为首节点
			next.prev = null;
		}
		size--;
		modCount++;
		return element;
	}

	// 删除尾节点并返回删除前尾节点的值，内部使用
	private E unlinkLast(Node<E> l) {
		final E element = l.item;
		final Node<E> prev = l.prev;
		last = prev;
		if (prev == null) {
			// 没有元素了
			first = null;
		} else {
			prev.next = null;
		}
		size--;
		modCount++;
		return element;
	}

	// 删除指定节点并返回被删除的元素值
	E unlink(Node<E> x) {
		final E element = x.item;
		final Node<E> next = x.next;
		final Node<E> prev = x.prev;

		if (prev == null) {
			first = next;// 如果是首节点
		} else {
			prev.next = next;// 将当前元素的后节点 给了它前节点所对应元素的后节点
			x.prev = null;// 当前元素前节点置空
		}
		if (next == null) {
			last = prev;// 如果是尾节点
		} else {
			next.prev = prev;//
			x.next = null;
		}
		x.item = null;
		size--;
		modCount++;
		return element;

	}

	// 添加一个元素，默认添加到末尾作为最后一个元素
	public boolean add(E e) {
		linkLast(e);
		return true;
	}

	public void add(int index, E element) {
		checkPositionIndex(index);
		if (index == size) {
			linkLast(element);
		} else {
			linkBefore(element, node(index));
		}
	}

	// 获取指定索引的节点的值
	public E get(int index) {
		checkElementIndex(index);
		return node(index).item;
	}

	// 修改指定索引的值并返回之前的值
	public E set(int index, E element) {
		checkElementIndex(index);
		// 传递过去的是地址，所以修改x就是对 链表内的值修改
		Node<E> x = node(index);
		E oldVal = x.item;
		x.item = element;
		return oldVal;
	}

	// 删除指定位置元素
	public E remove(int index) {
		checkElementIndex(index);
		return unlink(node(index));
	}

	// 删除一个元素
	public boolean remove(Object o) {
		if (o == null) {
			for (Node<E> x = first; x != null; x = x.next) {
				if (x.item == null) {
					unlink(x);
					return true;
				}
			}
		} else {
			for (Node<E> x = first; x != null; x = x.next) {
				if (o.equals(x.item)) {
					unlink(x);
					return true;
				}
			}
		}
		return false;
	}

	// 清空
	public void clear() {
		// 方便gc回收垃圾
		// 没有管首节点
		for (Node<E> x = first; x != null;) {
			Node<E> next = x.next;
			next.item = null;
			next.prev = null;
			next.next = null;
			x = next;// 方便循环
		}
		first = last = null;
		size = 0;
		modCount = 0;
	}

	// 获取指定位置的节点
	Node<E> node(int index) {
		// 如果位置索引小于列表长度的一半(或一半减一)，从前面开始遍历；否则，从后面开始遍历
		if (index < (size >> 1)) {
			Node<E> x = first;// index==0时不会循环，直接返回first
			for (int i = 0; i < index; i++) {
				x = x.next;
			}
			return x;
		} else {
			Node<E> x = last;
			for (int i = size - 1; i > index; i--) {
				x = x.prev;
			}
			return x;
		}
	}

	// 返回指定元素所在位置
	public int indexOf(Object o) {
		int index = 0;
		if (o == null) {
			for (Node<E> x = first; x != null; x = x.next) {
				if (x.item == null) {
					return index;
				}
				index++;
			}
		} else {
			for (Node<E> x = first; x != null; x = x.next) {
				if (o.equals(x.item)) {
					return index;
				}
				index++;
			}
		}
		return -1;
	}

	/***************************************************************/
	/**************** 以下为检查异常 ***************/
	/***************************************************************/

	// 检查索引是否超出范围，因为元素索引是0~size-1的，所以index必须满足0<=index<size
	private boolean isElementIndex(int index) {
		return index >= 0 && index < size;
	}

	// 检查位置是否超出范围，index必须在index~size之间（含），如果超出，返回false
	private boolean isPositionIndex(int index) {
		return index >= 0 && index <= size;
	}

	/**
	 * Constructs an IndexOutOfBoundsException detail message. Of the many possible
	 * refactorings of the error handling code, this "outlining" performs best with
	 * both server and client VMs.
	 */
	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}

	private void checkElementIndex(int index) {
		if (!isElementIndex(index))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private void checkPositionIndex(int index) {
		if (!isPositionIndex(index))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private static class Node<E> {
		E item; // 节点所包含的值
		Node<E> next; // 下一个节点
		Node<E> prev; // 上一个节点

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	/***************************************************************/
	/*
	 * 队列 提供普通队列和双向队列的功能，当然，也可以实现栈，FIFO，FILO
	 */
	/***************************************************************/

	@Override
	// 入队（从尾部）
	public boolean offer(E e) {
		return add(e);
	}

	@Override
	// 入队（从前端），始终返回true
	public boolean offerFirst(E e) {
		addFirst(e);
		return true;
	}

	@Override
	// 入队（从后端），始终返回true
	public boolean offerLast(E e) {
		addLast(e);
		return true;
	}

	@Override
	// 出队（从前端），获得第一个元素，不存在会返回null，会删除元素（节点）
	public E pollFirst() {
		final Node<E> f = first;
		return (f == null) ? null : unlinkFirst(f);
	}

	@Override
	// 出队（从后端），获得最后一个元素，不存在会返回null，会删除元素（节点）
	public E pollLast() {
		final Node<E> l = last;
		return (l == null) ? null : unlinkLast(l);
	}

	@Override
	// 出队（从前端），获得第一个元素，不存在会返回null，不会删除元素（节点）
	public E peekFirst() {
		final Node<E> f = first;
		return (f == null) ? null : f.item;
	}

	@Override
	// 出队（从后端），获得最后一个元素，不存在会返回null，不会删除元素（节点）
	public E peekLast() {
		final Node<E> l = last;
		return (l == null) ? null : l.item;
	}

	@Override
	// 出队（从前端），如果不存在会返回null，存在的话会返回值并移除这个元素（节点）
	public E poll() {
		final Node<E> f = first;
		return (f == null) ? null : unlinkFirst(f);
	}

	@Override
	// 出队（从前端），不删除元素，若为null会抛出异常而不是返回null
	public E element() {
		return getFirst();
	}

	@Override
	// 出队（从前端），获得第一个元素，不存在会返回null，不会删除元素（节点）
	public E peek() {
		final Node<E> f = first;
		return (f == null) ? null : f.item;
	}

	@Override
	// 入栈，从前面添加
	public void push(E e) {
		addFirst(e);
	}

	@Override
	// 出栈，返回栈顶元素，从前面移除（会删除）
	public E pop() {
		return removeFirst();
	}

	@Override
	// 获取并删除该双端队列的第一次出现的元素o
	public boolean removeFirstOccurrence(Object o) {
		return remove(o);
	}

	@Override
	// 获取并删除该双端队列的最后一次出现的元素o
	public boolean removeLastOccurrence(Object o) {
		if (o == null) {
			for (Node<E> x = last; x != null; x = x.prev) {
				if (x.item == null) {
					unlink(x);
					return true;
				}
			}
		} else {
			for (Node<E> x = last; x != null; x = x.prev) {
				if (o.equals(x.item)) {
					unlink(x);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public E remove() {
		return removeFirst();
	}

	@Override
	// 添加元素作为第一个元素
	public void addFirst(E e) {
		linkFirst(e);
	}

	@Override
	// 添加元素作为最后一个元素
	public void addLast(E e) {
		linkLast(e);
	}

	@Override
	public E getFirst() {
		final Node<E> f = first;
		if (f == null)
			throw new NoSuchElementException();
		return f.item;
	}

	@Override
	public E getLast() {
		final Node<E> l = last;
		if (l == null)
			throw new NoSuchElementException();
		return l.item;
	}

	@Override
	// 删除第一个元素并返回删除的元素
	public E removeFirst() {
		final Node<E> f = first;
		if (f == null)
			throw new NoSuchElementException();
		return unlinkFirst(f);
	}

	@Override
	// 删除最后一个元素并返回删除的元素
	public E removeLast() {
		final Node<E> l = last;
		if (l == null)
			throw new NoSuchElementException();
		return unlinkLast(l);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		checkPositionIndex(index);
		return new ListItr(index);
	}

	// 返回列表中元素的列表迭代器
	private class ListItr implements ListIterator<E> {
		private Node<E> lastReturned;
		private Node<E> next;// 某一节点
		private int nextIndex;
		private int expectedModCount = modCount;// 记录操作数

		ListItr(int index) {
			next = (index == size) ? null : node(index);
			nextIndex = index;
		}

		public boolean hasNext() {
			return nextIndex < size;
		}

		public E next() {
			checkForComodification();
			if (!hasNext())
				throw new NoSuchElementException();// 内部会判断是否越界
			lastReturned = next;// 本节点内容
			next = next.next;// 下一节点的值
			nextIndex++;// 指向下一节点
			return lastReturned.item;
		}

		@Override
		public boolean hasPrevious() {
			return nextIndex > 0;
		}

		@Override
		// 反向迭代
		public E previous() {
			checkForComodification();
			if (!hasPrevious())
				throw new NoSuchElementException();
			lastReturned = next = (next == null) ? last : next.prev;
			nextIndex--;
			return lastReturned.item;
		}

		@Override
		public int nextIndex() {
			return 0;
		}

		@Override
		public int previousIndex() {
			return 0;
		}

		@Override
		public void remove() {
			checkForComodification();
			// 如果直接调用remove而不用next 就直接报错
			if (lastReturned == null)
				throw new IllegalStateException();
			Node<E> lastNext = lastReturned.next;// 得到下一个节点信息
			// 把lastReturned中的内容全部清空 null lastReturned 为空
			unlink(lastReturned);
			if (next == lastReturned) {
				// 什么时候会进入 我也不知道 lastReturned删完之后为空
				next = lastNext;
			} else {
				// 删除后原来节点位置是下一个节点信息 ,nextIndex指向之前的下一个节点
				// 所以需要减1
				nextIndex--;
			}
			lastReturned = null;
			expectedModCount++;
		}

		@Override
		public void set(E e) {

		}

		@Override
		public void add(E e) {
		}

		// 检查链表是否进行过修改
		final void checkForComodification() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}
	}

	public Iterator<E> descendingIterator() {
		return new DescendingIterator();
	}

	// 迭代器内部类
	// 只有反向迭代功能
	private class DescendingIterator implements Iterator<E> {
		// 直接使用了列表迭代器
		private final ListItr itr = new ListItr(size());

		public boolean hasNext() {
			return itr.hasPrevious();
		}

		public E next() {
			return itr.previous();
		}

		public void remove() {
			itr.remove();
		}
	}

	@SuppressWarnings("unchecked")
	private MyLinkedList<E> superClone() {
		try {
			return (MyLinkedList<E>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	// 重写clone方法
	public Object clone() {
		MyLinkedList<E> clone = superClone();

		// 将clone置为初始状态
		clone.first = clone.last = null;
		clone.size = 0;
		clone.modCount = 0;
		// 再讲元素放进去
		for (Node<E> x = first; x != null; x = x.next) {
			clone.add(x.item);
		}
		return clone;
	}

	// 转化为数组
	public Object[] toArray() {
		Object[] result = new Object[size];
		int i = 0;
		for (Node<E> x = first; x != null; x = x.next) {
			result[i++] = x.item;
		}
		return result;
	}

	@Override
	@Deprecated
	// TODO 没有完成并行访问
	public Spliterator<E> spliterator() {
		return new LLSpliterator<E>(this, -1, 0);
	}

	// java 1.8新增加的
	// splitable_iterator可分割迭代器,是Java为了并行遍历数据源中的元素而设计的迭代器，这个可以类比最早Java提供的顺序遍历迭代器Iterator，但一个是顺序遍历，一个是并行遍历
	// 并行遍历迭代器
	static final class LLSpliterator<E> implements Spliterator<E> {

		static final int BATCH_UNIT = 1 << 10; // 并行数组大小增量
		static final int MAX_BATCH = 1 << 25; // 最大并行数组大小；
		final MyLinkedList<E> list; // null OK unless traversed
		Node<E> current; // 现在的 节点; null until initialized
		int est; // 规模估算; -1 until first needed
		int expectedModCount; // initialized when est set 操作增删改次数
		int batch; // 拆分并行大小

		LLSpliterator(MyLinkedList<E> list, int est, int expectedModCount) {
			this.list = list;// 操作的数组
			this.est = est;
			this.expectedModCount = expectedModCount;// 操作数
		}

		final int getEst() {
			int s; // force initialization
			final MyLinkedList<E> lst;
			if ((s = est) < 0) {
				if ((lst = list) == null)
					s = est = 0;
				else {
					expectedModCount = lst.modCount;
					current = lst.first;
					s = est = lst.size;
				}
			}
			return s;
		}

		@Override
		// 单个对元素执行给定的动作，如果有剩下元素未处理返回true，否则返回false
		public boolean tryAdvance(Consumer<? super E> action) {
			// 
			return false;
		}

		@Override
		// 对任务分割，返回一个新的Spliterator迭代器
		public Spliterator<E> trySplit() {
			// 
			return null;
		}

		@Override
		// 用于估算还剩下多少个元素需要遍历
		public long estimateSize() {
			// 
			return 0;
		}

		@Override
		// 返回当前对象有哪些特征值
		public int characteristics() {
			// 
			return 0;
		}

		// 对每个剩余元素执行给定的动作，依次处理，直到所有元素已被处理或被异常终止。默认方法调用tryAdvance方法
		public void forEachRemaining(Consumer<? super E> action) {

		}

	}

}
