package com.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ListCategory implements Serializable {
    private ArrayList<Category> categories;

    public ListCategory() {
        categories = new ArrayList<>();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category c) {
        categories.add(c);
    }

    public void generate_sample_dataset() {
        String[] categoryNames = {"Electronics", "Clothing", "Books", "Food", "Furniture", 
                                "Sports", "Beauty", "Toys", "Home", "Automotive"};
        
        for (int i = 0; i < categoryNames.length; i++) {
            Category c = new Category(i + 1, categoryNames[i]);
            addCategory(c);
        }
    }
} 