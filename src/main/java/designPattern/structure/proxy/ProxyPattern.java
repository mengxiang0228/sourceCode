package designPattern.structure.proxy;


/**
 * 代理模式
 *
 * @author liyaxiang
 * @since 2020/8/10 17:12
 */
public class ProxyPattern {
    public static void main(String[] args) {
        ProxySub proxy = new ProxySub();
        proxy.request();
    }
}

/**
 * 抽象主题
 */
interface Subject{
    void request();
}

/**
 * 真实主题
 */
class RealSubject implements Subject{


    @Override
    public void request() {
        System.out.println("真实主题");
    }
}

class ProxySub implements Subject{

    private RealSubject realSubject;

    @Override
    public void request() {
        if(realSubject == null){
            realSubject = new RealSubject();
        }
        System.out.println("先做一些事");
        realSubject.request();
        System.out.println("最后做一些事");
    }
}