package com.juc.demo.volatile_demo;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData{
    volatile int num = 0;

    public synchronized void addToSixty(){this.num=60;}

    public void add(){num++;}

    AtomicInteger atomicInteger = new AtomicInteger();

    public void atomicAdd(){ atomicInteger.getAndIncrement();}

}
public class VolatileDemo {
    public static void main(String[] args) {
//        visibilityByVolatile();//验证volatile的可见性
        atomicByVolatile();//验证volatile不保证原子性
    }

    private static void atomicByVolatile() {
        MyData myData = new MyData();

        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    myData.add();
                    myData.atomicAdd();
                }
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t finally num value is "+myData.num);
        System.out.println(Thread.currentThread().getName()+"\t finally atomicnum value is "+myData.atomicInteger);





    }

    private static void visibilityByVolatile() {
        MyData myData = new MyData();


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"  come in");
            try {
                TimeUnit.SECONDS.sleep(3);
                myData.addToSixty();
                System.out.println(Thread.currentThread().getName()+"   datavalue: "+myData.num);
            }catch (Exception e){
                e.printStackTrace();
            }
        },"thread1").start();

        while (myData.num==0){

        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over, num value is " + myData.num);

    }
}
