package designPattern.abstractFactory;
/**
 * 为 Color 和 Shape 对象创建抽象类来获取工厂。
 * @author 李雅翔
 * @date 2017年10月18日
 */
public abstract class AbstractFactory {
	abstract Color getColor(String color);
	abstract Shape  getShape(String shape);
}
