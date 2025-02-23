package com.tap.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    private Integer restaurantId;
    private String restaurantName;
    
    public Cart() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(CartItem item, String restaurantName) {
        if (items.isEmpty()) {
            this.restaurantId = item.getRestaurantId();
            this.restaurantName = restaurantName;
            items.add(item);
        } else if (restaurantId.equals(item.getRestaurantId())) {
            for (CartItem existingItem : items) {
                if (existingItem.getMenuId().equals(item.getMenuId())) {
                    existingItem.setQuantity(existingItem.getQuantity() + 1);
                    return;
                }
            }
            items.add(item);
        } else {
            items.clear();
            this.restaurantId = item.getRestaurantId();
            this.restaurantName = restaurantName;
            items.add(item);
        }
    }
    
    public void updateItemQuantity(Integer menuId, Integer quantity) {
        for (CartItem item : items) {
            if (item.getMenuId().equals(menuId)) {
                item.setQuantity(quantity);
                return;
            }
        }
    }
    
    public void removeItem(Integer menuId) {
        items.removeIf(item -> item.getMenuId().equals(menuId));
        if (items.isEmpty()) {
            restaurantId = null;
            restaurantName = null;
        }
    }
    
    public void clear() {
        items.clear();
        restaurantId = null;
        restaurantName = null;
    }
    
    public BigDecimal getTotal() {
        return items.stream()
                   .map(CartItem::getTotalPrice)
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public BigDecimal getDeliveryCharge() {
        if (items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal("40.00");
    }
    
    public BigDecimal getFinalTotal() {
        return getTotal().add(getDeliveryCharge());
    }
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public Integer getRestaurantId() {
        return restaurantId;
    }
    
    public String getRestaurantName() {
        return restaurantName;
    }
    
    public boolean hasItem(Integer menuId) {
        if (items == null) return false;
        
        for (CartItem item : items) {
            if (item.getMenuId().equals(menuId)) {
                return true;
            }
        }
        return false;
    }
}