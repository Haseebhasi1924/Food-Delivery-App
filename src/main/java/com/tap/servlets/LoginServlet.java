package com.tap.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

import com.tap.dao.UserDAO;
import com.tap.entity.User;
import com.tap.implementation.UserDAOImp;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
       
    public LoginServlet() {
        super();
        userDAO = new UserDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // User is already logged in, redirect to home
            response.sendRedirect("home");
        } else {
            // Show login page
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
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

                response.sendRedirect("home");
            } else {
                // Login failed
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
} 