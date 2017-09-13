/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.dao;

import com.mindfire.model.Order;
import java.util.List;

/**
 *
 * @author anants
 */
public interface OrderDao {
    public boolean saveOrder(Order order);
    public List<Order> getOrder(int user_id);
}
