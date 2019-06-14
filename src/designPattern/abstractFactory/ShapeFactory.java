package designPattern.abstractFactory;
/**
 * 6 创建扩展了 AbstractFactory 的工厂类，基于给定的信息生成实体类的对象。
 * @author 李雅翔
 * @date 2017年10月18日
 */
public class ShapeFactory extends AbstractFactory {

	@Override
	Shape getShape(String shape) {
		if (shape == null) {
			return null;
		}
		if (shape.equalsIgnoreCase("RECTANGLE")) {//矩形
			return new Rectangle();
		}else if (shape.equalsIgnoreCase("SQUARE")) {// 正方形
			return new Square();
		}
		return null;
	}
	
	@Override
	Color getColor(String color) {
		return null;
	}

}
