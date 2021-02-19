package designPattern.structure.bridge;


import com.sun.deploy.net.MessageHeader;

/**
 * 桥接模式
 *
 * @author liyaxiang
 * @since 2020/8/11 15:04
 */
public class BridgeDemo {
    public static void main(String[] args) {
        Implementor implementor = new ImplementorAImpl();
        Abstraction abstraction = new RefinedAbstraction(implementor);
        abstraction.Operation();
    }
}

/**
 *
 */
interface  Implementor{
    void add();
}

/**
 * 具体实现
 */
class ImplementorAImpl implements Implementor{

    @Override
    public void add() {
        System.out.println("具体实现被调用");
    }
}

/**
 * 抽象化
 */
abstract class Abstraction{
    protected Implementor implementor;

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    public abstract  void  Operation();
}

/**
 * 扩展抽象画
 */
class RefinedAbstraction extends  Abstraction{

    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    @Override
    public void Operation() {
        System.out.println("扩展抽象方法被访问");
        implementor.add();
    }
}