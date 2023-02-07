package designPattern.建造者模式;


import lombok.Data;

/**
 * 产品
 *
 * @author liyaxiang
 * @since 2021/5/18 15:14
 */
@Data
public class Product {
    private String partA;
    private String partB;
    private String partC;

    public void show(){
        // 显示产品特性
    }
}
