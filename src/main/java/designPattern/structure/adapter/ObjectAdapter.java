package designPattern.structure.adapter;


/**
 * 对象适配器
 *
 * @author liyaxiang
 * @since 2020/8/11 14:32
 */
public class ObjectAdapter {
    public static void main(String[] args) {
        Target target = new ObjectAdapterInfo(new Adaptee());
        target.add();
    }
}

/**
 * 对象适配器；饿哦
 */
class ObjectAdapterInfo implements  Target{
    private Adaptee adaptee;

    public ObjectAdapterInfo(Adaptee adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public void add() {
        adaptee.addMore();
    }
}
