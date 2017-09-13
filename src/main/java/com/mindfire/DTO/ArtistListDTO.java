/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.DTO;

import com.mindfire.model.Artist;
import java.util.List;

/**
 *
 * @author anants
 */
public class ArtistListDTO {
    String code;
    String status;
    String message;
    List<Artist> alist;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Artist> getAlist() {
        return alist;
    }

    public void setAlist(List<Artist> alist) {
        this.alist = alist;
    }
    
}
