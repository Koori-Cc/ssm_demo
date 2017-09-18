package com.zk.ssm_demo.entities;

import java.util.Date;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
public class User {
    private String id;
    private String name;
    private String password;
    private Integer age;
    private String address;
    private String photo;

    private Date createTime;
    private String s_createTime;   //辅助日期变量

    public String getS_createTime() {
        return s_createTime;
    }

    public void setS_createTime(String s_createTime) {
        this.s_createTime = s_createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
