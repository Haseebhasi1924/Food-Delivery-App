<%@ page import="com.tap.util.Cart"%>
<header class="header">
    <div class="header-content">
        <a href="home" class="logo">
            <i class="fas fa-utensils"></i>
            FoodieHub
        </a>
        <nav class="nav-right">
            <a href="cart" class="nav-item">
                <div class="cart-icon">
                    <i class="fas fa-shopping-cart"></i>
                    <%
                        Cart cart = (Cart) session.getAttribute("cart");
                        if (cart != null && !cart.getItems().isEmpty()) { 
                    %>
                        <span class="cart-count"><%= cart.getItems().size() %></span>
                    <% } %>
                </div>
            </a>
            <% if (session.getAttribute("user") != null) { %>
                <a href="profile" class="nav-item">
                    <i class="fas fa-user"></i>
                    Profile
                </a>
                <a href="logout" class="nav-item">
                    <i class="fas fa-sign-out-alt"></i>
                    Logout
                </a>
            <% } else { %>
                <a href="login" class="nav-item">
                    <i class="fas fa-sign-in-alt"></i>
                    Login
                </a>
            <% } %>
        </nav>
    </div>
</header> 