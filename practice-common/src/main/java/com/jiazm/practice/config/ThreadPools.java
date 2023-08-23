package com.jiazm.practice.config;

import java.util.concurrent.*;

/**
 * @author jiazm3
 * @date 2022/2/28 13:58
 */
public class ThreadPools {
    /**
     * @Author jiazm3
     * @Des  线程池创建
     * corePoolSize:指定了线程池中的线程数量，它的数量决定了添加的任务是开辟新的线程去执行，还是放到workQueue任务队列中去；
    maximumPoolSize:指定了线程池中的最大线程数量，这个参数会根据你使用的workQueue任务队列的类型，决定线程池会开辟的最大线程数量；
    keepAliveTime:当线程池中空闲线程数量超过corePoolSize时，多余的线程会在多长时间内被销毁；
    unit:keepAliveTime的单位
    workQueue:任务队列，被添加到线程池中，但尚未被执行的任务；它一般分为直接提交队列、有界任务队列、无界任务队列、优先任务队列几种；
    threadFactory:线程工厂，用于创建线程，一般用默认即可；
    handler:拒绝策略；当任务太多来不及处理时，如何拒绝任务；
     * @Date  2021/5/27  15:54
     * @Param
     * @return
     **/
    public static final ExecutorService exec = new ThreadPoolExecutor (
            10,
            10,
            0L,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1024),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            },
            new ThreadPoolExecutor.AbortPolicy());


    public ThreadPools() {
    }

}
