package sourceCode.String.MyStringBuild;

import sourceCode.String.MyString.MyString;

public class MyStringBuilderTest {
	public static void main(String[] args) {
		MyStringBuilder myStringBuilder = new MyStringBuilder();
		myStringBuilder.append("abcdefg");
		System.out.println(myStringBuilder);
		System.out.println(myStringBuilder.replace(2, 4, "123"));
		System.out.println(myStringBuilder);
		System.out.println(myStringBuilder.delete(2,4));
		System.out.println(myStringBuilder.getValue());


		long startTime = System.currentTimeMillis();    //获取开始时间
		doString();
//		doMyString();
//		doMyStringBuilder();    //测试的代码段
//		doSomethingTwo();
		long endTime = System.currentTimeMillis();    //获取结束时间
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
	}
	public static void doString() {
		String s = new String();
//		MyString s1 = new MyString("A");
		for(int i = 0;i<100000;i++) {
			s.concat("A");
//			s+="A";
		}
	}
	//自写String方法
	public static void doMyString() {
		MyString s = new MyString();
		MyString s1 = new MyString("A");
		for(int i = 0;i<100000;i++) {
			s.concat(s1);
		}
	}
	
	//自写StringBuilder
	public static void doMyStringBuilder() {
		MyStringBuilder s = new MyStringBuilder("");
		for(int i = 0;i<100000;i++) {
			s.append("A");
		}
	}

}
