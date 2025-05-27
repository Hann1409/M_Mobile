package com.example.k22411csampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.connectors.CustomerConnector;
import com.example.models.Customer;

public class CustomerManagementActivity extends AppCompatActivity {

    ListView lvCustomer;
    ArrayAdapter<Customer> adapter;
    CustomerConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addView();
        addEvents();
    }

    private void addEvents() {
        lvCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Customer selected = adapter.getItem(position);
//                adapter.remove(selected);
                Customer c = adapter.getItem(position);
                displayCustomerDetailActivity(c);
                return false;
            }
        });
//        lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Customer c = adapter.getItem(position);
//                displayCustomerDetailActivity(c);
//            }
//        });
    }

    private void displayCustomerDetailActivity(Customer c) {
        Intent intent = new Intent(CustomerManagementActivity.this, CustomerDetailActivity.class);
        intent.putExtra("SELECTED_CUSTOMER",c);
        startActivity(intent);
    }

    private void addView() {
        lvCustomer = findViewById(R.id.lvCustomer);
        adapter = new ArrayAdapter<>(CustomerManagementActivity.this, android.R.layout.simple_list_item_1);
        connector = new CustomerConnector();
        adapter.addAll(connector.get_all_customers());
        lvCustomer.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.option_menu_customer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_new_customer){

            Toast.makeText(CustomerManagementActivity.this,"Mở màn hình thêm mới khách hàng",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CustomerManagementActivity.this, CustomerDetailActivity.class);
            //đóng gói và đặt mã request code là 300
            startActivityForResult(intent,300);
            //startActivity(intent)
        }
        else if(item.getItemId()==R.id.menu_broadcast_advertising){

            Toast.makeText(CustomerManagementActivity.this,"Gửi quảng cáo hàng loạt tới khách hàng",Toast.LENGTH_LONG).show();

        }
        else if(item.getItemId()==R.id.mernu_help){

            Toast.makeText(CustomerManagementActivity.this,"Mở Website hướng dẫn sử dụng",Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //trường hợp xử lý cho NEW CUSTOMER ta chỉ quan tâm 300 và 500 do ta định nghĩa:
        if (requestCode==300 && resultCode==500){
            //lấy gói tin ra:
            Customer c =(Customer) data.getSerializableExtra("NEW CUSTOMER");
            process_save_customer(c);
            
        }
    }

    private void process_save_customer(Customer c) {
        boolean result = connector.isExist(c);
        if(result==true){
            //tức l customer này đã tồn tại trong hệ thống
            //họ có nhu cầu sử các thông tin khác, ví dụ:
            //Địa chỉ, payment method,..
            //Sinh viên tự xử lý trường hợp sửa thông tin
        }
        else{
            //là thêm mới Customer
            connector.addCustomer(c);
            adapter.clear();
            adapter.addAll(connector.get_all_customers());
        }
    }
}