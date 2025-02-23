package com.tap.util;

import java.math.BigDecimal;

public class CartItem {
    private Integer menuId;
    private Integer restaurantId;
    private String itemName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    
    public CartItem(Integer menuId, Integer restaurantId, String itemName, BigDecimal price) {
        this.menuId = menuId;
        this.restaurantId = restaurantId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = 1;
        this.totalPrice = price;
    }
    
    // Getters and Setters
    public Integer getMenuId() { return menuId; }
    public void setMenuId(Integer menuId) { this.menuId = menuId; }
    
    public Integer getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Integer restaurantId) { this.restaurantId = restaurantId; }
    
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity;
        this.totalPrice = this.price.multiply(new BigDecimal(quantity));
    }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { 
        this.price = price;
        this.totalPrice = price.multiply(new BigDecimal(quantity));
    }
    
    public BigDecimal getTotalPrice() { return totalPrice; }
} 