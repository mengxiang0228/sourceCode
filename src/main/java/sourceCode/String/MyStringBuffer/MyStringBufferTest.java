package sourceCode.String.MyStringBuffer;


public class MyStringBufferTest {

	public static void main(String[] args) {
//		StringBuffer stringBuffer = new StringBuffer();
		MyStringBuffer myStringBuffer = new MyStringBuffer("aa");
		myStringBuffer.append("aaa");
		System.out.println(myStringBuffer.replace(0, 2, "123"));
		
	}

}
