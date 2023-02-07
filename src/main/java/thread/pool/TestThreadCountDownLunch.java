package thread.pool;


import lombok.SneakyThrows;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程池测试
 *
 * @author liyaxiang
 * @since 2021/5/11 21:23
 */
public class TestThreadCountDownLunch {
//    static CountDownLatch countDownLatch = new CountDownLatch(5);
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
    static Lock lock = new ReentrantLock();
    public static void main(String[] args) throws ExecutionException, InterruptedException, BrokenBarrierException {
        Thread thread = new Thread(new Task());
        Thread thread2 = new Thread(new Task());
        Thread thread3 = new Thread(new Task());
        thread.start();
        thread2.start();
        thread3.start();
        System.out.println("测试");

    }

    static class Task implements Runnable{

        private static volatile int i = 1;

        @Override
        public void run() {
            int value = i++;
            try{
                lock.lock();
                try{
                    if(value == 1){
                        lock.wait();
                    }else {
                        lock.notify();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("任务 " + value + " 开始");
                try {
                    Thread.sleep(1000*i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                System.out.println("任务 " + value + " 完成");
            }finally {
                lock.unlock();
            }

        }
    }
}
