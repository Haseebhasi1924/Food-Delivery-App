package com.tap.implementation;

import com.tap.dao.MenuDAO;
import com.tap.entity.Menu;
import com.tap.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    @Override
    public Menu save(Menu menu) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(menu);
            transaction.commit();
            return menu;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Menu findById(Integer menuId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Menu.class, menuId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Menu> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Menu";
            return session.createQuery(hql, Menu.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Menu update(Menu menu) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            menu = session.merge(menu);
            transaction.commit();
            return menu;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Integer menuId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Menu menu = session.find(Menu.class, menuId);
            if (menu != null) {
                session.remove(menu);
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
    public List<Menu> findByRestaurantId(Integer restaurantId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Menu WHERE restaurant.id = :restaurantId";
            Query<Menu> query = session.createQuery(hql, Menu.class);
            query.setParameter("restaurantId", restaurantId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Menu> findAvailableItems(Integer restaurantId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Menu WHERE restaurant.id = :restaurantId AND isAvailable = true";
            Query<Menu> query = session.createQuery(hql, Menu.class);
            query.setParameter("restaurantId", restaurantId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Menu> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Menu WHERE price BETWEEN :minPrice AND :maxPrice";
            Query<Menu> query = session.createQuery(hql, Menu.class);
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateAvailability(Integer menuId, boolean isAvailable) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Menu menu = session.find(Menu.class, menuId);
            if (menu != null) {
                menu.setIsAvailable(isAvailable);
                session.merge(menu);
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
    public List<Menu> searchByItemName(String itemName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Menu WHERE LOWER(itemName) LIKE LOWER(:itemName)";
            Query<Menu> query = session.createQuery(hql, Menu.class);
            query.setParameter("itemName", "%" + itemName + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateRating(Integer menuId, Float newRating) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Menu menu = session.find(Menu.class, menuId);
            if (menu != null) {
                menu.setRatings(newRating);
                session.merge(menu);
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
