package designPattern.建造者模式;


/**
 * 实际建造者
 *
 * @author liyaxiang
 * @since 2022/11/1 15:10
 */
public class Builder extends AbstractBuilder {

    public static void main(String[] args) {
        Builder builder =new Builder();
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        Product product = builder.getProduct();
        System.out.println(product);
    }
    @Override
    public void buildPartA() {
        product.setPartA("A");

    }

    @Override
    public void buildPartB() {
        product.setPartB("B");

    }

    @Override
    public void buildPartC() {
        product.setPartC("C");
    }

    @Override
    Product getProduct() {
        return product;
    }
}
