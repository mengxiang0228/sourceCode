package designPattern.create.builder;


import lombok.Data;

/**
 * 建造者模式
 *
 * @author liyaxiang
 * @since 2020/8/10 16:03
 */
public class BuilderPattern {
    public static void main(String[] args) {
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        // 组装产品
        Product product = director.construct();
        // 查看产品属性
        product.show();

    }
}

/**
 * 产品角色，包含多个部件
 */
@Data
class Product{
    private String partA;
    private String partB;
    private String partC;

    public void show(){
        System.out.println("A:"+partA);
        System.out.println("B:"+partB);
        System.out.println("C:"+partC);
    }
}

/**
 * 抽象构造者模式
 */
abstract class Builder{
    // 创建产品对象
    protected Product product = new Product();
    public abstract void buildPartA();
    public abstract void buildPartB();
    public abstract void buildPartC();

    public Product getProduct(){
        return product;
    }
}

/**
 * 具体建造者
 */
class ConcreteBuilder extends  Builder{

    @Override
    public void buildPartA() {
        product.setPartA("部件A");
    }

    @Override
    public void buildPartB() {
        product.setPartB("部件B");
    }

    @Override
    public void buildPartC() {
        product.setPartC("部件C");
    }
}

/**
 * 指挥者
 */
class Director{
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    /**
     * 组装产品
     * @return
     */
    public Product construct(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return  builder.getProduct();
    }
}