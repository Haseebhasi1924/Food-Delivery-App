package com.tap.dao;

import com.tap.entity.Menu;
import java.math.BigDecimal;
import java.util.List;

public interface MenuDAO {
    Menu save(Menu menu);
    Menu findById(Integer menuId);
    List<Menu> findAll();
    Menu update(Menu menu);
    void delete(Integer menuId);
    
    // Business specific methods
    List<Menu> findByRestaurantId(Integer restaurantId);
    List<Menu> findAvailableItems(Integer restaurantId);
    List<Menu> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    void updateAvailability(Integer menuId, boolean isAvailable);
    List<Menu> searchByItemName(String itemName);
    void updateRating(Integer menuId, Float newRating);
}
