package com.juc.demo.volatile_demo;



//单机版的单例
class SingletonDemo1 {
    private static SingletonDemo1 instance = null;

    private SingletonDemo1() {
        System.out.println(Thread.currentThread().getName() + "\t 构造方法SingletonDemo1（）");
    }


    public static SingletonDemo1 getInstance() {
        if (instance == null) {
            instance = new SingletonDemo1();
        }

        return instance;
    }
}

//DCL+valatile
class SingletonDemo2 {

    private static  volatile SingletonDemo2 instance = null;

    private SingletonDemo2() {
        System.out.println(Thread.currentThread().getName() + "\t 构造方法SingletonDemo2（）");
    }


    public static SingletonDemo2 getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo2.class) {
                if (instance == null) {
                    instance = new SingletonDemo2();
                }
            }
        }
        return instance;
    }
}

public class SingletonDemo {

    public static void main(String[] args) {

        //存在问题 会多次调用
        testSingeletonDemo1();

        //必须加上volatile禁止指令重排防止线程不安全
        testSingeletonDemo2();

    }

    private static void testSingeletonDemo2() {

        System.out.println("===================");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonDemo2.getInstance();
            }).start();
        }
    }

    private static void testSingeletonDemo1() {
        //        SingletonDemo1 instance1 = SingletonDemo1.getInstance();
//        SingletonDemo1 instance2 = SingletonDemo1.getInstance();
//        System.out.println(instance1 == instance2);
        System.out.println("===================");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonDemo1.getInstance();
            }).start();
        }
    }


}
