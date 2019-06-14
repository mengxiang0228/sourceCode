package designPattern.Factory;

public class FactoryPatternDemo {

	public static void main(String[] args) {
		//创建工厂类
		ColorFactory colorFactory = new ColorFactory();
		
		Color color = colorFactory.getColor("red");
		color.fill();
		
		Color color2 = colorFactory.getColor("green");
		color2.fill();
	}

}
