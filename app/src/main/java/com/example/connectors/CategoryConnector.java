package com.example.connectors;

import com.example.models.Category;
import com.example.models.ListCategory;

import java.util.ArrayList;

public class CategoryConnector {
    ListCategory listCategory;

    public CategoryConnector() {
        listCategory = new ListCategory();
        listCategory.generate_sample_product_dataset();
    }

    public ArrayList<Category> get_all_categories() {
        if (listCategory == null) {
            listCategory = new ListCategory();
            listCategory.generate_sample_product_dataset();
        }
        return listCategory.getCategories();
    }

    public Category get_category_by_id(int id) {
        if (listCategory == null) {
            listCategory = new ListCategory();
            listCategory.generate_sample_product_dataset();
        }
        for (Category c : listCategory.getCategories()) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
}
