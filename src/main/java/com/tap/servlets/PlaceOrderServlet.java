package com.tap.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.tap.util.Cart;
import java.io.IOException;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form data
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String paymentMethod = request.getParameter("paymentMethod");
        
        // Get cart from session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart");
            return;
        }
        
        try {
            // Generate order ID (you can use your own logic)
            String orderId = "ORD" + System.currentTimeMillis();
            
            // Set attributes for order confirmation
            request.setAttribute("orderId", orderId);
            request.setAttribute("customerName", name);
            request.setAttribute("estimatedTime", "30");
            
            // Clear the cart after successful order
            session.removeAttribute("cart");
            
            // Forward to order confirmation page
            request.getRequestDispatcher("/order-confirmation.jsp").forward(request, response);
            
        } catch (Exception e) {
            // Handle any errors
            response.sendRedirect("checkout?error=true");
        }
    }
} 