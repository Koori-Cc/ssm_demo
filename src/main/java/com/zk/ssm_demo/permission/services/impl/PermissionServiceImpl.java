package com.zk.ssm_demo.permission.services.impl;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.permission.daos.PermissionDao;
import com.zk.ssm_demo.permission.entities.Permission;
import com.zk.ssm_demo.permission.services.PermissionService;
import com.zk.ssm_demo.role.entities.Role;
import com.zk.ssm_demo.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
@Service("ssm_demo_permission_service")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    public void insertPermission(Permission permission) {
        String id = UUIDUtils.getUUID();
        permission.setId(id);
        permissionDao.insertPermission(permission);
    }

    public List<Permission> queryPermissionList() {
        return permissionDao.queryPermissionList();
    }

    public PaginationVO<Permission> paging(Permission permission, PaginationVO<Permission> vo) {
        Long page = vo.getPage();
        Long pageSize = vo.getPageSize();
        Long startRow = (page-1) * pageSize + 1;   //开始的行数
        Long endRow = pageSize * page;   //结束的行数

        vo.setStartRow(startRow);
        vo.setEndRow(endRow);

        List<Permission> dataList = permissionDao.queryPermissionListForPaging(permission,vo);
        Long total = permissionDao.queryTotalCount(permission);

        Long pageCount = total / vo.getPageSize();
        if(total % vo.getPageSize() > 0) {
            pageCount++;
        }

        vo.setTotal(total);
        vo.setDataList(dataList);
        vo.setPageCount(pageCount);
        return vo;
    }


    public void deletePermissionById(String id) {
        permissionDao.deletePermissionById(id);
    }

    public Permission queryPermissionById(String id) {
        return permissionDao.queryPermissionById(id);
    }

    public void updatePermission(Permission permission) {
        permissionDao.updatePermission(permission);
    }

    public List<Permission> queryRolePermissionRelation(String roleId) {
        return permissionDao.queryRolePermissionRelation(roleId);
    }

    public List<Permission> queryPermissionListByRoleList(List<Role> roleList) {

        List<String> idList = new ArrayList<String>();
        for(int i = 0; i < roleList.size();i++) {
            idList.add(roleList.get(i).getId());
        }
        //可能会有重复
        List<Permission> oldList = permissionDao.queryPermissionByRoleIds(idList);

        //实体类需要重写hashcode和equals
        Set<Permission> set = new HashSet<Permission>();
        for (int i = 0; i < oldList.size(); i++) {
            set.add(oldList.get(i));
        }
        //将不重复的数据返回
        return new ArrayList<Permission>(set);
    }
}
