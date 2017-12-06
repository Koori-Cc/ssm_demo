package com.zk.function.ztree.utils;

import com.zk.function.ztree.entities.Ztree;
import com.zk.function.ztree.entities.ZtreeEntity;

/**
 * @author panbing@supcon.com
 * @create 2017/12/5 15:39
 * @description
 */
public class ObjectToEntity {

    public static ZtreeEntity exchange(Ztree obj) {
        ZtreeEntity entity = new ZtreeEntity();
        entity.setId(obj.getId());
        entity.setpId(obj.getpId());
        entity.setName(obj.getName());
        return entity;
    }


}
