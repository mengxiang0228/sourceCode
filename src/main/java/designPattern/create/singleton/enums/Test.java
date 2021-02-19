package designPattern.create.singleton.enums;

public class Test {
	public static void main(String[] args) {
		Resource resource = Singleton.INSTANCE.getInstance();
		System.out.println();
		Resource resource2 = Singleton.myone.getInstance();
		
		System.out.println();
		Resource resource3 = Singleton.mytwo.getInstance();
	
		System.out.println(resource.toString());
		System.out.println(resource2.toString());
		System.out.println(resource3.toString());
	}
}
