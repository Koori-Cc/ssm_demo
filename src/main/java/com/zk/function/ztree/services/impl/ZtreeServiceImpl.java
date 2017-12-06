package com.zk.function.ztree.services.impl;

import com.zk.function.ztree.daos.ZtreeDao;
import com.zk.function.ztree.entities.Ztree;
import com.zk.function.ztree.services.ZtreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/12/5 9:16
 * @description
 */
@Service("function_ztree_service")
public class ZtreeServiceImpl implements ZtreeService {

    @Autowired
    private ZtreeDao ztreeDao;

    public List<Ztree> getAllList() {
        return ztreeDao.getAllList();
    }
}
