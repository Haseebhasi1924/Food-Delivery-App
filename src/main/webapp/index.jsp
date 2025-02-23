<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %> 
<%@ page import="com.tap.entity.Restaurant" %>
<%@ page import="java.time.Year" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.tap.util.Cart" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FoodieHub - Food Delivery</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/common.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <section class="hero-section">
        <div class="hero-content">
            <h1 class="hero-title">Hungry? You're in the right place</h1>
            <p class="hero-subtitle">Order food from your favorite restaurants near you</p>
            <div class="popular-cuisines">
                <div class="cuisine-tag">Pizza</div>
                <div class="cuisine-tag">Burgers</div>
                <div class="cuisine-tag">Chinese</div>
                <div class="cuisine-tag">South Indian</div>
                <div class="cuisine-tag">North Indian</div>
                <div class="cuisine-tag">Biryani</div>
            </div>
        </div>
    </section>

    <div class="search-section">
        <div class="search-container">
            <form action="home" method="get" class="search-form">
                <input type="text" 
                       name="searchQuery" 
                       value="<%= request.getParameter("searchQuery") != null ? request.getParameter("searchQuery") : "" %>" 
                       placeholder="Search for restaurants, cuisine or a dish..." 
                       class="search-box">
                
                
            </form>
        </div>
    </div>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) {
    %>
        <div class="error-message">
            <%= error %>
        </div>
    <%
        }
    %>

    <div class="restaurant-grid">
        <%
            @SuppressWarnings("unchecked")
            List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
            if (restaurants != null && !restaurants.isEmpty()) {
                for (Restaurant restaurant : restaurants) {
        %>
            <div class="restaurant-card" onclick="window.location.href='menu?id=<%= restaurant.getRestaurantId() %>'">
                <img src="<%= restaurant.getImagePath() %>" 
                     alt="<%= restaurant.getName() %>" 
                     class="restaurant-image"
                     onerror="this.src='images/default-restaurant.jpg'">
                <div class="restaurant-info">
                    <h2 class="restaurant-name"><%= restaurant.getName() %></h2>
                    <p class="restaurant-cuisine"><%= restaurant.getCuisineType() %></p>
                    <div class="restaurant-meta">
                        <span class="restaurant-rating">
                            <i class="fas fa-star"></i> 
                            <%= restaurant.getRating() %>
                        </span>
                        <span>•</span>
                        <span><%= restaurant.getEta() %> mins</span>
                    </div>
                    <p class="restaurant-address"><i class="fas fa-map-marker-alt"></i> <%= restaurant.getAddress() %></p>
                    <div class="status-badge <%= restaurant.getIsActive() %>">
                        <%= restaurant.getIsActive() %>
                    </div>
                </div>
            </div>
        <%
                }
            } else {
        %>
            <div class="no-results">
                <h2>No restaurants found</h2>
                <p>Try a different search or check back later!</p>
            </div>
        <%
            }
        %>
    </div>

    <footer class="footer">
        <p>&copy; <%= java.time.Year.now().getValue() %> FoodieHub. All rights reserved.</p>
    </footer>
</body>
</html>