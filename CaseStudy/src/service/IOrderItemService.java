package service;

import model.OrderItem;

import java.util.List;

public interface IOrderItemService {
    List<OrderItem> findAll();

    void add(OrderItem newOrderItem);

    void update(long orderId,double price , double sum);

    OrderItem getOrderItemById(int id);
}
