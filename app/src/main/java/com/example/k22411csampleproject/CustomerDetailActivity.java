package com.example.k22411csampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.models.Customer;

public class CustomerDetailActivity extends AppCompatActivity {

    EditText edtCustomerID;
    EditText edtCustomerName;
    EditText edtCustomerEmail;
    EditText edtCustomerPhone;
    EditText edtCustomerUsername;
    EditText edtCustomerPassword;
    Button btnNew;
    Button btnSave;
    Button btnRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvents();
    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process_save_customer();
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process_remove_customer();
            }
        });
        }

    private void process_remove_customer() {
        Intent intent = getIntent();
        String id = edtCustomerID.getText().toString();
        intent.putExtra("REMOVED CUSTOMER ID",id);
        setResult(600, intent);
        finish();
    }

    private void process_save_customer() {
        //Lấy giữ liệu trên giao diện để mô hình hóa lại hướng đối tượng Customer:
        Customer c = new Customer();

        c.setId(Integer.parseInt(edtCustomerID.getText().toString()));
        c.setName(edtCustomerName.getText().toString());
        c.setEmail(edtCustomerEmail.getText().toString());
        c.setPhone(edtCustomerPhone.getText().toString());
        c.setUsername(edtCustomerUsername.getText().toString());
        c.setPassword(edtCustomerPassword.getText().toString());

        //lấy intent từ màn hình gọi nó:
        Intent intent = getIntent();
        if (intent.hasExtra("SELECTED_CUSTOMER")) {
            intent.putExtra("UPDATED CUSTOMER", c);
        } else {
            intent.putExtra("NEW CUSTOMER", c);
        }
        setResult(500, intent);
        finish();
    }

    private void addViews() {
        edtCustomerID=findViewById(R.id.edtProductID);
        edtCustomerName=findViewById(R.id.edtProductName);
        edtCustomerEmail=findViewById(R.id.edtProductQuantity);
        edtCustomerPhone=findViewById(R.id.edtProductPrice);
        edtCustomerUsername=findViewById(R.id.edtProductCateID);
        edtCustomerPassword=findViewById(R.id.edtProductImageID);
        display_infor();

        btnNew=findViewById(R.id.btnNew);
        btnSave=findViewById(R.id.btnSave);
        btnRemove=findViewById(R.id.btnRemove);
    }

    private void display_infor() {
        //Lấy intent từ bên CustomerManagementActivity gửi qua:
        Intent intent = getIntent();
        //Lấy dữ liệu Selected Customer từu intent:
        Customer c = (Customer) intent.getSerializableExtra("SELECTED_CUSTOMER");
        if(c==null)
            return;
        //Hiển thị thông tin Customer lên giao diện:
        edtCustomerID.setText(c.getId()+"");
        edtCustomerName.setText(c.getName());
        edtCustomerEmail.setText(c.getEmail());
        edtCustomerPhone.setText(c.getPhone());
        edtCustomerUsername.setText(c.getUsername());
        edtCustomerPassword.setText(c.getPassword());

    }
}