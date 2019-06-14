package designPattern.singleton.enums;

public enum Singleton {
	INSTANCE,myone,mytwo; 
	Resource resource ;
	private Singleton() {
		System.out.println("私有内部构造方法");
		resource = new Resource();
	}
	public Resource getInstance() {  
		System.out.println("数据流实例化方法");
		return resource;
    } 
}
class Resource{
	public Resource() {
		System.out.println("数据流构造方法");
	}
	
	@Override
	public String toString() {
		
		return "这是一个数据流";
	}
}