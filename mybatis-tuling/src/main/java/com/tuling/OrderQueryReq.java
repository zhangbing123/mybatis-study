package com.tuling;

/**
 * @description:
 * @author: zhangbing
 * @create: 2020-11-26 13:42
 **/
public class OrderQueryReq {

    private String orderName;

    private int pageNum = 1;

    private int pageSize = 10;

    public OrderQueryReq(String orderName, int pageNum, int pageSize) {
        this.orderName = orderName;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
