package designPattern.singleton.hungry;

/**
 * 饿汉模式
 * @author 李雅翔
 * @date 2017年10月19日
 */
public class Singleton {
	private int num = 0;
	private static Singleton singleton = new Singleton();
	
	private Singleton() {
		System.out.println("内部私有构造方法");
	}
	
	// 获取实例
	public static Singleton getInstance() {
		System.out.println("获取内部私有对象");
		return singleton;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
