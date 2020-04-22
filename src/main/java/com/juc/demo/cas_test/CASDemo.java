package com.juc.demo.cas_test;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    public static void main(String[] args) {
        checkCAS();
    }

    private static void checkCAS() {
        AtomicInteger atomicInteger = new AtomicInteger(3);
        System.out.println(atomicInteger.compareAndSet(3,2019) + "\t current data is " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(3,2020) + "\t current data is " + atomicInteger.get());
    }
}
