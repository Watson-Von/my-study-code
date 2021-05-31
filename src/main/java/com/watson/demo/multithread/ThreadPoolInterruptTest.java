package com.watson.demo.multithread;

import java.util.concurrent.*;

public class ThreadPoolInterruptTest {

    private static ExecutorService executorService = new ThreadPoolExecutor(20, 20,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        Future<?> submit = executorService.submit(new MyThread());
        try {
            Thread.sleep(1L);
            submit.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    static class MyThread implements Runnable {
        boolean stop = false;

        @Override
        public void run() {
            while (!stop) {
                if (Thread.interrupted()) {
                    stop = true;
                    System.out.println("线程中断了!!!!!!!");
                    System.out.println(Thread.currentThread().isInterrupted());
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("线程真的中断了!!!!!!!");
                    }
                } else {
                    System.out.println("线程没有中断");
                }
            }

        }
    }

}
