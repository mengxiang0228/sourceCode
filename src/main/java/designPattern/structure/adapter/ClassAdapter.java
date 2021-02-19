package designPattern.structure.adapter;


/**
 * 类适配器
 *
 * @author liyaxiang
 * @since 2020/8/11 14:25
 */
public class ClassAdapter {
    public static void main(String[] args) {
        Target target = new ClassAdapterInfo();
        target.add();
    }
}

interface Target{
    void add();
}

/**
 * 适配者接口
 */
class Adaptee{
    public void addMore(){
        System.out.println("适配者调用");
    }
}

/**
 * 类适配器
 */
class ClassAdapterInfo extends Adaptee implements Target{

    @Override
    public void add() {
       addMore();
    }
}
