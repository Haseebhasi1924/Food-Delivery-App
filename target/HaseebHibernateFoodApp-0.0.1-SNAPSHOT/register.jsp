<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - FoodieHub</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="css/common.css" rel="stylesheet">
    <link href="css/register.css" rel="stylesheet">
</head>
<body>
    <header class="header">
        <a href="home" class="logo">
            <i class="fas fa-utensils"></i>
            FoodieHub
        </a>
    </header>

    <div class="register-container">
        <div class="register-box">
            <div class="register-header">
                <h1>Create Account</h1>
                <p>Join FoodieHub for delicious food delivery!</p>
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

            <form action="register" method="post" class="register-form">
                <div class="form-group">
                    <label><i class="fas fa-user"></i> Full Name</label>
                    <input type="text" name="name" required 
                           pattern="[A-Za-z\s]+" 
                           title="Please enter a valid name (letters and spaces only)">
                </div>

                <div class="form-group">
                    <label><i class="fas fa-envelope"></i> Email Address</label>
                    <input type="email" name="email" required>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-phone"></i> Phone Number</label>
                    <input type="tel" 
                           name="phone" 
                           pattern="[6-9][0-9]{9}" 
                           title="Please enter valid 10 digit mobile number" 
                           required>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-user-tag"></i> Role</label>
                    <select name="role" required>
                        <option value="">Select Role</option>
                        <option value="USER">Customer</option>
                        <option value="ADMIN">Restaurant Admin</option>
                    </select>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-map-marker-alt"></i> Address</label>
                    <textarea name="address" required></textarea>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-user-circle"></i> Username</label>
                    <input type="text" name="username" required 
                           pattern="[a-zA-Z0-9_]+" 
                           title="Username can only contain letters, numbers and underscore">
                </div>

                <div class="form-group">
                    <label><i class="fas fa-lock"></i> Password</label>
                    <input type="password" name="password" required 
                           pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" 
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters">
                </div>

                <button type="submit" class="register-button">
                    <i class="fas fa-user-plus"></i> Create Account
                </button>

                <div class="login-link">
                    Already have an account? <a href="login">Sign In</a>
                </div>
            </form>

            <script>
            document.querySelector('.register-form').addEventListener('submit', function(e) {
                const button = this.querySelector('button[type="submit"]');
                button.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Creating Account...';
                button.disabled = true;
            });
            </script>
        </div>
    </div>

    <footer class="footer">
        <p>&copy; <%= java.time.Year.now().getValue() %> FoodieHub. All rights reserved.</p>
    </footer>
</body>
</html> 