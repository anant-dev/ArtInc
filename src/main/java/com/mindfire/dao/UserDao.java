/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.dao;

import com.mindfire.model.User;

/**
 *
 * @author anants
 */
public interface UserDao {
    public int saveUser(User user);
    public User getUser(String email);
}
