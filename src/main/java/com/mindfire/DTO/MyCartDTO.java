/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.DTO;

import com.mindfire.model.Order;
import com.mindfire.model.Product;
import java.util.List;

/**
 *
 * @author anants
 */
public class MyCartDTO {
    String code;
    String status;
    String message;
    List<Order> olist;
    List<Product> plist;
    

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

    public List<Order> getOlist() {
        return olist;
    }

    public void setOlist(List<Order> olist) {
        this.olist = olist;
    }

    public List<Product> getPlist() {
        return plist;
    }

    public void setPlist(List<Product> plist) {
        this.plist = plist;
    }
    
}
