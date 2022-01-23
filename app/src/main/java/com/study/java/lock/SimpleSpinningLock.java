package com.study.java.lock;

import java.util.concurrent.atomic.AtomicReference;

public class SimpleSpinningLock {

    /**
     * 持有锁的线程，null表示锁未被线程持有
     */
    private AtomicReference<Thread> ref = new AtomicReference<>();
    private AtomicReference thread;

    public void lock(){
        System.out.println("尝试获取锁----" + Thread.currentThread());
        Thread currentThread = Thread.currentThread();
        while(!ref.compareAndSet(null, currentThread)){
            //当ref为null的时候compareAndSet返回true，反之为false
            //通过循环不断的自旋判断锁是否被其他线程持有
        }
    }

    public void unLock() {
        System.out.println("释放锁****");
        Thread cur = Thread.currentThread();
        if(ref.get() != cur){
            //exception ...
        }
        ref.set(null);
    }
}
