package com.tap.dao;

import com.tap.entity.Order;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDAO {
    Order save(Order order);
    Order findById(Integer orderId);
    List<Order> findAll();
    Order update(Order order);
    void delete(Integer orderId);
    
    // Business specific methods
    List<Order> findByUserId(Integer userId);
    List<Order> findByRestaurantId(Integer restaurantId);
    List<Order> findByStatus(String status);
    List<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    void updateStatus(Integer orderId, String newStatus);
    List<Order> findByPaymentMode(String paymentMode);
}
