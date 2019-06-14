package sourceCode.String.MyString;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 自己实现一个String
 * 
 * @author 李雅翔
 * @date 2017年8月23日
 */
public class MyString implements CharSequence {
	// String 的底层实现是 字符数组，String类调用的大都是value这个成员变量
	private final char value[];
	// 用于哈希值得缓存
	private int hash; // 初始值为0

	/**
	 * 构造方法
	 */
	public MyString() {
		this.value = "".toCharArray();
		// 实际上为这条语句应该是这样 ：this.value = "".value;
		// 但是由于value这个成员变量是String的私有属性，无法再外部类调用，所以用上述方法实现相同效果
	}

	public MyString(String str) {
		this.value = str.toCharArray();
	}

	public MyString(char value[]) {
		this.value = Arrays.copyOf(value, value.length);
	}

	// 用 StringBuffer 初始化时，对同一 buffer 是线程安全的，即初始化 String 的过程中，其它线程不会改变 buffer 的内容
	public MyString(StringBuffer buffer) {
		synchronized (buffer) {
			// this.value = Arrays.copyOf(buffer.getValue(), buffer.length());
			this.value = Arrays.copyOf(buffer.toString().toCharArray(), buffer.length());
		}
	}

	public MyString(StringBuilder builder) {
		this.value = Arrays.copyOf(builder.toString().toCharArray(), builder.length());
	}

	/**
	 * 
	 * @param value
	 * @param offset
	 *            初始偏移
	 * @param count
	 *            取几个字符
	 */
	public MyString(char value[], int offset, int count) {
		if (offset < 0) {
			throw new StringIndexOutOfBoundsException(offset);
		}
		if (count <= 0) {
			if (count < 0) {
				throw new StringIndexOutOfBoundsException(count);
			}
			// 当一个字符也不取时 返回空
			if (offset <= value.length) {
				this.value = null;
				return;
			}
		}
		// Note: offset or count might be near -1>>>1.
		// 是否超出长度
		if (offset > value.length - count) {
			throw new StringIndexOutOfBoundsException(offset + count);
		}
		this.value = Arrays.copyOfRange(value, offset, offset + count);
	}

	public String toString() {
		// String的toStirng方法其实返回的就是他自己本身"this"
		// 下面的方法是为了实现MyString的输出
		String string = "";
		for (int i = 0; i < value.length; i++) {
			string += value[i];
		}
		return string;
	}

	// 判断两个对象是否相同
	public boolean equals(Object anObject) {
		if (this == anObject) {
			// 首先判断两个地址是否相同
			return true;
		}
		if (anObject instanceof MyString) {
			// 判断对象是否和String是同一个类
			MyString anotherString = (MyString) anObject;
			// 得到字符串长度
			int n = value.length;
			// 使用value.length 而不是用length()方法,应该是为了减少方法间的依赖
			if (n == anotherString.value.length) {
				char[] v1 = value;
				char[] v2 = anotherString.value;
				int i = 0;
				while (n-- != 0) {
					// 对每个字符进行比较
					if (v1[i] != v2[i]) {
						// 有一个不符合就返回false
						return false;
					}
					i++;
				}
				return true;
			}
		}
		return false;
	}

	// hash code方法
	public int hashCode() {
		int h = hash;
		// 判断是否计算过哈希值，并且字符串长度大于1
		if (h == 0 && value.length > 0) {
			char val[] = value;
			for (int i = 0; i < value.length; i++) {

				h = 31 * h + val[i];
			}
			hash = h;
		}
		return h;
	}

	// 替换方法
	public MyString replace(char oldChar, char newChar) {
		if (oldChar != newChar) {
			int len = value.length;
			int i = -1;
			char[] val = value;
			// 使用++i而不用i++ 是为了
			// ++i 先自加，在进行判断 当i=3是自加变4，字符串长度为4，此时就可以跳出
			// i++ 先判断，再自加 当i=3，字符串长度为4，比较符合条件，然后自加 变4 ，又会进入，此时val下标最大到3，当val[4]会抛出下标越界
			while (++i < len) {
				// 判断字符串中是否含有要替换的字符，有就跳出
				if (val[i] == oldChar) {
					break;
				}
			}
			// 如果 存在 替换字符
			if (i < len) {
				// 用新字符数组接收替换后的字符串
				char[] buf = new char[len];
				// 先把替换字符前面的字符放进新字符
				for (int j = 0; j < i; j++) {
					buf[j] = val[j];
				}
				while (i < len) {
					char c = val[i];
					// 如果字符等于要替换字符，则将替换字符放入新字符数组
					buf[i] = (c == oldChar) ? newChar : c;
					i++;
				}
				// replace实际上返回的是一个新建的对象
				return new MyString(buf);
			}

		}
		// 如果不存在替换字符 直接返回原对象
		return this;
	}

