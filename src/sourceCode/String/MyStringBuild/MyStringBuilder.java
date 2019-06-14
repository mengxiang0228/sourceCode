package sourceCode.String.MyStringBuild;

/**
 * 重写StringBuild 方法
 * @author 李雅翔
 * @date 2017年8月24日
 */
public class MyStringBuilder extends MyAbstractStringBuilder implements java.io.Serializable, CharSequence {

	private static final long serialVersionUID = 2245520430210268523L;

	public MyStringBuilder() {
		// StringBuilder默认创建长度为16的
		super(16);
	}

	public MyStringBuilder(int capacity) {
		super(capacity);
	}

	public MyStringBuilder(String str) {
		super(str.length() + 16);
		append(str);
	}

	public MyStringBuilder append(MyStringBuilder sb) {
		super.append(sb);
		return this;
	}
	//追加信息
	public MyStringBuilder append(String str) {
		super.append(str);
		return this;
	}
	//删除start-end之间的字符
	public MyStringBuilder delete(int start, int end) {
		super.delete(start, end);
		return this;
	}
	//替换
	//直接修改原数组
	public MyStringBuilder replace(int start, int end, String str) {
		super.replace(start, end, str);
		return this;
	}
	//插入字符
	public MyStringBuilder insert(int offset, char c) {
		super.insert(offset, c);
		return this;
	}	
	//反转数组
	public MyStringBuilder reverse() {
		super.reverse();
		return this;
	} 
	//toString
	public  String toString() {
		return new String(value,0,count);
	}
	//序列化对象
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException{
		s.defaultWriteObject();
		s.writeInt(count);
		s.writeObject(value);
	}
	
	private void readObject(java.io.ObjectInputStream s) 
			throws java.io.IOException,ClassNotFoundException{
		s.defaultReadObject();
		count = s.readInt();
		value  = (char[]) s.readObject();
	}

	
}
