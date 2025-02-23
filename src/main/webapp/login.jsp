<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - FoodieHub</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="css/common.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet">
</head>
<body>
    <header class="header">
        <a href="home" class="logo">
            <i class="fas fa-utensils"></i>
            FoodieHub
        </a>
    </header>

    <div class="login-container">
        <div class="login-box">
            <div class="login-header">
                <h1>Welcome Back!</h1>
                <p>Sign in to continue to FoodieHub</p>
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

            <%
                String registered = request.getParameter("registered");
                if (registered != null && registered.equals("true")) {
            %>
                <div class="success-message">
                    Registration successful! Please login with your credentials.
                </div>
            <%
                }
            %>

            <form action="auth/login" method="post">
                <div class="input-group">
                    <i class="fas fa-user"></i>
                    <input type="text" name="username" placeholder="Username or Email" required>
                </div>
                
                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" name="password" placeholder="Password" required>
                </div>

                <div class="remember-me">
                    <input type="checkbox" id="remember" name="remember">
                    <label for="remember">Remember me</label>
                </div>

                <button type="submit" class="login-button">Sign In</button>

                <div class="login-footer">
                    <p>Don't have an account? <a href="register.jsp">Sign Up</a></p>
                    <p><a href="forgot-password.jsp">Forgot Password?</a></p>
                </div>
            </form>
        </div>
    </div>
</body>
</html> 