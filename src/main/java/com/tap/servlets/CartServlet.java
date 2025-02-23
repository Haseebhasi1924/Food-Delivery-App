package com.tap.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import com.tap.dao.MenuDAO;
import com.tap.entity.Menu;
import com.tap.implementation.MenuDAOImpl;
import com.tap.util.Cart;
import com.tap.util.CartItem;

@WebServlet("/cart/*")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MenuDAO menuDAO;
    
    public CartServlet() {
        menuDAO = new MenuDAOImpl();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        try {
            switch (action) {
                case "/update":
                    Integer menuId = Integer.parseInt(request.getParameter("menuId"));
                    Integer quantity = Integer.parseInt(request.getParameter("quantity"));
                    
                    if (quantity <= 0) {
                        cart.removeItem(menuId);
                    } else {
                        // If it's a new item
                        if (!cart.hasItem(menuId)) {
                            Menu menu = menuDAO.findById(menuId);
                            if (menu != null) {
                                CartItem item = new CartItem(
                                    menuId,
                                    menu.getRestaurant().getRestaurantId(),
                                    menu.getItemName(),
                                    menu.getPrice()
                                );
                                cart.addItem(item, menu.getRestaurant().getName());
                            }
                        } else {
                            cart.updateItemQuantity(menuId, quantity);
                        }
                    }
                    break;
                    
                case "/clear":
                    // Clear the cart
                    cart.clear();
                    session.setAttribute("cart", cart);
                    break;
            }
            response.sendRedirect(request.getHeader("Referer"));
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/menu");
        }
    }
} 