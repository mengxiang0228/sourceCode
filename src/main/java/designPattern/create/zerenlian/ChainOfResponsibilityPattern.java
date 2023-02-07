package designPattern.create.zerenlian;


import com.sun.deploy.util.StringUtils;
import lombok.Data;

/**
 * @author liyaxiang
 * @since 2022/11/1 13:54
 */
public class ChainOfResponsibilityPattern {
    public static void main(String[] args) {
        // 组装责任链
        Handle handle1 = new Handle1();
        Handle handle2 = new Handle2();
        handle1.setNext(handle2);
        handle1.handleRequest("two");
    }
}

@Data
abstract class Handle{
    private Handle next;

    public abstract void handleRequest(String request);
}

class Handle1 extends Handle{

    @Override
    public void handleRequest(String request) {
        if(request.equals("one")){
            System.out.println("1处理");
        }else {
            if(getNext() != null){
                getNext().handleRequest(request);
            }else {
                System.out.println("无需处理");
            }
        }
    }
}

class Handle2 extends Handle{

    @Override
    public void handleRequest(String request) {
        if(request.equals("two")){
            System.out.println("2处理");
        }else {
            if(getNext() != null){
                getNext().handleRequest(request);
            }else {
                System.out.println("无需处理");
            }
        }
    }
}