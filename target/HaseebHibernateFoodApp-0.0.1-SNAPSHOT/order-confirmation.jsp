<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmed - FoodieHub</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/order-confirmation.css">
</head>
<body>
    <div class="confirmation-container">
        <div class="confirmation-box">
            <img src="https://t3.ftcdn.net/jpg/04/33/92/58/240_F_433925820_cpUCpcNOQ2gvE9zw2toZHDUDGzsaSW4v.jpg" alt="Delivery in Progress" class="delivery-image">
            <h1>Order Confirmed!</h1>
            <p class="message">Hey <%= request.getAttribute("customerName") %>! Your order is getting delivered</p>
            <div class="order-info">
                <p>Order ID: #<%= request.getAttribute("orderId") %></p>
                <p>Estimated Delivery Time: <%= request.getAttribute("estimatedTime") %> minutes</p>
            </div>
            <a href="home" class="back-home">Back to Home</a>
        </div>
    </div>
</body>
</html> 