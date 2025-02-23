package com.tap.implementation;

import com.tap.dao.OrderItemDAO;
import com.tap.entity.OrderItem;
import com.tap.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {

    @Override
    public OrderItem save(OrderItem orderItem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(orderItem);
            transaction.commit();
            return orderItem;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrderItem findById(Integer orderItemId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(OrderItem.class, orderItemId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderItem> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM OrderItem";
            return session.createQuery(hql, OrderItem.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            orderItem = session.merge(orderItem);
            transaction.commit();
            return orderItem;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Integer orderItemId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            OrderItem orderItem = session.find(OrderItem.class, orderItemId);
            if (orderItem != null) {
                session.remove(orderItem);
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
    public List<OrderItem> findByOrderId(Integer orderId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM OrderItem WHERE order.id = :orderId";
            Query<OrderItem> query = session.createQuery(hql, OrderItem.class);
            query.setParameter("orderId", orderId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderItem> findByMenuId(Integer menuId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM OrderItem WHERE menu.id = :menuId";
            Query<OrderItem> query = session.createQuery(hql, OrderItem.class);
            query.setParameter("menuId", menuId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateQuantity(Integer orderItemId, Integer newQuantity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            OrderItem orderItem = session.find(OrderItem.class, orderItemId);
            if (orderItem != null) {
                orderItem.setQuantity(newQuantity);
                // Update total price based on new quantity
                updateTotalPrice(orderItemId);
                session.merge(orderItem);
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
    public void updateTotalPrice(Integer orderItemId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            OrderItem orderItem = session.find(OrderItem.class, orderItemId);
            if (orderItem != null) {
                BigDecimal unitPrice = orderItem.getMenu().getPrice();
                BigDecimal quantity = new BigDecimal(orderItem.getQuantity());
                orderItem.setTotalPrice(unitPrice.multiply(quantity));
                session.merge(orderItem);
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
