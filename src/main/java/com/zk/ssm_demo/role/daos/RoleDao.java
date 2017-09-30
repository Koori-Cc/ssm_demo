package com.zk.ssm_demo.role.daos;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.role.entities.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
public interface RoleDao {

    void insertRole(Role role);

    List<Role> queryRoleList();

    Long queryTotalCount(Role role);

    List<Role> queryRolerListForPaging(@Param("role")Role role, @Param("vo")PaginationVO<Role> vo);

    void deleteRoleById(String id);

    Role queryRoleById(String id);

    void updateRole(Role role);

}
