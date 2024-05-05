package org.example;

import java.util.List;

public class CourierOrders {
    List<Order> ordersList;

    public List<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

    public CourierOrders() {
    }

    public CourierOrders(List<Order> ordersList) {
        this.ordersList = ordersList;
    }
}
