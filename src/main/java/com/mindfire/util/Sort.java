/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.util;

import com.mindfire.model.Product;
import java.util.Comparator;

/**
 *
 * @author anants
 */
public class Sort {

    public static Comparator<Product> ByPriceLH = new Comparator<Product>() {

        public int compare(Product p1, Product p2) {

            int priceP1 = Integer.parseInt(p1.getPrice());
            int priceP2 = Integer.parseInt(p2.getPrice());
            return priceP1 - priceP2;

        }
    };
    public static Comparator<Product> ByPriceHL = new Comparator<Product>() {

        public int compare(Product p1, Product p2) {

            int priceP1 = Integer.parseInt(p1.getPrice());
            int priceP2 = Integer.parseInt(p2.getPrice());
            return priceP2 - priceP1;

        }
    };

    public static Comparator<Product> ByPopularity = new Comparator<Product>() {

        @Override
        public int compare(Product p1, Product p2) {

            int popularP1 = Integer.parseInt(p1.getNumb_sold());
            int popularP2 = Integer.parseInt(p2.getNumb_sold());

            /*For descending order*/
            return popularP2 - popularP1;

        }
    };

    public static Comparator<Product> ByNameAZ = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            String titleP1 = p1.getTitle();
            String titleP2 = p2.getTitle();
            return titleP1.toLowerCase().compareTo(titleP2.toLowerCase());
        }
    };

    public static Comparator<Product> ByNameZA = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            String titleP1 = p1.getTitle();
            String titleP2 = p2.getTitle();
            return titleP2.toLowerCase().compareTo(titleP1.toLowerCase());
        }
    };

}
