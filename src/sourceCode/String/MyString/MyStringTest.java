package sourceCode.String.MyString;
/**
 * @author 李雅翔
 * @date 2017年8月25日
 */
public class MyStringTest {
	/**
	 *  s+="a";  的实现原理:底层实现是StirngBuilder，并且使用了StringBuilder的两次append方法
	 *  换句话说就是进入StirngBuilder两次,第一次创建StirngBuilder对象，将现在字符串中的字符放进StirngBuilder，调用了一次append
	 *  第二次进入 直接调用append把要添加的字符加入
	 *  在此期间创建了一个StirngBuilder对象，进行了两次append;
	 *  所有它的速度是非常慢的；
	 *  
	 */
	public static char[] value = {'A','B','C','D','E','B'};
	public static void main(String[] args) {
		char[] cs = {'\uffff','\u0010'};
		System.out.println(cs.length);
		MyString string = new MyString("aa");
		string = string.replace('a','c');
		System.out.println(string);
		
//		ReaderString readerString = new ReaderString("aa");

//		System.out.println(readerString.replace('a', 'b'));
//		String string2 = new String("a", true);
	}
	
	
}
