package designPattern.create.zerenlian;


import lombok.Data;

/**
 * @author liyaxiang
 * @since 2022/11/1 14:43
 */
public class 装饰器模式 {
    public static void main(String[] args) {
        Component aTest = new A();
        Component bTest = new ZhuangB(aTest);
        bTest.operation();
    }

}

//抽象构件角色
interface  Component {
    void operation();
}


//具体构件角色
class A implements Component
{
    public void operation()  {
        System.out.println("调用具体构件角色的方法operation()");
    }
}

@Data
abstract class ZhuangBAbstract implements Component{
    public Component component;


    public void operation()  {
        component.operation();
    }
}

class ZhuangB extends ZhuangBAbstract{
    public ZhuangB(Component component) {
        this.component = component;
    }
    public void operation()  {
        super.operation();
        add();
    }
    public void add(){
        System.out.println("补充");
    }
}