	/**
	 * 删除前后空格 如果此 String 对象表示一个空字符序列，或者此 String 对象表示的字符序列的第一个和最后一个字符的代码都大于
	 * '\u0020'（空格字符），则返回对此 String 对象的引用。 否则，若字符串中没有代码大于 '\u0020'
	 * 的字符，则创建并返回一个表示空字符串的新 String 对象。s
	 */
	public MyString trim() {
		int len = value.length;
		int st = 0;
		char[] val = value; /* 避免 误操作吧 avoid getfield opcode */
		// 实际比较的是编码,去所有空格
		while ((st < len) && (val[st] <= ' ')) {
			st++;
		}
		while ((st < len) && (val[len - 1] <= ' ')) {
			len--;
		}
		// 如果前后存在空格，就截取中间的字符，否则返回原字符串
		return ((st > 0) || (len < value.length)) ? substring(st, len) : this;

	}

	/**
	 * 截取字符串 核心代码就一句，其他都是判断输入是否正确 实际上又创建了新对象
	 * 
	 * @return
	 */
	public MyString substring(int beginIndex, int endIndex) {
		if (beginIndex < 0) {
			throw new StringIndexOutOfBoundsException(beginIndex);
		}
		if (endIndex > value.length) {
			throw new StringIndexOutOfBoundsException(endIndex);
		}
		// 判断开始位置是否大于结束位置
		int subLen = endIndex - beginIndex;
		if (subLen < 0) {
			throw new StringIndexOutOfBoundsException(subLen);
		}
		return ((beginIndex == 0) && (endIndex == value.length)) ? this : new MyString(value, beginIndex, endIndex);
	}

	/**
	 * 返回指定字符所在位置
	 * 
	 * @param ch
	 *            指定字符
	 * @param fromIndex
	 *            搜索起始位置 还有一个重载方法 indexOf(int ch)，只有起始位置，实际调用的还是这个方法 indexOf(ch, 0)
	 * @return
	 */
	public int indexOf(int ch, int fromIndex) {
		final int max = value.length;
		if (fromIndex < 0) {
			fromIndex = 0;
		} else if (fromIndex >= max) {
			// 超过字符串长度,返回-1
			return -1;
		}
		if (ch < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
			// handle most cases here (ch is a BMP code point or a
			// negative value (invalid code point))意思是
			// 处理大多数情况下这里（ch是BMP代码点或负值（代码点无效））
			// BMP U+0000 and U+FFFF
			// 补充代码点:U+10000 and U+10FFFF.
			// 对于 0 到 0xFFFF（包括 0 和 0xFFFF）范围内的 ch 的值
			// MIN_SUPPLEMENTARY_CODE_POINT='0x010000'
			final char[] value = this.value;
			for (int i = fromIndex; i < max; i++) {
				if (value[i] == ch) {
					return i;
				}
			}
			return -1;
		} else {
			return indexOfSupplementary(ch, fromIndex);
		}
	}

