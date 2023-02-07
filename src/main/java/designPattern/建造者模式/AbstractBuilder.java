package designPattern.建造者模式;


/**
 * 抽象建造者
 *
 * @author liyaxiang
 * @since 2021/5/18 15:17
 */
public abstract class AbstractBuilder {

    public Product product = new Product();

    public abstract void buildPartA();
    public abstract void buildPartB();
    public abstract void buildPartC();

    abstract Product getProduct();
}
