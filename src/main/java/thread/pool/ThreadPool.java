package thread.pool;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程池类，线程管理器
 *
 * @author liyaxiang
 * @since 2021/5/11 20:56
 */
public class ThreadPool {
    // 工作线程数
    private static int workerNum = 5;
    // 工作线程池
    private WorkThread[] workThreads;
    private static volatile  int finishedTask = 0;
    // 任务队列
    private List<Runnable> taskQueue = new LinkedList<>();
    private static ThreadPool threadPool;

    private ThreadPool() {
        this(5);
    }

    private ThreadPool(int workerNum) {
        ThreadPool.workerNum = workerNum;
        workThreads = new WorkThread[workerNum];
        for (int i = 0; i < workThreads.length; i++) {
            workThreads[i] = new WorkThread();
            workThreads[i].setThreadNum(i);
            workThreads[i].start();// 开启线程池中的线程
        }
    }

    /**
     * 单例模式创建线程池
     * @return
     */
    public static ThreadPool getThreadPool(){
        return getThreadPool(ThreadPool.workerNum);
    }

    /**
     * 单例模式创建线程池
     * @param workerNum
     * @return
     */
    public static ThreadPool getThreadPool(int workerNum){
        if(workerNum <= 0){
            workerNum = ThreadPool.workerNum;
        }
        if(threadPool == null){
            // 线程不安全
            threadPool = new ThreadPool(workerNum);
        }
        return threadPool;
    }

    /**
     * 执行任务
     * @param task
     */
    public void execute(Runnable task){
        synchronized (taskQueue){
            taskQueue.add(task);
            taskQueue.notify();
        }
    }


    /**
     * 执行任务
     * @param task
     * @param size 任务执行次数
     */
    public void execute(Runnable task, int size){
        synchronized (taskQueue){
            for (int i = 0; i < size; i++) {
                taskQueue.add(task);
            }
            taskQueue.notify();
        }
    }





    /**
     * 销毁线程 需要保证所有任务都执行完成才能销毁
     */
    public void destroy(){
        while (!taskQueue.isEmpty()){// 还有未执行的任务
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < workThreads.length; i++) {
            workThreads[i].stopWorker();
            workThreads[i] = null;
        }
        threadPool = null;
        taskQueue.clear();// 清空任务队列
    }

    public static int getWorkerNum() {
        return workerNum;
    }
    // 返回已完成的任务数
    public static int getFinishedTask() {
        return finishedTask;
    }

    // 返回任务队列的长度，即还没处理的任务个数
    public int getWaitTaskNumber() {
        return taskQueue.size();
    }

    @Override
    public String toString() {
        return "WorkThread number:" + workerNum + "  finished task number:"
                + finishedTask + "  wait task number:" + getWaitTaskNumber();
    }

    /**
     * 工作线程
     */
    class  WorkThread extends Thread{
        // 该工作线程是否有效
        private boolean isRunning = true;
        private int threadNum;
        @Override
        public void run() {
            Runnable r = null;
            while (isRunning){
                synchronized (taskQueue){
                    while (isRunning && taskQueue.isEmpty()){// 任务队列为空
                        try {
                            taskQueue.wait(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!taskQueue.isEmpty()){
                        r = taskQueue.remove(0);
                    }
                    if(r != null){
                        System.out.print("线程："+threadNum+"---   ");
                        r.run();
                    }
                    finishedTask++;
                    r = null;
                }
            }
        }

        public int getThreadNum() {
            return threadNum;
        }

        public void setThreadNum(int threadNum) {
            this.threadNum = threadNum;
        }

        public void stopWorker(){
            isRunning = false;
        }
    }
}

