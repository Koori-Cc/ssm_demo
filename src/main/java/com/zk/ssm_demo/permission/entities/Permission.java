package com.zk.ssm_demo.permission.entities;


/**
 * Created by Koori_Cc on 2017/8/24.
 */
public class Permission {

    private String id;
    private String code;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * +++++++++++++++自定义属性  Start+++++++++++++++
     */
    private String c_name;   //查询条件
    private String c_code;

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    /**
     * +++++++++++++++自定义属性  End+++++++++++++++
     */
}
