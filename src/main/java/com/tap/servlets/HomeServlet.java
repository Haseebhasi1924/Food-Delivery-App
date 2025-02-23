package com.tap.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.tap.dao.RestaurantDAO;
import com.tap.entity.Restaurant;
import com.tap.implementation.RestaurantDAOImpl;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RestaurantDAO restaurantDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        restaurantDAO = new RestaurantDAOImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Get search query parameter
			String searchQuery = request.getParameter("searchQuery");
			List<Restaurant> restaurants;

			// Fetch restaurants based on search query
			if (searchQuery != null && !searchQuery.trim().isEmpty()) {
				// Search for restaurants by name
				restaurants = restaurantDAO.searchByName(searchQuery);
			} else {
				// Get all restaurants if no search query is provided
				restaurants = restaurantDAO.findAll();
			}

			// Add restaurants to request attribute
			request.setAttribute("restaurants", restaurants);

			// Get unique cuisine types for filtering
			List<String> cuisineTypes = restaurants.stream()
				.map(Restaurant::getCuisineType)
				.distinct()
				.sorted()
				.toList();
			request.setAttribute("cuisineTypes", cuisineTypes);

			// Forward to index.jsp
			request.getRequestDispatcher("index.jsp").forward(request, response);

		} catch (Exception e) {
			// Log the error
			e.printStackTrace();
			
			// Set error message
			request.setAttribute("error", "An error occurred while fetching restaurants.");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Handle any POST requests (e.g., filtering)
		String cuisineType = request.getParameter("cuisineType");
		List<Restaurant> restaurants;

		try {
			if (cuisineType != null && !cuisineType.isEmpty()) {
				restaurants = restaurantDAO.findByCuisineType(cuisineType);
			} else {
				restaurants = restaurantDAO.findAll();
			}

			request.setAttribute("restaurants", restaurants);
			request.setAttribute("selectedCuisine", cuisineType);
			request.getRequestDispatcher("index.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "An error occurred while filtering restaurants.");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

}
