package com.zk.ssm_demo.role.services;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.role.entities.Role;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
public interface RoleService {

    void insertRole(Role role);

    List<Role> queryRoleList();

    public PaginationVO<Role> paging(Role role, PaginationVO<Role> vo);

    void deleteRoleById(String id);

    Role queryRoleById(String id);

    void updateRole(Role role);

    List<Role> queryRoleUserRelation(String userId);

    List<Role> queryRoleUserNoRelation(String userId);

    void addUserRoleRel(String roleId,String userId);

}
