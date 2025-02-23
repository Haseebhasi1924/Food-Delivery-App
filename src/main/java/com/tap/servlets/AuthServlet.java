package com.tap.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

import com.tap.dao.UserDAO;
import com.tap.entity.User;
import com.tap.implementation.UserDAOImp;

@WebServlet(urlPatterns = {"/auth/*"})
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    public AuthServlet() {
        super();
        userDAO = new UserDAOImp();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if ("/logout".equals(pathInfo)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        
        // For login page
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if ("/login".equals(pathInfo)) {
            handleLogin(request, response);
        }
    }
    
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        try {
            // First try to find by username
            User user = userDAO.findByUsername(username);
            
            // If not found by username, try email
            if (user == null) {
                user = userDAO.findByEmail(username);
            }

            if (user != null && user.getPassword().equals(password)) {
                // Login successful
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                
                // Update last login date
                user.setLastLoginDate(LocalDateTime.now());
                userDAO.update(user);

                // Set session timeout if "remember me" is checked
                if (remember != null) {
                    session.setMaxInactiveInterval(7 * 24 * 60 * 60); // 7 days
                }

                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                // Login failed
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
} 