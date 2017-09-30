package com.zk.ssm_demo.permission.services.impl;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.permission.daos.PermissionDao;
import com.zk.ssm_demo.permission.entities.Permission;
import com.zk.ssm_demo.permission.services.PermissionService;
import com.zk.ssm_demo.utils.UUIDUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
