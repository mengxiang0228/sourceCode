package designPattern.create.abstractFactory;

/**
 * 使用 FactoryProducer 来获取 AbstractFactory，通过传递类型信息来获取实体类的对象。
 * @author 李雅翔
 * @date 2017年10月19日
 */
public class FactoryPatternDemo {

	public static void main(String[] args) {
		//获取形状工厂
		AbstractFactory shapeFactory = FactoryProducer.getFactory("shape");
		
		//获取形状为 矩形  的对象
		Shape shape = shapeFactory.getShape("RECTANGLE");
		shape.draw();
		//获取形状为 方形 的对象
		Shape shape2 = shapeFactory.getShape("Square");
		shape2.draw();
		
		AbstractFactory colorFactory = FactoryProducer.getFactory("color");
		
		Color color = colorFactory.getColor("red");
		color.fill();
		
		Color color2 = colorFactory.getColor("green");
		color2.fill();
	}
}
