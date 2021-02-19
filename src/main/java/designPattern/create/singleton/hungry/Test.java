package designPattern.create.singleton.hungry;

public class Test {
	public static void main(String[] args) {
		Singleton singleton = Singleton.getInstance();
		
		Singleton singleton2 = Singleton.getInstance();
		
		System.out.println("singleton: " + singleton.getNum());
		System.out.println("singleton2: " + singleton2.getNum());
		singleton2.setNum(2);
		System.out.println("singleton: " + singleton.getNum());
		System.out.println("singleton2: " + singleton2.getNum());
		
	}
}
