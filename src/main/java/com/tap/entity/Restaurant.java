package com.tap.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer restaurantId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "address", nullable = false)
    private String address;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "rating")
    private Float rating;
    
    @Column(name = "cuisine_type")
    private String cuisineType;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "eta")
    private Integer eta;
    
    @Column(name = "admin_user_id")
    private Integer adminUserId;
    
    @Column(name = "image_path")
    private String imagePath;
    
    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menuItems;
    
    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;
    
    public Restaurant() {
		// TODO Auto-generated constructor stub
	}
    
    

    public Restaurant(Integer restaurantId, String name, String address, String phone, Float rating, String cuisineType,
			Boolean isActive, Integer eta, Integer adminUserId, String imagePath, List<Menu> menuItems,
			List<Order> orders) {
		super();
		this.restaurantId = restaurantId;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.rating = rating;
		this.cuisineType = cuisineType;
		this.isActive = isActive;
		this.eta = eta;
		this.adminUserId = adminUserId;
		this.imagePath = imagePath;
		this.menuItems = menuItems;
		this.orders = orders;
	}



	// Getters and Setters
    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Menu> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<Menu> menuItems) {
        this.menuItems = menuItems;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
