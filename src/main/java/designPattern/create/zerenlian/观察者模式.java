package designPattern.create.zerenlian;


import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 *
 * @author liyaxiang
 * @since 2022/11/1 14:33
 */
public class 观察者模式 {
    public static void main(String[] args) {
        ObserverServer observerServer = new ObserverServer();
        observerServer.addObserve(new User("张"));
        observerServer.addObserve(new User("李"));
        observerServer.setInfomation("a");
    }
}

interface Observe{
    void update(String message);
}

class User implements  Observe{

    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("观察者："+name+", 消息:"+message);
    }
}

interface ObserverAble{
    void addObserve(Observe o);
    void notifyObserve();
}

class ObserverServer implements  ObserverAble{
    String message = null;
    List<Observe> list = new ArrayList<>();
    @Override
    public void addObserve(Observe o) {
        list.add(o);
    }

    @Override
    public void notifyObserve() {
        for (Observe observe : list) {
            observe.update("测试");
        }
    }

    public void setInfomation(String s) {
        this.message = s;
        System.out.println("微信服务更新消息： " + s);
        //消息更新，通知所有观察者
        notifyObserve();
    }

}