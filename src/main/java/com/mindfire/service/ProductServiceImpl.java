/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.service;

import com.mindfire.dao.ProductDao;
import com.mindfire.model.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author anants
 */
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductDao productDao;

    @Override
    public boolean saveProduct(Product product) {
       return productDao.saveProduct(product);
    }

    @Override
    public List<Product> getOrder(int artist_id) {
       return productDao.getOrder(artist_id);
    }

    @Override
    public boolean updateProduct(Product product) {
        return productDao.updateProduct(product);
    }

    @Override
    public int deleteProduct(int product_id) {
        return productDao.deleteProduct(product_id);
    }
    
}
