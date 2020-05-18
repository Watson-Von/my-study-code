package com.watson.demo.multithread.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/5/18 9:57
 * @Description : 自定义共享锁
 */
public class MyShareLock {

    private final Sync sync = new Sync(2);

    public void lock() {
        sync.tryAcquireShared(1);
    }

    public void unLock() {
        sync.tryReleaseShared(1);
    }

    private static final class Sync extends AbstractQueuedSynchronizer {

        Sync(int shareCnt) {

            if (shareCnt < 0) {
                throw new IllegalArgumentException("shareCnt must large than zero");
            }

            setState(shareCnt);

        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for (; ; ) {
                int currentState = getState();
                int i = currentState - reduceCount;
                if (i < 0 || compareAndSetState(currentState, i)) {
                    return i;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (; ; ) {
                int currentState = getState();
                int i = currentState + returnCount;
                if (compareAndSetState(currentState, i)) {
                    return true;
                }
            }
        }

    }

}
