/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.service;

import com.mindfire.dao.ArtistDao;
import com.mindfire.model.Artist;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author anants
 */
public class ArtistServiceImpl implements ArtistService {
    @Autowired
    ArtistDao artistDao;

    @Override
    public int saveArtist(Artist artist) {
        return artistDao.saveArtist(artist);
    }

    @Override
    public List<Artist> getArtist() {
        return artistDao.getArtist();
    }

    @Override
    public int updateArtist(Artist artist) {
        return artistDao.updateArtist(artist);
    }
    
    
    
    
}
