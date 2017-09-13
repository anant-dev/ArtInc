/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.dao;

import com.mindfire.model.Order;
import com.mindfire.model.Product;
import com.mindfire.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anants
 */
@Repository
public class OrderDaoImpl implements OrderDao{

    @Override
    public boolean saveOrder(Order order) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();  
        session.close();
        return true;
    }

    @Override
    public List<Order> getOrder(int user_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Order order where user_id =:user_id";
        Query query = session.createQuery(hql);
        query.setParameter("user_id", user_id);
        List<Order> or = (List<Order>) query.list();
        session.getTransaction().commit();
        session.close();
        return or;
    }

}
