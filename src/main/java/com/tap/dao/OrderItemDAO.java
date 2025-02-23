package com.tap.dao;

import com.tap.entity.OrderItem;
import java.util.List;

public interface OrderItemDAO {
    OrderItem save(OrderItem orderItem);
    OrderItem findById(Integer orderItemId);
    List<OrderItem> findAll();
    OrderItem update(OrderItem orderItem);
    void delete(Integer orderItemId);
    
    // Business specific methods
    List<OrderItem> findByOrderId(Integer orderId);
    List<OrderItem> findByMenuId(Integer menuId);
    void updateQuantity(Integer orderItemId, Integer newQuantity);
    void updateTotalPrice(Integer orderItemId);
}
