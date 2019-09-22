package com.mehmetkirazli.testchallenge.model;

public class ProductDetail {
    public String orderDetail, summaryPrice;
    public boolean isVisible;

    public ProductDetail() {
    }

    public ProductDetail(String orderDetail, String summaryPrice, boolean isVisible) {
        this.orderDetail = orderDetail;
        this.summaryPrice = summaryPrice;
        this.isVisible = isVisible;
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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
