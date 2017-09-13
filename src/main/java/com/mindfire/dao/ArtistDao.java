/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.dao;

import com.mindfire.model.Artist;
import java.util.List;

/**
 *
 * @author anants
 */
public interface ArtistDao {
    public boolean saveArtist(Artist artist);
    public List<Artist> getArtist();
    public boolean updateArtist(Artist artist);
}
