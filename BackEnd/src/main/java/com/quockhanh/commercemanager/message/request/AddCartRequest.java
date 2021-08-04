package com.quockhanh.commercemanager.message.request;

public class AddCartRequest {
    private Long productId;
    private Long userId;
    private Long qty;
    private double price;

    public AddCartRequest(Long productId, Long userId, Long qty, double price) {
        this.productId = productId;
        this.userId = userId;
        this.qty = qty;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
