package designPattern.create.abstractFactory;
/**
 * 创建一个工厂创造器/生成器类，通过传递形状或颜色信息来获取工厂。
 * @author 李雅翔
 * @date 2017年10月18日
 */
public class FactoryProducer {
	
	public static AbstractFactory getFactory(String choice) {
		if ("SHAPE".equalsIgnoreCase(choice)) {// 形状
			return new ShapeFactory();
		}else if ("COLOR".equalsIgnoreCase(choice)) {
			return new ColorFactory();
		}
		return null;
	}
}
