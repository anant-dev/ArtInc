/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.dao;

import com.mindfire.model.Product;
import java.util.List;

/**
 *
 * @author anants
 */
public interface ProductDao {
    public boolean saveProduct(Product product);
    public List<Product> getOrder(int artist_id);
    public boolean updateProduct(Product product);
    public int deleteProduct(int product_id);
}
