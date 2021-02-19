package designPattern.structure.bridge;


import java.sql.DriverManager;
import java.sql.JDBCType;

/**
 * jdbc桥接模式示例
 * @author liyaxiang
 * @since 2020/8/11 15:31
 */
public class JDBCBridgeDemo {
    public static void main(String[] args) {
        ISQLService service = new MySQLServiceImpl();
        JDBC jdbc = new JDBCImpl(service);
        jdbc.connect();
    }
}

interface ISQLService {
    void connect();
}

class MySQLServiceImpl implements ISQLService {

    @Override
    public void connect() {
        System.out.println("MySQL连接");
    }
}

abstract class JDBC{
    protected ISQLService service;

    public JDBC(ISQLService service) {
        this.service = service;
    }

    public abstract void connect();
}

class JDBCImpl extends  JDBC{

    public JDBCImpl(ISQLService service) {
        super(service);
    }

    @Override
    public void connect() {
        System.out.println("数据库连接开始");
        service.connect();
    }
}