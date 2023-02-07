package thread.pool;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 线程池测试
 *
 * @author liyaxiang
 * @since 2021/5/11 21:23
 */
public class TestThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Main当前线程id:"+Thread.currentThread().getId());
        String value = "";
        FutureTask future = new FutureTask(() -> {
            System.out.println("Thread当前线程id:"+Thread.currentThread().getId());
            Thread.sleep(1000);
            System.out.println("任务 call 完成");
            return "String";
        });
//        Thread thread =new Thread(future);
//        thread.start();
        future.run();
        System.out.println(2);
        System.out.println(future.get());
        System.out.println(3);
//        // 创建线程池
//        ThreadPool threadPool = ThreadPool.getThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            threadPool.execute(new Task(), 10);
//        }
//        System.out.println(threadPool);
//        threadPool.destroy();
//        System.out.println(threadPool);
    }

    static class Task implements Runnable{

        private static volatile int i = 1;

        @Override
        public void run() {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("任务 " + (i++) + " 完成");
        }
    }
}
