package com.gzy.spider.spiderman.proxy;

public class Student implements Person {

    private String name;

    public Student(String name){
        this.name = name;
    }

    @Override
    public void giveMoney() {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name+"交了50班费");
    }
}
