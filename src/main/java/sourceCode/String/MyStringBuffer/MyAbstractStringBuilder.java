package sourceCode.String.MyStringBuffer;

import java.util.Arrays;

/**
 * AbstractString是StringBuild和StringBuffer的父类 关键操作:
 *  System.arraycopy(value,start+len, value, start, count-end);
 * 
 * Serializable是可以序列化的标志。CharSequence接口包含了
 * charAt()、length() 、subSequence()、toString()这几个方法;
 * 
 * @author 李雅翔
 * @date 2017年8月24日 是一个抽象类
 */
public abstract class MyAbstractStringBuilder implements Appendable, CharSequence {
	// 存储字符串
	char[] value;
	// 存储字符数
	int count;

	// 私有构造方法
	MyAbstractStringBuilder() {

	}

	// 创建指定的容量
	MyAbstractStringBuilder(int capacity) {
		value = new char[capacity];
	}

	// 返回字符串长度
	public int length() {
		return count;
	}

	// 返回数组容量
	public int capacity() {
		return value.length;
	}

	/**
	 * 扩容
	 * 
	 * @param minimumCapacity
	 *            最小期望容量。
	 */
	public void ensureCapacity(int minimumCapacity) {
		// 确保扩容至少等于规定的最低。
		if (minimumCapacity > 0)
			ensureCapacityInternal(minimumCapacity);
	}

	// 判断扩容的大小是否比扩容前的大
	private void ensureCapacityInternal(int minimumCapacity) {
		// overflow-conscious code
		if (minimumCapacity - value.length > 0) {
			// 扩容相当于新建字符数字
			// 复制指定的数组
			value = Arrays.copyOf(value, newCapacity(minimumCapacity));
		}
	}

	// 最大扩容大小 MAX_VALUE-8 数组分配的最大大小 ,如果超过要求数组大小超过VM的限制
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	// 开始扩容
	private int newCapacity(int minCapacity) {
		// 首先默认扩容为 （原容量的二倍）+2
		int newCapacity = (value.length << 1) + 2;
		// 如果默认扩容比 传入的容量消就取传入的容量
		if (newCapacity - minCapacity < 0) {
			newCapacity = minCapacity;
		}
		// 扩容大小比最大扩容小就直接返回扩容大小
		return (newCapacity <= 0 || MAX_ARRAY_SIZE - newCapacity < 0) ? hugeCapacity(minCapacity) : newCapacity;
	}

	/**
	 * 大容量的处理 Integer.MAX_VALUE - 8 < minCapacity< Integer.MAX_VALUE 为什么会有这个方法 因为
	 * 前面判断的最大容量是int的最大值减8，也就是说还空余8个容量 如果 想要扩容的大小比 int的最大值小，就取想要扩容的大小
	 * 
	 * @param minCapacity
	 * @return
	 */
	private int hugeCapacity(int minCapacity) {
		// 如果 传入容量比VM支持的最大int值大，就会抛错
		if (Integer.MAX_VALUE - minCapacity < 0) { // overflow
			throw new OutOfMemoryError();
		}
		return (minCapacity > MAX_ARRAY_SIZE) ? minCapacity : MAX_ARRAY_SIZE;
	}

	/**
	 * 字符串减少存储空间 如果实际长度小于容量，为了减少存储空间，就把容量缩小为刚好满足字符串长度。
	 */
	public void trimToSize() {
		if (count < value.length) {
			value = Arrays.copyOf(value, count);
		}
	}

	/**
	 * 扩容的方法
	 * 
	 * @param newLength
	 *            新的长度 如果新长度比原长度小，这里就没进行处理，应该是下面继承类实现的
	 */
	public void setLength(int newLength) {
		// 新长度小于0，抛错
		if (newLength < 0)
			throw new StringIndexOutOfBoundsException(newLength);
		// 对value字符串扩容
		ensureCapacityInternal(newLength);
		// 新长度大于字符长度，多出来的长度 就用空格补充
		if (count < newLength) {
			Arrays.fill(value, count, newLength, '\0');
		}

		count = newLength;
	}

	/**
	 * 返回指定位置的字符
	 */
	@Override
	public char charAt(int index) {
		if ((index < 0) || (index >= count))
			throw new StringIndexOutOfBoundsException(index);
		return value[index];
	}

	/**
	 * 返回指定位置的字符编码
	 * 
	 * @param index
	 * @return
	 */
	public int codePointAt(int index) {
		if ((index < 0) || (index >= count)) {
			throw new StringIndexOutOfBoundsException(index);
		}
		return Character.codePointAt(value, index, count);
	}

