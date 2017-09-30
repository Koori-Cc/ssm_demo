package com.zk.ssm_demo.role.services.impl;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.role.daos.RoleDao;
import com.zk.ssm_demo.role.entities.Role;
import com.zk.ssm_demo.role.services.RoleService;
import com.zk.ssm_demo.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
@Service("ssm_demo_role_service")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public void insertRole(Role role) {
        String id = UUIDUtils.getUUID();
        role.setId(id);
        roleDao.insertRole(role);
    }

    public List<Role> queryRoleList() {
        return roleDao.queryRoleList();
    }

    public PaginationVO<Role> paging(Role role, PaginationVO<Role> vo) {

        Long page = vo.getPage();
        Long pageSize = vo.getPageSize();
        Long startRow = (page-1) * pageSize + 1;   //开始的行数
        Long endRow = pageSize * page;   //结束的行数

        vo.setStartRow(startRow);
        vo.setEndRow(endRow);

        List<Role> dataList = roleDao.queryRolerListForPaging(role,vo);
        Long total = roleDao.queryTotalCount(role);

        Long pageCount = total / vo.getPageSize();
        if(total % vo.getPageSize() > 0) {
            pageCount++;
        }

        vo.setTotal(total);
        vo.setDataList(dataList);
        vo.setPageCount(pageCount);
        return vo;
    }

    public void deleteRoleById(String id) {
        roleDao.deleteRoleById(id);
    }

    public Role queryRoleById(String id) {
        return roleDao.queryRoleById(id);
    }

    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }
}
