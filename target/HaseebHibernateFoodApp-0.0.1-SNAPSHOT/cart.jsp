<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.util.Cart"%>
<%@ page import="com.tap.util.CartItem"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Cart - FoodieHub</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/cart.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="cart-container">
        <h1>Your Cart</h1>
        
        <%
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null || cart.getItems().isEmpty()) {
        %>
            <div class="empty-cart">
                <i class="fas fa-shopping-cart"></i>
                <h2>Your cart is empty</h2>
                <p>Add items from a restaurant to start your order</p>
                <a href="home" class="browse-btn">Browse Restaurants</a>
            </div>
        <%
            } else {
                DecimalFormat df = new DecimalFormat("0.00");
        %>
            <div class="cart-header">
                <h2>Order from <%= cart.getRestaurantName() %></h2>
                <a href="menu?id=<%= cart.getRestaurantId() %>" class="add-more-btn">
                    <i class="fas fa-plus"></i> Add More Items
                </a>
            </div>
            
            <div class="cart-items">
                <% for (CartItem item : cart.getItems()) { %>
                    <div class="cart-item">
                        <div class="item-info">
                            <h3><%= item.getItemName() %></h3>
                            <p>₹<%= df.format(item.getPrice()) %></p>
                        </div>
                        <div class="item-controls">
                            <form action="cart/update" method="post">
                                <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
                                <button type="submit" name="quantity" value="<%= item.getQuantity() - 1 %>">-</button>
                                <span><%= item.getQuantity() %></span>
                                <button type="submit" name="quantity" value="<%= item.getQuantity() + 1 %>">+</button>
                            </form>
                        </div>
                        <div class="item-total">
                            ₹<%= df.format(item.getTotalPrice()) %>
                        </div>
                    </div>
                <% } %>
            </div>
            
            <div class="cart-summary">
                <div class="summary-row">
                    <span>Subtotal</span>
                    <span>₹<%= df.format(cart.getTotal()) %></span>
                </div>
                <div class="summary-row">
                    <span>Delivery Fee</span>
                    <span>₹40.00</span>
                </div>
                <div class="summary-row total">
                    <span>Total</span>
                    <span>₹<%= df.format(cart.getTotal().add(new BigDecimal("40.00"))) %></span>
                </div>
                
                <form action="checkout" method="post" class="checkout-form">
                    <button type="submit" class="checkout-btn">
                        Proceed to Checkout
                    </button>
                </form>
                
                <form action="cart/clear" method="post" class="clear-cart-form">
                    <button type="submit" class="clear-cart-btn" onclick="return confirm('Are you sure you want to clear your cart?')">
                        <i class="fas fa-trash"></i> Clear Cart
                    </button>
                </form>
            </div>
        <% } %>
    </div>
</body>
</html> 