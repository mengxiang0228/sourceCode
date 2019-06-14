package sourceCode.String.MyStringBuffer;

import java.util.Arrays;
//默认长度为16,2个字节
public final class MyStringBuffer extends MyAbstractStringBuilder implements java.io.Serializable, CharSequence {

	private static final long serialVersionUID = 2120712422178427245L;

	/**
	 * 用transient关键字标记的成员变量不参与序列化过程 作用:当每次对MyStringBuffer进行修改之后，都会对toStringCache
	 * 置空，这样做保证toString方法返回的是缓存的最后一个值 保证了多线程调用时的同步
	 * 
	 */
	private transient char[] toStringCache;

	public MyStringBuffer() {
		super(16);
	}

	public MyStringBuffer(int capacity) {
		super(capacity);
	}

	public MyStringBuffer(String str) {
		super(str.length() + 16);
		append(str);
	}
	// 还有一个构建方法是 CharSequence 省略了

	/**
	 * 接下来就是对MyAbstractStringBuilder中所有的方法用synchronized修饰 让其同步
	 */
	@Override
	public synchronized int length() {
		return count;
	}

	@Override
	public synchronized int capacity() {
		return value.length;
	}

	@Override
	public synchronized void ensureCapacity(int minimumCapacity) {
		super.ensureCapacity(minimumCapacity);
	}

	/**
	 * @since 1.5
	 */
	@Override
	public synchronized void trimToSize() {
		super.trimToSize();
	}

	/**
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 * @see #length()
	 */
	@Override
	public synchronized void setLength(int newLength) {
		toStringCache = null;
		super.setLength(newLength);
	}

	/**
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 * @see #length()
	 */
	@Override
	public synchronized char charAt(int index) {
		if ((index < 0) || (index >= count))
			throw new StringIndexOutOfBoundsException(index);
		return value[index];
	}

	/**
	 * @since 1.5
	 */
	@Override
	public synchronized int codePointAt(int index) {
		return super.codePointAt(index);
	}

	@Override
	public synchronized void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
		super.getChars(srcBegin, srcEnd, dst, dstBegin);
	}

	/**
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 * @see #length()
	 */
	@Override
	public synchronized void setCharAt(int index, char ch) {
		if ((index < 0) || (index >= count))
			throw new StringIndexOutOfBoundsException(index);
		toStringCache = null;
		value[index] = ch;
	}

	@Override
	public synchronized MyStringBuffer append(String str) {
		toStringCache = null;
		super.append(str);
		return this;
	}

	public synchronized MyStringBuffer append(MyStringBuffer sb) {
		toStringCache = null;
		super.append(sb);
		return this;
	}

	/**
	 * @since 1.8
	 */
	@Override
	synchronized MyStringBuffer append(MyAbstractStringBuilder asb) {
		toStringCache = null;
		super.append(asb);
		return this;
	}

	/**
	 * @throws StringIndexOutOfBoundsException
	 *             {@inheritDoc}
	 * @since 1.2
	 */
	@Override
	public synchronized MyStringBuffer deleteCharAt(int index) {
		toStringCache = null;
		super.deleteCharAt(index);
		return this;
	}

	/**
	 * @throws StringIndexOutOfBoundsException
	 *             {@inheritDoc}
	 * @since 1.2
	 */
	@Override
	public synchronized MyStringBuffer replace(int start, int end, String str) {
		toStringCache = null;
		super.replace(start, end, str);
		return this;
	}

	/**
	 * @throws StringIndexOutOfBoundsException
	 *             {@inheritDoc}
	 * @since 1.2
	 */
	@Override
	public synchronized String substring(int start, int end) {
		return super.substring(start, end);
	}

	/**
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	@Override
	public synchronized MyStringBuffer insert(int offset, char c) {
		toStringCache = null;
		super.insert(offset, c);
		return this;
	}

	/**
	 * @since JDK1.0.2
	 */
	@Override
	public synchronized MyStringBuffer reverse() {
		toStringCache = null;
		super.reverse();
		return this;
	}

	@Override
	public synchronized String toString() {
		if (toStringCache == null) {
			toStringCache = Arrays.copyOfRange(value, 0, count);
		}
		return new String(toStringCache);
	}

	// 序列化 略...
	private static final java.io.ObjectStreamField[] serialPersistentFields = {
			new java.io.ObjectStreamField("value", char[].class), new java.io.ObjectStreamField("count", Integer.TYPE),
			new java.io.ObjectStreamField("shared", Boolean.TYPE), };

}
