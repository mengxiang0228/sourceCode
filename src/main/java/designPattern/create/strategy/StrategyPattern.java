package designPattern.create.strategy;

/**
 * 策略模式-示例-最简单
 *
 * @author liyaxiang
 * @since 2020/8/10 15:12
 */
public class StrategyPattern{
    public static void main(String[] args) {
        // 创建策略
        IStrategy vipStrategy = new VIPStrategy();
        IStrategy commonStrategy = new CommonStrategy();
        // 使用策略-构造
        Context context = new Context(vipStrategy);
        // 获取策略结果
        context.getPayMoney();

        context = new Context(commonStrategy);
        // 获取策略结果
        context.getPayMoney();
    }
}


/**
 * 抽象策略类
 */
interface IStrategy {

    /**
     * 获取用户商品价格
     */
    void getUserPayMoney();
}

/**
 * 算法实现策略
 */
class VIPStrategy implements  IStrategy{

    @Override
    public void getUserPayMoney() {
        System.out.println("VIP用户");
    }
}

/**
 * 算法实现策略
 */
class CommonStrategy implements  IStrategy{

    @Override
    public void getUserPayMoney() {
        System.out.println("普通用户");
    }
}

/**
 * 环境类 上下文环境
 */
class Context {
    private IStrategy strategy;

    private Context() {

    }

    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void getPayMoney(){
        this.strategy.getUserPayMoney();
    }
}