	// 将字符从此字符串复制到目标字符数组。
	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
		if (srcBegin < 0)
			throw new StringIndexOutOfBoundsException(srcBegin);
		if ((srcEnd < 0) || (srcEnd > count))
			throw new StringIndexOutOfBoundsException(srcEnd);
		if (srcBegin > srcEnd)
			throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
		System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
	}

	// 替换指定位置的字符
	public void setCharAt(int index, char ch) {
		if ((index < 0) || (index >= count))
			throw new StringIndexOutOfBoundsException(index);
		value[index] = ch;
	}

	// 使用给定 String 中的字符替换此序列的子字符串中的字符
	public MyAbstractStringBuilder replace(int start, int end, String str) {
		if (start < 0)
			throw new StringIndexOutOfBoundsException(start);
		if (start > count)
			throw new StringIndexOutOfBoundsException("start > length()");
		if (start > end)
			throw new StringIndexOutOfBoundsException("start > end");

		if (end > count)
			end = count;
		// 输入字符串长度
		int len = str.length();
		// 新字符串长度
		int newCount = count + len - (end - start);
		// 扩容 到新字符串长度
		ensureCapacityInternal(newCount);
		// 扩容后，在原字符串中留出 放str字符串的位置，end后面的字符向后移
		// "abcdefg"->cde替换为 1234 start = 2 end=5
		// abcdefg->abcdeffg
		System.arraycopy(value, end, value, start + len, count - end);
		// str.getChars(value, start); 这是原方法.
		// 但是 由于这个方法是String的'friendly'方法 在外部包中无法调用
		// 将abcdeffg中的 cdef 替换为1234
		str.getChars(0, str.length(), value, start);
		count = newCount;
		return this;
	}

	// 截取指定长度字符
	// new了一个新的对象
	// 返回的是String而不是本身对象
	public String substring(int start, int end) {
		if (start < 0)
			throw new StringIndexOutOfBoundsException(start);
		if (end > count)
			throw new StringIndexOutOfBoundsException(end);
		if (start > end)
			throw new StringIndexOutOfBoundsException(end - start);
		// 从何处开始，复制几个字符
		return new String(value, start, end - start);
	}

	// 追加操作 字符串
	MyAbstractStringBuilder append(String asb) {
		// 如果字符串为空，就在原字符串后面添加“null”；
		if (asb == null)
			return appendNull();
		int len = asb.length();
		ensureCapacity(len + count);
		asb.getChars(0, len, value, count);
		count += len;
		return this;
	}

	// 追加对象
	MyAbstractStringBuilder append(MyAbstractStringBuilder asb) {
		if (asb == null)
			return appendNull();
		int len = asb.length();
		ensureCapacity(len + count);
		asb.getChars(0, len, value, count);
		count += len;
		return this;
	}

	private MyAbstractStringBuilder appendNull() {
		int c = count;
		ensureCapacityInternal(c + 4);
		// 数据实际上传递的是引用的地址
		final char[] value = this.value;
		value[c++] = 'n';
		value[c++] = 'u';
		value[c++] = 'l';
		value[c++] = 'l';
		count = c;
		return this;
	}

	public MyAbstractStringBuilder delete(int start, int end) {
		if (start < 0)
			throw new StringIndexOutOfBoundsException(start);
		if (end > count)
			end = count;
		if (start > end)
			throw new StringIndexOutOfBoundsException();
		int len = end - start;
		if (len > 0) {
			// 神奇的方法啊
			// 例如'abcdefgh' 去掉 2-4之间的字符
			// 用下面的方法得到的字符串是: abefghgh
			// 注意此时得到的并不是 abefgh
			// 但是这个方法就再对字符数组没有进行任何处理
			// 原理就是：内存中存储的字符串是 abefghgh
			// 但是count却减少了，count是字符串的个数
			// 所以在真正展示的时候,toString方法只是读取到count的位置
			// 最后的 gh 并没有读取,但是它确实时存在的
			System.arraycopy(value, start + len, value, start, count - end);
			count -= len;
		}
		return this;
	}

	// 移除指定位置上的字符串
	public MyAbstractStringBuilder deleteCharAt(int index) {
		if ((index < 0) || (index >= count))
			throw new StringIndexOutOfBoundsException(index);
		// 与delete（）方法原理相同
		System.arraycopy(value, index + 1, value, index, count - index - 1);
		count--;
		return this;
	}

	// 反转数组
	public MyAbstractStringBuilder reverse() {
		boolean hasSurrogates = false;
		int n = count - 1;
		for (int j = (n - 1) >> 1; j >= 0; j--) {
			int k = n - j;
			char cj = value[j];
			char ck = value[k];
			value[j] = ck;
			value[k] = cj;
			// 确定指定的 char 值对是否为有效的代理项对。
			// 判断是否有代理项对
			if (Character.isSurrogate(cj) || Character.isSurrogate(ck)) {
				hasSurrogates = true;
			}

		}
		if (hasSurrogates) {
			reverseAllValidSurrogatePairs();
		}
		return this;
	}
	//对代理项进行反转
	//代理项由两项组成，高代理项和低代理项两部分组成
	private void reverseAllValidSurrogatePairs() {
		for(int i = 0;i<count -1;i++) {
			char c2 = value[i];
			//因为经过反转，所以前面是低代理项
			if (Character.isLowSurrogate(c2)) {
				char c1 = value[i+1];
				if(Character.isHighSurrogate(c1)) {
					value[i++] = c1;
					value[i]=c2;
				}
				 
			}
		}
	}
	//指定位置插入字符
	public MyAbstractStringBuilder insert(int offset, char c) {
        ensureCapacityInternal(count + 1);
        //指定位置后面的字符向后覆盖，空出一个地方放要放的字符
        System.arraycopy(value, offset, value, offset + 1, count - offset);
        value[offset] = c;
        count += 1;
        return this;
    }
    @Override
    public CharSequence subSequence(int start, int end) {
        return substring(start, end);
    }
    @Override
    public MyAbstractStringBuilder append(CharSequence s) {
        if (s == null)
            return appendNull();
        if (s instanceof String)
            return this.append((String)s);
        if (s instanceof MyAbstractStringBuilder)
            return this.append((MyAbstractStringBuilder)s);
        return this.append(s, 0, s.length());
    }
    @Override
    public MyAbstractStringBuilder append(CharSequence s, int start, int end) {
        if (s == null)
            s = "null";
        if ((start < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException(
                "start " + start + ", end " + end + ", s.length() "
                + s.length());
        int len = end - start;
        ensureCapacityInternal(count + len);
        for (int i = start, j = count; i < end; i++, j++)
            value[j] = s.charAt(i);
        count += len;
        return this;
    }
    @Override
    public MyAbstractStringBuilder append(char c) {
        ensureCapacityInternal(count + 1);
        value[count++] = c;
        return this;
    }
    @Override
	public abstract String toString();
    
    final char[] getValue() {
    	return value;
    }
}