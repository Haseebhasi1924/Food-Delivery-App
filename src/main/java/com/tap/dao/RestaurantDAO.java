package com.tap.dao;

import com.tap.entity.Restaurant;
import java.util.List;

public interface RestaurantDAO {
    Restaurant save(Restaurant restaurant);
    Restaurant findById(Integer restaurantId);
    List<Restaurant> findAll();
    Restaurant update(Restaurant restaurant);
    void delete(Integer restaurantId);
    
    // Business specific methods
    List<Restaurant> findByCuisineType(String cuisineType);
    List<Restaurant> findByRatingGreaterThan(Float rating);
    List<Restaurant> findActiveRestaurants();
    List<Restaurant> searchByName(String name);
    List<Restaurant> findByAdminUserId(Integer adminUserId);
    void updateRating(Integer restaurantId, Float newRating);
}
