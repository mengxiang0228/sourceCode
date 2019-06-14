package sourceCode.String.MyAbstractStringBuilder;

public class Test {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();    //获取开始时间
//		doSomething();
//		doSomethingOne();    //测试的代码段
		doSomethingTwo();
		long endTime = System.currentTimeMillis();    //获取结束时间

		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间

	}
	@SuppressWarnings("unused")
	public static void doSomething() {
		String s = "";
		for(int i = 0;i<100000;i++) {
			s +="A";
		}
	}
	public static void doSomethingOne() {
		StringBuilder s = new StringBuilder("");
		for(int i = 0;i<100000;i++) {
			s.append("A");
		}
	}
	public static void doSomethingTwo() {
		StringBuffer s = new StringBuffer("");
		for(int i = 0;i<1000000;i++) {
			s.append("A");
		}
	}
}
