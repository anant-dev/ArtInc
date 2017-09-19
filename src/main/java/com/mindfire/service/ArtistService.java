/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.service;

import com.mindfire.model.Artist;
import java.util.List;

/**
 *
 * @author anants
 */
public interface ArtistService {

    public int saveArtist(Artist artist);

    public List<Artist> getArtist();

    public Artist getArtistById(int aid);

    public Artist getArtistByUserId(int uid);

    public int updateArtist(Artist artist);
    
    public int updateProfilePic(String location, int usr_id);
}
