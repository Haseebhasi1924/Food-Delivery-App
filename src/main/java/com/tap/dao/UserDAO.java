package com.tap.dao;

import com.tap.entity.User;
import java.util.List;

public interface UserDAO {
    User save(User user);
    User findById(Integer userId);
    List<User> findAll();
    User update(User user);
    void delete(Integer userId);
    
    // Business specific methods
    User findByUsername(String username);
    User findByEmail(String email);
    boolean authenticate(String username, String password);
    void updateLastLoginDate(Integer userId);
    List<User> findByRole(String role);
}
