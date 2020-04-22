package com.juc.demo.collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 集合类不安全问题
 * ArrayList
 */
public class ContainerDemo {
    public static void main(String[] args) {
//        notSafe();


//        //解决方案1：使用Vector
//        vectorTest();


//        // 解决方案2: 使用Collections辅助类
//        collectionsTest();


         //解决方案3: CopyOnWriteArrayList

        copyOnWriteArrayListTest();
    }

    private static void copyOnWriteArrayListTest() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }

    private static void collectionsTest() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }


    /**
     * 故障现象
     * java.util.ConcurrentModificationException
     */
    private static void notSafe() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }


    private static void vectorTest() {
        List<String> list = new Vector<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }
}
