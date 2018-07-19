package com.gzy.spider.spiderman.entity;

import java.io.Serializable;

public class User implements Serializable {
    private  String id;
    private String username;
    private String mobile;
    private Integer age;
    private Integer sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public User() {
    }

    public User(String id, String username, String mobile, Integer age, Integer sex) {
        this.id = id;
        this.username = username;
        this.mobile = mobile;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
