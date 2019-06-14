package designPattern.singleton.lazy;

/**
 * 懒汉模式
 * @author 李雅翔
 * @date 2017年10月19日
 */
public class Singleton {
	
	public static Singleton singleton;
	public Singleton() {
		System.out.println("内部私有构造方法");
	}
	
	public static Singleton getInstance() {
		if (singleton == null) {
			singleton = new Singleton(); 
		}
		System.out.println("内部调用方法");
		return singleton;
		
	}
}
