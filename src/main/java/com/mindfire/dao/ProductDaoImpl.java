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
public class ProductDaoImpl implements ProductDao {

    @Override
    public int saveProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
        return 1;
    }

    @Override
    public List<Product> getProductByArtist(int artist_id) {
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
    public List<Product> getProduct() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Product product";
        Query query = session.createQuery(hql);
        List<Product> pr = (List<Product>) query.list();
        session.getTransaction().commit();
        session.close();
        return pr;
    }

    @Override
    public int updateProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "update Product product set title = :title, description =: description, category =: category, price =: price";
        Query query = session.createQuery(hql);
        query.setParameter("title", product.getTitle());
        query.setParameter("description", product.getDescription());
        query.setParameter("category", product.getCategory());
        query.setParameter("price", product.getPrice());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return result;
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

    @Override
    public List<Product> getProductByCategory(int c_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Product product where category =:category";
        Query query = session.createQuery(hql);
        query.setParameter("category",c_id);
        List<Product> pr = (List<Product>) query.list();
        session.getTransaction().commit();
        session.close();
        return pr;
    }

    @Override
    public List<Product> getProductBySize(int size) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Product product where p_size =:p_size";
        Query query = session.createQuery(hql);
        query.setParameter("p_size", size);
        List<Product> pr = (List<Product>) query.list();
        session.getTransaction().commit();
        session.close();
        return pr;
    }

    @Override
    public Product getProductById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Product product where product_id =:product_id";
        Query query = session.createQuery(hql);
        query.setParameter("product_id", id);
        Product product= (Product) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return product;
    }

}
