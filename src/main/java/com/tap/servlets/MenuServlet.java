package com.tap.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.tap.dao.MenuDAO;
import com.tap.dao.RestaurantDAO;
import com.tap.entity.Menu;
import com.tap.entity.Restaurant;
import com.tap.implementation.MenuDAOImpl;
import com.tap.implementation.RestaurantDAOImpl;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MenuDAO menuDAO;
    private RestaurantDAO restaurantDAO;
       
    public MenuServlet() {
        super();
        menuDAO = new MenuDAOImpl();
        restaurantDAO = new RestaurantDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String restaurantId = request.getParameter("id");
            int id = Integer.parseInt(restaurantId);
            
            if (restaurantId != null && !restaurantId.trim().isEmpty()) {
                Restaurant restaurant = restaurantDAO.findById(id);
                List<Menu> menuItems = menuDAO.findByRestaurantId(id);
                
                if (restaurant != null) {
                    request.setAttribute("restaurant", restaurant);
                    request.setAttribute("menuItems", menuItems);
                    request.getRequestDispatcher("menu.jsp").forward(request, response);
                    return;
                }
            }
            
            // If restaurant not found or no ID provided
            response.sendRedirect("home");
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("home"); // Redirect if parsing fails
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading menu items");
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        }
    }
} 