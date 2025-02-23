package com.tap;

import com.tap.dao.RestaurantDAO;
import com.tap.entity.Restaurant;
import com.tap.implementation.RestaurantDAOImpl;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();

        // Test 1: Create a new restaurant
        System.out.println("\n=== Creating new restaurants ===");
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("Taj Hotel");
        restaurant1.setAddress("MG Road, Bangalore");
        restaurant1.setPhone("9876543210");
        restaurant1.setCuisineType("Indian");
        restaurant1.setRating(4.5f);
        restaurant1.setIsActive(true);
        restaurant1.setEta(30);
        restaurant1.setAdminUserId(1);
        restaurant1.setImagePath("/images/taj.jpg");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Pizza Hut");
        restaurant2.setAddress("Jayanagar, Bangalore");
        restaurant2.setPhone("9876543211");
        restaurant2.setCuisineType("Italian");
        restaurant2.setRating(4.0f);
        restaurant2.setIsActive(true);
        restaurant2.setEta(45);
        restaurant2.setAdminUserId(2);
        restaurant2.setImagePath("/images/pizzahut.jpg");

        // Save restaurants
        restaurant1 = restaurantDAO.save(restaurant1);
        restaurant2 = restaurantDAO.save(restaurant2);
        System.out.println("Restaurants created successfully!");

        // Test 2: Find restaurant by ID
        System.out.println("\n=== Finding restaurant by ID ===");
        Restaurant foundRestaurant = restaurantDAO.findById(restaurant1.getRestaurantId());
        System.out.println("Found restaurant: " + foundRestaurant.getName());

        // Test 3: Find all restaurants
        System.out.println("\n=== Finding all restaurants ===");
        List<Restaurant> allRestaurants = restaurantDAO.findAll();
        System.out.println("Total restaurants found: " + allRestaurants.size());
        for (Restaurant restaurant : allRestaurants) {
            System.out.println("Restaurant: " + restaurant.getName() + ", Cuisine: " + restaurant.getCuisineType());
        }

        // Test 4: Find by cuisine type
        System.out.println("\n=== Finding restaurants by cuisine type ===");
        List<Restaurant> indianRestaurants = restaurantDAO.findByCuisineType("Indian");
        System.out.println("Indian restaurants found: " + indianRestaurants.size());
        for (Restaurant restaurant : indianRestaurants) {
            System.out.println("Restaurant: " + restaurant.getName() + ", Rating: " + restaurant.getRating());
        }

        // Test 5: Find restaurants by rating
        System.out.println("\n=== Finding restaurants by rating > 4.0 ===");
        List<Restaurant> highRatedRestaurants = restaurantDAO.findByRatingGreaterThan(4.0f);
        for (Restaurant restaurant : highRatedRestaurants) {
            System.out.println("Restaurant: " + restaurant.getName() + ", Rating: " + restaurant.getRating());
        }

        // Test 6: Update restaurant
        System.out.println("\n=== Updating restaurant ===");
        restaurant1.setRating(4.8f);
        restaurantDAO.update(restaurant1);
        Restaurant updatedRestaurant = restaurantDAO.findById(restaurant1.getRestaurantId());
        System.out.println("Updated rating for " + updatedRestaurant.getName() + ": " + updatedRestaurant.getRating());

        // Test 7: Search by name
        System.out.println("\n=== Searching restaurants by name ===");
        List<Restaurant> searchResults = restaurantDAO.searchByName("Pizza");
        for (Restaurant restaurant : searchResults) {
            System.out.println("Found: " + restaurant.getName());
        }

        // Test 8: Find active restaurants
        System.out.println("\n=== Finding active restaurants ===");
        List<Restaurant> activeRestaurants = restaurantDAO.findActiveRestaurants();
        System.out.println("Active restaurants found: " + activeRestaurants.size());
        for (Restaurant restaurant : activeRestaurants) {
            System.out.println("Active Restaurant: " + restaurant.getName());
        }

        // Test 9: Update rating
        System.out.println("\n=== Updating restaurant rating ===");
        restaurantDAO.updateRating(restaurant2.getRestaurantId(), 4.2f);
        Restaurant ratingUpdatedRestaurant = restaurantDAO.findById(restaurant2.getRestaurantId());
        System.out.println("Updated rating for " + ratingUpdatedRestaurant.getName() + ": " + ratingUpdatedRestaurant.getRating());

        // Optional: Test 10: Delete restaurant (commented out to preserve data)
        /*
        System.out.println("\n=== Deleting restaurant ===");
        restaurantDAO.delete(restaurant2.getRestaurantId());
        System.out.println("Restaurant deleted successfully!");
        */

        System.out.println("\nAll tests completed successfully!");
    }
}
