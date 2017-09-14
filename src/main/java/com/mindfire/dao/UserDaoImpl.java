/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.dao;

import com.mindfire.model.User;
import com.mindfire.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anants
 */
@Repository
public class UserDaoImpl implements UserDao {
    
    @Override
    public int saveUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        int status = (int) session.save(user);
        session.getTransaction().commit();  
        session.close();
        return status;
    }

    @Override
    public User getUser(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();                  
        session.beginTransaction();
        String hql = "from User user where email =:email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        User user = (User) query.uniqueResult();
        session.getTransaction().commit();
        return user;
    }
    
}
