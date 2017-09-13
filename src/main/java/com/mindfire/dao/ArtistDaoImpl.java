/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.dao;

import com.mindfire.model.Artist;
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
public class ArtistDaoImpl implements ArtistDao{

    @Override
    public int saveArtist(Artist artist) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(artist);
        session.getTransaction().commit();
        session.close();
        return 1;
    }

    @Override
    public List<Artist> getArtist() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Artist artist where artist_id =:artist_id";
        Query query = session.createQuery(hql);
        //query.setParameter("artist_id", artist_id);
        List<Artist> ar = (List<Artist>) query.list();
        session.getTransaction().commit();
        session.close();
        return ar;
    }

    @Override
    public int updateArtist(Artist artist) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql ="update Artist artist set description =: description, place =: place";
        Query query = session.createQuery(hql);
        query.setParameter("description", artist.getDescription());
        query.setParameter("place", artist.getPlace());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
}
