package com.juc.demo.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是
 * 如果有一个线程象取写共享资源来，就不应该自由其他线程可以对资源进行读或写
 * 总结
 * 读读能共存
 * 读写不能共存
 * 写写不能共存
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int tmp = i;
            new Thread(()->{
                myCache.put(tmp+"",tmp+"");
            },"Thread"+i).start();
        }
        for (int i = 0; i < 5; i++) {
            final int tmp = i;
            new Thread(()->{
                myCache.get(tmp+"");
            },"Thread"+i).start();
        }

    }
}


class MyCache {
    private volatile Map<String,Object> map = new HashMap<>();
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key,Object value){
        readWriteLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t正在写入：" + key);
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t写入完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t正在读取：" + key);
            TimeUnit.MILLISECONDS.sleep(3000);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t读取完成" + o);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void clear(){map.clear();}

}
