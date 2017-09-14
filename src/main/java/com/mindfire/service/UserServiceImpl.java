/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.service;

import com.mindfire.dao.UserDao;
import com.mindfire.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anants
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    
    @Override
    public int saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    public User getUser(String email) {
        User user =userDao.getUser(email);
        return user;
    }
    
}
