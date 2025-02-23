package com.tap.implementation;

import com.tap.dao.RestaurantDAO;
import com.tap.entity.Restaurant;
import com.tap.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RestaurantDAOImpl implements RestaurantDAO {

    @Override
    public Restaurant save(Restaurant restaurant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(restaurant);
            transaction.commit();
            return restaurant;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Restaurant findById(Integer restaurantId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Restaurant.class, restaurantId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Restaurant> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Restaurant";
            return session.createQuery(hql, Restaurant.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            restaurant = session.merge(restaurant);
            transaction.commit();
            return restaurant;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Integer restaurantId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Restaurant restaurant = session.find(Restaurant.class, restaurantId);
            if (restaurant != null) {
                session.remove(restaurant);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Restaurant> findByCuisineType(String cuisineType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Restaurant WHERE cuisineType = :cuisineType";
            Query<Restaurant> query = session.createQuery(hql, Restaurant.class);
            query.setParameter("cuisineType", cuisineType);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Restaurant> findByRatingGreaterThan(Float rating) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Restaurant WHERE rating > :rating";
            Query<Restaurant> query = session.createQuery(hql, Restaurant.class);
            query.setParameter("rating", rating);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Restaurant> findActiveRestaurants() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Restaurant WHERE isActive = true";
            return session.createQuery(hql, Restaurant.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Restaurant> searchByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Restaurant WHERE LOWER(name) LIKE LOWER(:name)";
            Query<Restaurant> query = session.createQuery(hql, Restaurant.class);
            query.setParameter("name", "%" + name + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Restaurant> findByAdminUserId(Integer adminUserId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Restaurant WHERE adminUserId = :adminUserId";
            Query<Restaurant> query = session.createQuery(hql, Restaurant.class);
            query.setParameter("adminUserId", adminUserId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateRating(Integer restaurantId, Float newRating) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Restaurant restaurant = session.find(Restaurant.class, restaurantId);
            if (restaurant != null) {
                restaurant.setRating(newRating);
                session.merge(restaurant);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
