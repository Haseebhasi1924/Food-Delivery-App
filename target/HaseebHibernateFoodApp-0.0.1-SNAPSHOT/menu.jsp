<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.entity.Restaurant"%>
<%@ page import="com.tap.entity.Menu"%>
<%@ page import="com.tap.util.Cart"%>
<%@ page import="com.tap.util.CartItem"%>
<%@ page import="java.util.List"%>
<%@ page import="java.math.BigDecimal"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menu - FoodieHub</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/menu.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <%
        Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
        Cart cart = (Cart) session.getAttribute("cart");
        if (restaurant != null) {
    %>
    <div class="menu-page">
        <div class="restaurant-banner">
            <div class="banner-content">
                <div class="restaurant-info">
                    <h1><%= restaurant.getName() %></h1>
                    <p class="cuisine"><%= restaurant.getCuisineType() %></p>
                    <p class="address"><i class="fas fa-map-marker-alt"></i> <%= restaurant.getAddress() %></p>
                    <div class="restaurant-meta">
                        <span class="rating">
                            <i class="fas fa-star"></i> 
                            <%= restaurant.getRating() %>
                        </span>
                        <span class="delivery-time">
                            <i class="fas fa-clock"></i>
                            <%= restaurant.getEta() %> mins
                        </span>
                    </div>
                </div>
            </div>
        </div>

        <div class="menu-container">
            <div class="menu-content">
                <%
                    List<Menu> menuItems = (List<Menu>) request.getAttribute("menuItems");
                    if (menuItems != null && !menuItems.isEmpty()) {
                        for (Menu item : menuItems) {
                %>
                <div class="menu-item" id="item-<%= item.getMenuId() %>">
                    <div class="item-image">
                        <img src="<%= item.getImagePath() %>" 
                             alt="<%= item.getItemName() %>"
                             onerror="this.src='images/default-food.jpg'">
                    </div>
                    <div class="item-details">
                        <h3><%= item.getItemName() %></h3>
                        <p class="description"><%= item.getDescription() %></p>
                        <div class="price">₹<%= String.format("%.2f", item.getPrice()) %></div>
                        <div class="item-actions">
                            <% 
                                boolean itemInCart = false;
                                int itemQuantity = 0;
                                if (cart != null) {
                                    for (CartItem cartItem : cart.getItems()) {
                                        if (cartItem.getMenuId() == item.getMenuId()) {
                                            itemInCart = true;
                                            itemQuantity = cartItem.getQuantity();
                                            break;
                                        }
                                    }
                                }
                                if (!itemInCart) {
                            %>
                            <form action="cart/update#item-<%= item.getMenuId() %>" method="post" class="add-form">
                                <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
                                <input type="hidden" name="quantity" value="1">
                                <button type="submit" class="add-button">Add to Cart</button>
                            </form>
                            <% } else { %>
                            <form action="cart/update#item-<%= item.getMenuId() %>" method="post" class="quantity-form">
                                <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
                                <button type="submit" name="quantity" value="<%= itemQuantity - 1 %>">−</button>
                                <span class="quantity"><%= itemQuantity %></span>
                                <button type="submit" name="quantity" value="<%= itemQuantity + 1 %>">+</button>
                            </form>
                            <% } %>
                        </div>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                <div class="no-items">
                    <h2>No menu items available</h2>
                    <p>Please check back later!</p>
                </div>
                <%
                    }
                %>
            </div>

            <div class="cart-wrapper">
                <div class="cart-sidebar">
                    <div class="cart-content">
                        <h3>Your Cart</h3>
                        <% if (cart != null && !cart.getItems().isEmpty()) { %>
                            <div class="cart-items">
                                <% for (CartItem item : cart.getItems()) { %>
                                    <div class="cart-item">
                                        <div class="cart-item-info">
                                            <h4><%= item.getItemName() %></h4>
                                            <p>₹<%= String.format("%.2f", item.getPrice()) %> × <%= item.getQuantity() %></p>
                                        </div>
                                        <div class="cart-item-total">
                                            ₹<%= String.format("%.2f", item.getTotalPrice()) %>
                                        </div>
                                    </div>
                                <% } %>
                            </div>
                            <div class="cart-summary">
                                <div class="summary-row">
                                    <span>Subtotal</span>
                                    <span>₹<%= String.format("%.2f", cart.getTotal()) %></span>
                                </div>
                                <div class="summary-row">
                                    <span>Delivery Fee</span>
                                    <span>₹40.00</span>
                                </div>
                                <div class="summary-row total">
                                    <span>Total</span>
                                    <span>₹<%= String.format("%.2f", cart.getTotal().add(new BigDecimal("40.00"))) %></span>
                                </div>
                                <a href="checkout" class="checkout-btn">Proceed to Checkout</a>
                            </div>
                        <% } else { %>
                            <div class="empty-cart-message">
                                <p>Your cart is empty</p>
                            </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <% } %>
</body>
</html> 