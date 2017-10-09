package com.zk.ssm_demo.rolePermissionRelation.entities;

/**
 * @author panbing@supcon.com
 * @create 2017/10/9 9:42
 * @description
 */
public class RolePermissionRelation {

    private String id;
    private String role_id;
    private String permission_id;

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

    public String getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(String permission_id) {
        this.permission_id = permission_id;
    }
}
