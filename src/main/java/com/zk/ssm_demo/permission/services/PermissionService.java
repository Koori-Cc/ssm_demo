package com.zk.ssm_demo.permission.services;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.permission.entities.Permission;
import org.apache.ibatis.annotations.Param;

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


}
