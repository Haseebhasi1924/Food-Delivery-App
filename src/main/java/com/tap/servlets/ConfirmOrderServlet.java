package com.tap.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

import com.tap.dao.OrderDAO;
import com.tap.entity.Order;
import com.tap.implementation.OrderDAOImpl;
import com.tap.util.Cart;

@SuppressWarnings("unused")
@WebServlet("/confirmOrder")
public class ConfirmOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("menu");
            return;
        }
        
        // Get form data
        String customerName = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        
        // Validate phone number
        if (!phone.matches("[6-9][0-9]{9}")) {
            request.setAttribute("error", "Please enter a valid 10-digit mobile number");
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);
            return;
        }
        
        // Generate order ID and estimated time
        String orderId = String.format("%d%d", System.currentTimeMillis() % 10000, 
                                     (int)(Math.random() * 1000));
        int estimatedTime = 30 + (int)(Math.random() * 15);
        
        // Set attributes for confirmation page
        request.setAttribute("customerName", customerName);
        request.setAttribute("orderId", orderId);
        request.setAttribute("estimatedTime", estimatedTime);
        
        // Clear the cart
        session.removeAttribute("cart");
        
        // Forward to confirmation page
        request.getRequestDispatcher("/order-confirmation.jsp").forward(request, response);
    }
} 