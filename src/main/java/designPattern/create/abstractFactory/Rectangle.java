package designPattern.create.abstractFactory;

/**
 * 具体接口实体类
 * @author 李雅翔
 * @date 2017年10月19日
 */
public class Rectangle implements Shape {

	@Override
	public void draw() {
		System.out.println("矩形");
	}

}
