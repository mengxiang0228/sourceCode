package lyx.test;


import java.util.concurrent.TimeUnit;

/**
 * @author liyaxiang
 * @since 2021/5/11 20:24
 */
public class MyTest {

    public static void main(String[] args) {

        System.out.println(TimeUnit.SECONDS.toNanos(1));
        System.out.println(TimeUnit.MINUTES.toNanos(1));
        System.out.println(TimeUnit.HOURS.toNanos(1));
        System.out.println(TimeUnit.DAYS.toNanos(1));
    }
}
