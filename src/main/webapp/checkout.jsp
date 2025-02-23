<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.util.Cart"%>
<%@ page import="com.tap.util.CartItem"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Secure Checkout - FoodieHub</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/checkout.css">
</head>
<body>
    <%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null || cart.getItems().isEmpty()) {
        response.sendRedirect("cart");
        return;
    }
    BigDecimal itemsTotal = cart.getTotal();
    BigDecimal deliveryFee = new BigDecimal("40.00");
    BigDecimal orderTotal = itemsTotal.add(deliveryFee);
    %>

    <header class="header">
        <div class="header-content">
            <a href="home" class="logo">
                <i class="fas fa-utensils"></i>
                FoodieHub
            </a>
            <div class="checkout-steps">
                <span class="step active">Cart</span>
                <span class="step-divider">→</span>
                <span class="step active">Checkout</span>
                <span class="step-divider">→</span>
                <span class="step">Confirmation</span>
            </div>
        </div>
    </header>

    <div class="checkout-container">
        <div class="checkout-box">
            <div class="checkout-header">
                <h1 class="checkout-title">Secure Checkout</h1>
                <p class="checkout-subtitle">Complete your order with our secure payment system</p>
            </div>

            <form action="place-order" method="post" id="checkoutForm" onsubmit="return validateForm()">
                <!-- Delivery Details Section -->
                <div class="checkout-form-section delivery-section">
                    <div class="checkout-section-header">
                        <div class="checkout-section-icon">
                            <i class="fas fa-map-marker-alt"></i>
                        </div>
                        <div class="checkout-section-title-group">
                            <h2 class="checkout-section-title">Delivery Details</h2>
                            <p class="checkout-section-subtitle">Please provide accurate delivery information</p>
                        </div>
                    </div>

                    <div class="checkout-form-group">
                        <div class="checkout-input-group">
                            <label for="fullName" class="checkout-label">Full Name</label>
                            <div class="checkout-input-wrapper">
                                <i class="fas fa-user checkout-input-icon"></i>
                                <input type="text" 
                                       id="fullName" 
                                       name="name" 
                                       class="checkout-form-control" 
                                       placeholder="Enter your full name"
                                       required>
                            </div>
                        </div>

                        <div class="checkout-input-group">
                            <label for="phone" class="checkout-label">Phone Number</label>
                            <div class="checkout-input-wrapper">
                                <i class="fas fa-phone-alt checkout-input-icon"></i>
                                <input type="tel" 
                                       id="phone" 
                                       name="phone" 
                                       class="checkout-form-control" 
                                       placeholder="Enter your mobile number"
                                       pattern="[6-9][0-9]{9}"
                                       title="Please enter a valid 10-digit Indian mobile number"
                                       maxlength="10" 
                                       required>
                            </div>
                            <span class="checkout-input-hint">We'll send delivery updates on this number</span>
                        </div>

                        <div class="checkout-input-group">
                            <label for="address" class="checkout-label">Delivery Address</label>
                            <div class="checkout-input-wrapper">
                                <i class="fas fa-home checkout-input-icon"></i>
                                <textarea id="address" 
                                         name="address" 
                                         class="checkout-form-control checkout-textarea" 
                                         placeholder="Enter your complete delivery address"
                                         rows="3" 
                                         required></textarea>
                            </div>
                            <span class="checkout-input-hint">Include landmark for easier delivery</span>
                        </div>

                        <div class="checkout-input-group">
                            <label for="instructions" class="checkout-label">Delivery Instructions 
                                <span class="checkout-label-optional">(Optional)</span>
                            </label>
                            <div class="checkout-input-wrapper">
                                <i class="fas fa-info-circle checkout-input-icon"></i>
                                <input type="text" 
                                       id="instructions" 
                                       name="instructions" 
                                       class="checkout-form-control" 
                                       placeholder="Any specific instructions for delivery?">
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Payment Section -->
                <div class="checkout-form-section payment-section">
                    <div class="checkout-section-header">
                        <div class="checkout-section-icon">
                            <i class="fas fa-credit-card"></i>
                        </div>
                        <div class="checkout-section-title-group">
                            <h2 class="checkout-section-title">Payment Method</h2>
                            <p class="checkout-section-subtitle">Choose your preferred payment option</p>
                        </div>
                    </div>

                    <div class="payment-options">
                        <div class="payment-option">
                            <input type="radio" id="card" name="paymentMethod" value="card">
                            <label for="card">
                                <i class="fas fa-credit-card"></i>
                                <span>Credit/Debit Card</span>
                            </label>
                        </div>

                        <div class="payment-option">
                            <input type="radio" id="upi" name="paymentMethod" value="upi">
                            <label for="upi">
                                <i class="fas fa-mobile-alt"></i>
                                <span>UPI Payment</span>
                            </label>
                        </div>

                        <div class="payment-option">
                            <input type="radio" id="cod" name="paymentMethod" value="cod">
                            <label for="cod">
                                <i class="fas fa-money-bill-wave"></i>
                                <span>Cash on Delivery</span>
                            </label>
                        </div>
                    </div>

                    <!-- Card Payment Details (Initially Hidden) -->
                    <div id="cardDetails" class="payment-details hidden">
                        <div class="checkout-form-group">
                            <div class="checkout-input-group">
                                <label class="checkout-label">Card Number</label>
                                <div class="checkout-input-wrapper">
                                    <i class="fas fa-credit-card checkout-input-icon"></i>
                                    <input type="text" 
                                           class="checkout-form-control" 
                                           placeholder="1234 5678 9012 3456"
                                           maxlength="19"
                                           pattern="\d{4}\s?\d{4}\s?\d{4}\s?\d{4}">
                                </div>
                            </div>

                            <div class="checkout-input-row">
                                <div class="checkout-input-group">
                                    <label class="checkout-label">Expiry Date</label>
                                    <div class="checkout-input-wrapper">
                                        <i class="fas fa-calendar checkout-input-icon"></i>
                                        <input type="text" 
                                               class="checkout-form-control" 
                                               placeholder="MM/YY"
                                               maxlength="5">
                                    </div>
                                </div>

                                <div class="checkout-input-group">
                                    <label class="checkout-label">CVV</label>
                                    <div class="checkout-input-wrapper">
                                        <i class="fas fa-lock checkout-input-icon"></i>
                                        <input type="password" 
                                               class="checkout-form-control" 
                                               placeholder="123"
                                               maxlength="3">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- UPI Payment Details (Initially Hidden) -->
                    <div id="upiDetails" class="payment-details hidden">
                        <div class="checkout-form-group">
                            <div class="checkout-input-group">
                                <label class="checkout-label">UPI ID</label>
                                <div class="checkout-input-wrapper">
                                    <i class="fas fa-at checkout-input-icon"></i>
                                    <input type="text" 
                                           class="checkout-form-control" 
                                           placeholder="yourname@upi">
                                </div>
                                <span class="checkout-input-hint">Enter your UPI ID (e.g., name@bank)</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Order Summary -->
                <div class="checkout-summary">
                    <h3>Order Summary</h3>
                    <div class="summary-items">
                        <%
                        List<CartItem> items = cart.getItems();
                        for (CartItem item : items) {
                        %>
                            <div class="summary-item">
                                <span><%= item.getQuantity() %>x <%= item.getItemName() %></span>
                                <span>₹<%= String.format("%.2f", item.getTotalPrice()) %></span>
                            </div>
                        <% } %>
                    </div>
                    <div class="summary-total">
                        <div class="summary-row">
                            <span>Sub-total</span>
                            <span>₹<%= String.format("%.2f", itemsTotal) %></span>
                        </div>
                        <div class="summary-row">
                            <span>Delivery Fee</span>
                            <span>₹<%= String.format("%.2f", deliveryFee) %></span>
                        </div>
                        <div class="summary-row total">
                            <span>Total Amount</span>
                            <span>₹<%= String.format("%.2f", orderTotal) %></span>
                        </div>
                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="checkout-actions">
                    <button type="button" class="btn-secondary" onclick="window.location.href='cart'">
                        <i class="fas fa-arrow-left"></i> Back to Cart
                    </button>
                    <button type="submit" class="btn-primary">
                        <i class="fas fa-lock"></i> Place Order
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Show/hide payment details based on selection
        document.querySelectorAll('input[name="paymentMethod"]').forEach(radio => {
            radio.addEventListener('change', function() {
                document.querySelectorAll('.payment-details').forEach(div => {
                    div.classList.add('hidden');
                });
                if (this.value === 'card') {
                    document.querySelector('#cardDetails').classList.remove('hidden');
                } else if (this.value === 'upi') {
                    document.querySelector('#upiDetails').classList.remove('hidden');
                }
            });
        });

        function validateForm() {
            const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked');
            if (!paymentMethod) {
                alert('Please select a payment method');
                return false;
            }
            return true;
        }
    </script>

    <footer class="footer">
        <div class="footer-content">
            <p>© 2024 FoodieHub. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>