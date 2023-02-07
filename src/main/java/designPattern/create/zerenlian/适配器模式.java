package designPattern.create.zerenlian;


/**
 * @author liyaxiang
 * @since 2022/11/1 14:59
 */
public class 适配器模式 {
    public static void main(String[] args) {
        IV110 adapterV = new AdapterV();
        adapterV.getV110();
    }
}

interface IV110{
    void getV110();
}

class V110 implements IV110{
    @Override
    public void getV110() {
        System.out.println("110");
    }
}
class V220{
    void getV220(){
        System.out.println("220");
    }
}

class AdapterV extends V220 implements IV110{

    @Override
    public void getV110() {
        System.out.println("视频转换110");
    }
}