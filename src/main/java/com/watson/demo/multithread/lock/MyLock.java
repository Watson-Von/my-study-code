package com.watson.demo.multithread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/5/15 15:22
 * @Description : 自定义同步组件
 */
public class MyLock {

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public void unLock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return sync.tryAcquireNanos(1, timeUnit.toNanos(timeout));
    }

    private static class Sync extends AbstractQueuedSynchronizer {

        /**
         * @author : fengHangWen
         * @CreateDate : 2020/5/15 14:13
         * @Description : 是否处于占用状态
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * @author : fengHangWen
         * @CreateDate : 2020/5/15 14:14
         * @Description : 当状态为 0 的时候获取该锁
         */
        @Override
        protected boolean tryAcquire(int arg) {
            // CAS操作
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }

            return false;
        }

        /**
         * @author : fengHangWen
         * @CreateDate : 2020/5/15 14:15
         * @Description : 释放锁, 将状态设置为 0
         */
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * @author : fengHangWen
         * @CreateDate : 2020/5/15 14:26
         * @Description : 返回一个 Condition, 每个 condition 都包含一个 condition 队列
         */
        Condition newCondition() {
            return new ConditionObject();
        }
    }

}
