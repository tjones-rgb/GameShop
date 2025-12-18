package org.yearup.models;

public class Order {
    private int orderId;
    private int userId;

    public Order(int oderId, int userId){
        this.orderId = orderId;
        this.userId = userId;
    }
    public int getOrderId()
    {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }
}
