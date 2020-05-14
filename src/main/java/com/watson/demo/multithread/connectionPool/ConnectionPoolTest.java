package com.watson.demo.multithread.connectionPool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {

    // 初始化数据库连接池
    private static ConnectionPool connectionPool = new ConnectionPool(10);

    // 保证所有 ConnectionRunner 能够同时开始
    private static CountDownLatch start = new CountDownLatch(1);

    // main 线程将会等待所有 ConnectionRunner 结束后才能继续执行
    private static CountDownLatch end;

    public static void main(String[] args) throws Exception {

        // 线程数量
        int threadCount = 10;
        end = new CountDownLatch(threadCount);

        // 每次线程获取的总数
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }

        start.countDown();
        end.await();
        System.out.println("total invoke : " + (threadCount * count));
        System.out.println("got connection : " + got);
        System.out.println("not got connection : " + notGot);

    }

    static class ConnectionRunner implements Runnable {

        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {

            // 每个线程一开始先暂停, 等待 start.countDown() 被调用
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (count > 0) {
                try {
                    // 从连接池中获取连接, 如果 1000 毫秒获取不到, 则返回 null
                    Connection connection = connectionPool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            // 释放连接
                            connectionPool.releaseConnection(connection);
                            // 统计获取到的连接的次数
                            got.incrementAndGet();
                        }
                    } else {
                        // 统计获取不到连接的次数
                        notGot.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    count--;
                }
            }

            end.countDown();
        }
    }

}
