package designPattern.create.zerenlian;


/**
 * 策略
 *
 * @author liyaxiang
 * @since 2022/11/1 14:24
 */
public class 策略模式 {
    public static void main(String[] args) {
        StrategyHandle strategyHandle = new StrategyHandle();
        strategyHandle.add(1);
    }
}

interface Strategy {
    void add();
}

class Strategy1 implements Strategy {

    @Override
    public void add() {
        System.out.println("a");
    }
}

class StrategyHandle{
    public void add(Integer value){
        Strategy strategy = null;
        switch (value){
            case 1:
                strategy = new Strategy1();
                break;

            case 2:
                break;
        }
        strategy.add();
    }
}
