package com.watson.demo.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 10; i++) {
            executorService.submit(new MyThread(String.valueOf(i)));
        }

        System.out.println("执行完了");

    }

    static class MyThread implements Runnable {

        private String threadName;

        MyThread(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
//            try {
//                Thread.sleep(2000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(threadName + ", 线程开始执行");
        }
    }

}
