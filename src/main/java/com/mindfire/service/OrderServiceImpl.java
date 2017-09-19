/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.service;

import com.mindfire.dao.OrderDao;
import com.mindfire.model.Order;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anants
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderDao orderDao;

    @Override
    public int saveOrder(Order order) {
        return orderDao.saveOrder(order);
    }

    @Override
    public List<Order> getOrder(int user_id ,int status) {
       return orderDao.getOrder(user_id,status);
    }

    @Override
    public int deleteOrder(int order_id) {
        return orderDao.deleteOrder(order_id);
    }

    @Override
    public int updateOrder(int order_id, int status) {
        return orderDao.updateOrder(order_id, status);
    }

}
