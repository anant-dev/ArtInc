/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.service;

import com.mindfire.model.Product;
import java.util.List;

/**
 *
 * @author anants
 */
public interface ProductService {

    public int saveProduct(Product product);

    public List<Product> getProductByArtist(int artist_id);

    public List<Product> getProductByCategory(int c_id);

    public List<Product> getProductBySize(int size);

    public List<Product> getProduct();

    public Product getProductById(int id);

    public int updateProduct(Product product);

    public int deleteProduct(int product_id);

    public int increaseCount(int product_id, String count);
}
