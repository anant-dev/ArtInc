/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.dao;

import com.mindfire.model.Product;
import com.mindfire.model.User;
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
public class ProductDaoImpl implements ProductDao{

    @Override
    public boolean saveProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();  
        session.close();
        return true;
    }

    @Override
    public List<Product> getOrder(int artist_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Product product where artist_id =:artist_id";
        Query query = session.createQuery(hql);
        query.setParameter("artist_id", artist_id);
        List<Product> pr = (List<Product>) query.list();
        session.getTransaction().commit();
        session.close();        
        return pr;
    }

    @Override
    public boolean updateProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteProduct(int product_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "delete Product where product_id = :product_id";
        Query query = session.createQuery(hql);
        query.setParameter("product_id", product_id);
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
}