	/**
	 * Handles (rare) calls of indexOf with a supplementary character.
	 * 差不多就相当于特殊字符吧,意思就是说字符超出了char 2个字节的范围了，所以 用 2个字符来表示一个char 也就是 2字节,16位:ffff
	 * 少见,知道有就行
	 */
	private int indexOfSupplementary(int ch, int fromIndex) {
		// 确定指定的代码点是否为从 0x0000 到 0x10FFFF 范围之内的有效 Unicode 代码点值。
		if (Character.isValidCodePoint(ch)) {
			final char[] value = this.value;
			// 返回高代理项代码单元 代表指定的代理对增补字符（Unicode代码点）在UTF-16编码 机翻
			final char hi = Character.highSurrogate(ch);
			final char lo = Character.lowSurrogate(ch);
			final int max = value.length - 1;
			for (int i = fromIndex; i < max; i++) {
				if (value[i] == hi && value[i + 1] == lo) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 字符最后一次出现处的索引
	 * 
	 * @param ch
	 *            字符编码
	 * @param fromIndex
	 *            从指定的索引处开始进行反向搜索
	 * @return
	 */
	public int lastIndexOf(int ch, int fromIndex) {
		if (ch < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
			// handle most cases here (ch is a BMP code point or a
			// negative value (invalid code point))
			final char[] value = this.value;
			// 如果指定索引大于字符串长度，就取字符串长度，反正就是取 指定索引开始位置和字符串长度中晓得
			int i = Math.min(fromIndex, value.length - 1);
			for (; i >= 0; i--) {
				if (value[i] == ch) {
					return i;
				}
			}
			return -1;
		} else {
			return lastIndexOfSupplementary(ch, fromIndex);
		}
	}

	/**
	 * Handles (rare) calls of lastIndexOf with a supplementary character.
	 * 意思和上面从头开始查道理相同
	 */
	private int lastIndexOfSupplementary(int ch, int fromIndex) {
		if (Character.isValidCodePoint(ch)) {
			final char[] value = this.value;
			char hi = Character.highSurrogate(ch);
			char lo = Character.lowSurrogate(ch);
			// value.length - 2只有一种情况会出现，当fromIndex是字符串尾时候
			int i = Math.min(fromIndex, value.length - 2);
			for (; i >= 0; i--) {
				if (value[i] == hi && value[i + 1] == lo) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 两个字符串比较大小 如果参数字符串等于此字符串，则返回值 0；如果此字符串按字典顺序小于字符串参数， 则返回一个小于 0
	 * 的值；如果此字符串按字典顺序大于字符串参数，则返回一个大于 0 的值。
	 * 
	 * @param anotherString
	 * @return
	 */
	public int compareTo(MyString anotherString) {
		int len1 = value.length;
		int len2 = anotherString.value.length;
		int lim = Math.min(len1, len2);
		char v1[] = value;
		char v2[] = anotherString.value;
		int k = 0;
		while (k < lim) {
			char c1 = v1[k];
			char c2 = v2[k];
			if (c1 != c2) {
				// 如果相同位置两个字符不一样，就直接返回结果
				return c1 - c2;
			}
			k++;
		}
		// 如果两个字符中短的字符串的部分都一样，就比较谁的长
		return len1 - len2;

	}

	/**
	 * 是否以指定字符串开头,从指定索引处算开头
	 * 
	 * @param prefix
	 * @param toffset
	 * @return
	 */
	public boolean startsWith(MyString prefix, int toffset) {
		char ta[] = value;
		int to = toffset;
		char pa[] = prefix.toCharArray();
		int po = 0;
		int pc = prefix.toCharArray().length;
		// Note: toffset might be near -1>>>1.
		// 判断起始位置是否正确 开始位置必须大于0
		if ((toffset < 0) || (toffset > value.length - pc)) {
			return false;
		}
		// 判断两个字符串是否相同
		while (--pc >= 0) {
			if (ta[to++] != pa[po++]) {
				return false;
			}
		}
		return true;
	}

	// 转化为字符数组
	public char[] toCharArray() {
		// Cannot use Arrays.copyOf because of class initialization order issues
		char result[] = new char[value.length];
		System.arraycopy(value, 0, result, 0, value.length);
		return result;
	}

	// 判断字符串是否不为空
	public boolean isEmpty() {
		return value.length == 0;
	}

	/**
	 * 返回指定位置的字符
	 */
	public char charAt(int index) {
		if ((index < 0) || (index >= value.length)) {
			throw new StringIndexOutOfBoundsException(index);
		}
		return value[index];
	}

	// 将指定字符插在结尾
	public MyString concat(MyString str) {
		int otherlen = str.length();
		if(otherlen==0) {
			return this;
		}
		int len = value.length;
		//先把数组长度准备好
		char[] buf = Arrays.copyOf(value, len+otherlen);
//		将字符从此字符串复制到目标字符数组。
		str.getChars(buf,len);
		return new MyString(buf);
	}
	//将字符从此字符串复制到目标字符数组。
    void getChars(char dst[], int dstBegin) {
    	//dst是目标数组
        System.arraycopy(value, 0, dst, dstBegin, value.length);
    }
    public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
    	 if (srcBegin < 0) {
             throw new StringIndexOutOfBoundsException(srcBegin);
         }
         if (srcEnd > value.length) {
             throw new StringIndexOutOfBoundsException(srcEnd);
         }
         if (srcBegin > srcEnd) {
             throw new StringIndexOutOfBoundsException(srcEnd - srcBegin);
         }
         System.arraycopy(value, srcBegin, dst, dstBegin,  srcEnd - srcBegin);
    }
	// 得到字符串长度
	public int length() {
		return value.length;
	}

	// 正则匹配直接调用了正则类
	public boolean matches(String regex) {
		return Pattern.matches(regex, this);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return null;
	}

}
