package designPattern.structure.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理实现
 *
 * @author liyaxiang
 * @since 2020/8/10 17:31
 */
public class DynamicProxy {

    public Subject createDynamicProxy(){
        Subject subject = new RealSubject();
        Subject proxy = null;
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("开始");
                Object result = method.invoke(subject, args);
                System.out.println("结束");
                return result;
            }
        };
        proxy = (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader(), new Class[] {Subject.class}, handler);
        return proxy;
    }
}

class DynamicProxyDemo{
    public static void main(String[] args) {
        Subject proxy = new DynamicProxy().createDynamicProxy();
        proxy.request();
    }
}