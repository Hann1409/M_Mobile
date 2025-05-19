package com.example.connectors;

import com.example.models.Product;
import com.example.models.ListProduct;

import java.util.ArrayList;

public class ProductConnector {
    ListProduct listProduct;
    
    public ProductConnector() {
        listProduct = new ListProduct();
        listProduct.generate_sample_dataset();
    }
    
    public ArrayList<Product> get_all_products() {
        if (listProduct == null) {
            listProduct = new ListProduct();
            listProduct.generate_sample_dataset();
        }
        return listProduct.getProducts();
    }
    
    public ArrayList<Product> get_products_by_category(int categoryId) {
        if (listProduct == null) {
            listProduct = new ListProduct();
            listProduct.generate_sample_dataset();
        }
        ArrayList<Product> results = new ArrayList<>();
        for (Product p : listProduct.getProducts()) {
            if (p.getCate_id() == categoryId) {
                results.add(p);
            }
        }
        return results;
    }
    
    public Product get_product_by_id(int id) {
        if (listProduct == null) {
            listProduct = new ListProduct();
            listProduct.generate_sample_dataset();
        }
        for (Product p : listProduct.getProducts()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
