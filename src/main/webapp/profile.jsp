<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.entity.User"%>
<%@ page import="java.time.format.DateTimeFormatter"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile - FoodieHub</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/profile.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="profile-container">
        <%
            User user = (User) session.getAttribute("user");
            if(user != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        %>
        <div class="profile-card">
            <div class="profile-header">
                <div class="profile-avatar">
                    <i class="fas fa-user"></i>
                </div>
                <h1><%= user.getName() %></h1>
                <p class="username">@<%= user.getUsername() %></p>
            </div>
            
            <div class="profile-info">
                <div class="info-group">
                    <label><i class="fas fa-envelope"></i> Email</label>
                    <p><%= user.getEmail() %></p>
                </div>
                
                <div class="info-group">
                    <label><i class="fas fa-phone"></i> Phone</label>
                    <p><%= user.getPhone() != null ? user.getPhone() : "Not provided" %></p>
                </div>
                
                <div class="info-group">
                    <label><i class="fas fa-map-marker-alt"></i> Address</label>
                    <p><%= user.getAddress() != null ? user.getAddress() : "Not provided" %></p>
                </div>
                
                <div class="info-group">
                    <label><i class="fas fa-user-tag"></i> Role</label>
                    <p><%= user.getRole() %></p>
                </div>
                
                <div class="info-group">
                    <label><i class="fas fa-calendar-alt"></i> Member Since</label>
                    <p><%= user.getCreatedDate().format(formatter) %></p>
                </div>
                
                <div class="info-group">
                    <label><i class="fas fa-clock"></i> Last Login</label>
                    <p><%= user.getLastLoginDate() != null ? user.getLastLoginDate().format(formatter) : "First login" %></p>
                </div>
            </div>
            
            <div class="profile-actions">
                <!-- <a href="edit-profile" class="edit-btn">
                    <i class="fas fa-edit"></i> Edit Profile
                </a> -->
                <a href="auth/logout" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </a>
            </div>
        </div>
        <% } else { %>
            <div class="error-message">
                <i class="fas fa-exclamation-circle"></i>
                <p>Please login to view your profile</p>
                <a href="login" class="login-btn">Login Now</a>
            </div>
        <% } %>
    </div>
</body>
</html> 