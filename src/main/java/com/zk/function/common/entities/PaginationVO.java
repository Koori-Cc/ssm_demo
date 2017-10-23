package com.zk.function.common.entities;

import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/10/13 8:54
 * @description
 */
public class PaginationVO<T> {

    private Long page = 1l;  //当前的页数,默认第一页
    private Long pageSize = 5L;  //每页显示的个数,默认是5个
    private Long pageCount = 0L;  //总页数
    private Long totalCount = 0L;  //总记录数
    //数据的集合
    private List<T> dataList;

    //数据库查询用到的startRow,endRow
    private Long startRow;
    private Long endRow;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
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

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Long getStartRow() {
        return startRow;
    }

    public void setStartRow(Long startRow) {
        this.startRow = startRow;
    }

    public Long getEndRow() {
        return endRow;
    }

    public void setEndRow(Long endRow) {
        this.endRow = endRow;
    }
}
