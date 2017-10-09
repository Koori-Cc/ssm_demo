package com.zk.ssm_demo.permission.services;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.permission.entities.Permission;
import com.zk.ssm_demo.role.entities.Role;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
public interface PermissionService {

    void insertPermission(Permission permission);

    List<Permission> queryPermissionList();

    public PaginationVO<Permission> paging(Permission permission, PaginationVO<Permission> vo);

    void deletePermissionById(String id);

    Permission queryPermissionById(String id);

    void updatePermission(Permission permission);

    List<Permission> queryRolePermissionRelation(String roleId);   //根据角色的id查询相关联的权限

    /**
     * 根据角色的集合查询权限
     * @param roleList
     * @return
     */
    List<Permission> queryPermissionListByRoleList(List<Role> roleList);


}
