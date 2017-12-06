package com.zk.function.ztree.entities;

/**
 * @author panbing@supcon.com
 * @create 2017/12/5 15:38
 * @description 用于前台展示树的实体类
 */
public class ZtreeEntity {

    private Long id;
    private Long pId;
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
