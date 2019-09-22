package com.mehmetkirazli.testchallenge.model;

public class Order {
    public String date, month, marketName, orderName, productPrice, productState;
    public ProductDetail productDetail;

    public Order() {
    }

    public Order(String date, String month, String marketName, String orderName, String productPrice, String productState, ProductDetail productDetail) {
        this.date = date;
        this.month = month;
        this.marketName = marketName;
        this.orderName = orderName;
        this.productPrice = productPrice;
        this.productState = productState;
        this.productDetail = productDetail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
}
