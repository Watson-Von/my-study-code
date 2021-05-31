package com.watson.demo.multithread;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/5/14 15:13
 * @Description :
 * ThreadLocal, 当前线程的概念, 设置的值只有当前线程可以获取到
 */
public class ThreadLocalTest {

    public static void main(String[] args) throws Exception {

        ThreadLocalTest.begin();
        Thread.sleep(1000L);
        System.out.println("Cost : " + ThreadLocalTest.end() + " mills");

    }

    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<Long>() {

        /**
         * @author : fengHangWen
         * @CreateDate : 2020/5/14 15:00
         * @Description :
         * 此方法表示, 当前线程如果没有调用 set() 方法设置值时, 第一次调用 get() 获取到的初始值
         * 每个线程最多调用一次
         */
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }

    };

    private static void begin() {
        THREAD_LOCAL.set(System.currentTimeMillis());
    }

    private static long end() {
        return System.currentTimeMillis() - THREAD_LOCAL.get();
    }

}
