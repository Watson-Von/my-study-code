package com.watson.demo.multithread;

import java.util.concurrent.*;

public class ThreadPool {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            executorService.submit(new MyThread(String.valueOf(i)));
        }

        for (int i = 0; i < 10; i++) {
            Future<User> submit = executorService.submit(new MyThread2());
            try {
                System.out.println("姓名:" + submit.get().getName() + ", 年龄:" + submit.get().getAge());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        System.out.println("主线程执行完了");

    }

    static class MyThread2 implements Callable<User> {

        @Override
        public User call() {
            return new User("不说", 10);
        }
    }

    static class User {
        private String name;
        private int age;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }

    static class MyThread implements Runnable {

        private String threadName;

        MyThread(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + ", 线程开始执行");
        }
    }

}
