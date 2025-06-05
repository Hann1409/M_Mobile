package com.example.k22411csampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.models.Product;

public class ProductDetailActivity extends AppCompatActivity {

    EditText edtProductID;
    EditText edtProductName;
    EditText edtProductQuantity;
    EditText edtProductPrice;
    EditText edtProductCateID;
    EditText edtProductDescription;
    EditText edtProductImageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        displayInfo();
    }

    private void addViews() {
        edtProductID = findViewById(R.id.edtProductID);
        edtProductName = findViewById(R.id.edtProductName);
        edtProductQuantity = findViewById(R.id.edtProductQuantity);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductCateID = findViewById(R.id.edtProductCateID);
        edtProductDescription = findViewById(R.id.edtProductDescription);
        edtProductImageID = findViewById(R.id.edtProductImageID);
    }

    private void displayInfo() {
        Intent intent = getIntent();
        Product p = (Product) intent.getSerializableExtra("SELECTED_PRODUCT");
        if (p == null) {
            return; // Để trống các trường khi thêm sản phẩm mới
        }
        edtProductID.setText(String.valueOf(p.getId()));
        edtProductName.setText(p.getName());
        edtProductQuantity.setText(String.valueOf(p.getQuantity()));
        edtProductPrice.setText(String.valueOf(p.getPrice()));
        edtProductCateID.setText(String.valueOf(p.getCate_id()));
        edtProductDescription.setText(p.getDescription());
        edtProductImageID.setText(String.valueOf(p.getImage_id()));
    }
}