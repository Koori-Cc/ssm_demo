package com.zk.ssm_demo.userRoleRelation.entities;

/**
 * @author panbing@supcon.com
 * @create 2017/10/9 9:42
 * @description
 */
public class UserRoleRelation {

    private String id;
    private String role_id;
    private String user_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
