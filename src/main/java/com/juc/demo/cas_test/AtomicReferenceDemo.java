package com.juc.demo.cas_test;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {

    public static void main(String[] args) {

        User u1 = new User("zhangsan", 18);
        User u2 = new User("lisi", 18);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(u1);

        System.out.println(atomicReference.compareAndSet(u1, u2) + "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(u1, u2) + "\t" + atomicReference.get().toString());


    }
}


@Data
@ToString
@AllArgsConstructor
class User {
    String userName;
    int age;
}


