package com.mehmetkirazli.testchallenge.model;

public class ProductDetail {
    public String orderDetail, summaryPrice;

    public ProductDetail() {
    }

    public ProductDetail(String orderDetail, String summaryPrice) {
        this.orderDetail = orderDetail;
        this.summaryPrice = summaryPrice;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getSummaryPrice() {
        return summaryPrice;
    }

    public void setSummaryPrice(String summaryPrice) {
        this.summaryPrice = summaryPrice;
    }
}
