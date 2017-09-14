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
import org.springframework.stereotype.Service;

/**
 *
 * @author anants
 */
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductDao productDao;

    @Override
    public int saveProduct(Product product) {
       return productDao.saveProduct(product);
    }

    @Override
    public List<Product> getProductByArtist(int artist_id) {
       return productDao.getProductByArtist(artist_id);
    }
     
    @Override
    public List<Product> getProduct() {
        return productDao.getProduct();
    }
    

    @Override
    public int updateProduct(Product product) {
        return productDao.updateProduct(product);
    }

    @Override
    public int deleteProduct(int product_id) {
        return productDao.deleteProduct(product_id);
    }

  
}
