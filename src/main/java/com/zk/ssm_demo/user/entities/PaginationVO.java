package com.zk.ssm_demo.user.entities;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/25.
 */
public class PaginationVO {
    private Long pageNo = 1L;
    private Long pageSize = 5L;

    private Long startRow;
    private Long endRow;

    private Long pageCount;
    private Long totalCount;
    private List<User> userList;

    private String c_name;
    private Integer c_age;
    private String c_address;

    /**
     * +++++ Kendo UI Start +++++
     */
    // kendo中的当前页数
    private Long page;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    /**
     * +++++ Kendo UI End +++++
     */


    public Long getStartRow() {
        return (pageNo-1)*pageSize + 1;
    }

    public void setStartRow(Long startRow) {
        this.startRow = startRow;
    }

    public Long getEndRow() {
        return pageSize*pageNo;
    }

    public void setEndRow(Long endRow) {
        this.endRow = endRow;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }



    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public Integer getC_age() {
        return c_age;
    }

    public void setC_age(Integer c_age) {
        this.c_age = c_age;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }
}
