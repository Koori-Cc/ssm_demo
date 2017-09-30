package com.zk.ssm_demo.common.entities;


import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
@Component
public class PaginationVO<T> {

    private Long page = 1L;   //当前页数
    private Long pageSize = 5L;   //每页显示的个数
    private Long pageCount = 0L;   //总页数
    private Long total = 0L;  //总记录数
    private List<T> dataList;   //返回的数据集合

    private Long startRow = 0L;   //开始的行号
    private Long endRow = 0L;   //结束的行号

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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
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
