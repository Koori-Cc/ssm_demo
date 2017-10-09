package com.zk.ssm_demo.permission.daos;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.permission.entities.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
public interface PermissionDao {

    void insertPermission(Permission permission);

    List<Permission> queryPermissionList();

    Long queryTotalCount(Permission permission);

    List<Permission> queryPermissionListForPaging(@Param("permission")Permission permission, @Param("vo")PaginationVO<Permission> vo);

    void deletePermissionById(String id);

    Permission queryPermissionById(String id);

    void updatePermission(Permission permission);

    List<Permission> queryRolePermissionRelation(String roleId);

    List<Permission> queryPermissionByRoleIds(List<String> idList);

}
