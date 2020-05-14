package com.watson.demo.multithread.connectionPool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {

    private final LinkedList<Connection> pool = new LinkedList<>();

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/5/14 16:28
     * @Description : 初始化
     */
    public ConnectionPool(int initCnt) {

        if (initCnt > 0) {
            for (int i = 0; i < initCnt; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }

    }

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/5/14 16:31
     * @Description : 释放连接池
     */
    public void releaseConnection(Connection connection) {

        if (connection != null) {
            synchronized (pool) {
                // 连接释放回到连接池中
                pool.addLast(connection);
                // 通知其他线程
                pool.notifyAll();
            }
        }

    }

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/5/14 16:32
     * @Description : 在 mills 内尝试获取连接池, 获取不到返回 null
     */
    public Connection fetchConnection(long mills) throws InterruptedException {

        synchronized (pool) {

            // 完全超时的情况
            if (mills <= 0) {
                // 如果线程池是空的, 一直进入等待, 否则返回第一个线程池
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                // 总的时间
                long totalTime = System.currentTimeMillis() + mills;
                // 剩下的时间
                long remainingTime = mills;
                while (pool.isEmpty() && remainingTime > 0) {
                    pool.wait(remainingTime);
                    remainingTime = totalTime - System.currentTimeMillis();
                }

                Connection obj = null;
                if (!pool.isEmpty()) {
                    obj = pool.removeFirst();
                }

                return obj;
            }
        }
    }


}
