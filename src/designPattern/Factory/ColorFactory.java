package designPattern.Factory;
/**
 * 6 创建扩展了 AbstractFactory 的工厂类，基于给定的信息生成实体类的对象。
 * @author 李雅翔
 * @date 2017年10月18日
 */
public class ColorFactory {

	public Color getColor(String color) {
		if (color == null) {
			return null;
		}
		if (color.equalsIgnoreCase("RED")) {
			return new Red();
		}else if (color.equalsIgnoreCase("GREEN")) {
			return new Green();
		}
		return null;
	}

}
