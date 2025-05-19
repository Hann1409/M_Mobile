package com.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ListProduct implements Serializable {
    private ArrayList<Product> products;

    public ListProduct() {
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public void generate_sample_dataset() {
        Random random = new Random();
        
        // Sample products for Electronics (category_id = 1)
        addProduct(new Product(1, "Smartphone", 50, 999.99, 1, "Latest model smartphone"));
        addProduct(new Product(2, "Laptop", 30, 1299.99, 1, "High-performance laptop"));
        addProduct(new Product(3, "Headphones", 100, 199.99, 1, "Wireless noise-cancelling headphones"));
        
        // Sample products for Clothing (category_id = 2)
        addProduct(new Product(4, "T-Shirt", 200, 29.99, 2, "Cotton t-shirt"));
        addProduct(new Product(5, "Jeans", 150, 79.99, 2, "Classic blue jeans"));
        addProduct(new Product(6, "Sneakers", 80, 89.99, 2, "Comfortable running shoes"));
        
        // Sample products for Books (category_id = 3)
        addProduct(new Product(7, "Novel", 300, 19.99, 3, "Bestselling fiction novel"));
        addProduct(new Product(8, "Textbook", 50, 49.99, 3, "Educational textbook"));
        addProduct(new Product(9, "Cookbook", 75, 24.99, 3, "Collection of recipes"));
        
        // Sample products for Food (category_id = 4)
        addProduct(new Product(10, "Cereal", 100, 5.99, 4, "Breakfast cereal"));
        addProduct(new Product(11, "Pasta", 150, 3.99, 4, "Italian pasta"));
        addProduct(new Product(12, "Snacks", 200, 2.99, 4, "Assorted snacks"));
    }
} 