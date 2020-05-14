package com.watson.demo.multithread.waitnotify;

public class Join {

    public static void main(String[] args) throws Exception {

        Thread preThread = Thread.currentThread();

        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(new Domino(preThread), String.valueOf(i));
            thread.start();
            // 每个线程拥有前一个线程的引用, 需要等待前一个线程终止, 才能从等待中返回
            preThread = thread;

        }

        Thread.sleep(3000L);

        System.out.println(Thread.currentThread().getName() + " terminate.");

    }

    static class Domino implements Runnable {

        private Thread thread;

        Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }

}
