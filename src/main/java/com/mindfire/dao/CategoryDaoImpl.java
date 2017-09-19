/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.dao;

import com.mindfire.model.Artist;
import com.mindfire.model.Category;
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
public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> getCategory() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Category category";
        Query query = session.createQuery(hql);
        List<Category> categorys = (List<Category>) query.list();
        session.getTransaction().commit();
        session.close();
        return categorys;
    }

    @Override
    public Category getCategoryById(int caid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Category category where c_id =:c_id";
        Query query = session.createQuery(hql);
        query.setParameter("c_id", caid);
        Category category= (Category) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return category;
    }

}
