package com.example.connectors;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.models.Customer;
import com.example.models.ListCustomer;

import java.util.ArrayList;

public class CustomerConnector {
    ListCustomer listCustomer;

    public CustomerConnector() {
        listCustomer = new ListCustomer();
        listCustomer.generate_sample_dataset();
    }

    public ArrayList<Customer> get_all_customers() {
        if (listCustomer == null) {
            listCustomer = new ListCustomer();
            listCustomer.generate_sample_dataset();
        }
        return listCustomer.getCustomers();
    }

    public ArrayList<Customer> get_customers_by_provider(String provider) {
        if (listCustomer == null) {
            listCustomer = new ListCustomer();
            listCustomer.generate_sample_dataset();
        }
        ArrayList<Customer> results = new ArrayList<>();
        for (Customer c : listCustomer.getCustomers()) {
            if (c.getPhone().startsWith(provider)) {
                results.add(c);
            }
        }
        return results;
    }

    public boolean isExist(Customer c) {
        return listCustomer.isExist(c);
    }

    public void addCustomer(Customer c) {
        listCustomer.addCustomer(c);
    }

    /**
     * Đây là hàm truy vấn toàn bộ dữ liệu khách hàng
     * sau đó mô hình hóa hướng đối tượng
     * @param database
     * @return trả về ListCustomer
     */
    public ListCustomer getAllCustomer(SQLiteDatabase database){
        listCustomer = new ListCustomer();
        Cursor cursor = database.rawQuery("SELECT * FROM Customer",null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String Name = cursor.getString(1);
            String Email = cursor.getString(2);
            String Phone = cursor.getString(3);
            String UserName = cursor.getString(4);
            String Password = cursor.getString(5);
            Customer c = new Customer();
            c.setId(id);
            c.setName(Name);
            c.setEmail(Email);
            c.setPhone(Phone);
            c.setUsername(UserName);
            c.setPassword(Password);
            listCustomer.addCustomer(c);
            //To do something ….
        }
        cursor.close();
        return listCustomer;
    }

    public long saveNewCustomer(SQLiteDatabase database, Customer c){
        ContentValues values = new ContentValues();
        values.put("Name",c.getName());
        values.put("Email",c.getEmail());
        values.put("Phone",c.getPhone());
        values.put("Username",c.getEmail());
        values.put("Password",c.getPassword());
        long flag = database.insert("Customer",null,values);
        return flag;
    }

    public long saveUpdateCustomer(SQLiteDatabase database, Customer c){
        ContentValues values = new ContentValues();
        values.put("Name",c.getName());
        values.put("Email",c.getEmail());
        values.put("Phone",c.getPhone());
        values.put("Username",c.getEmail());
        values.put("Password",c.getPassword());
        long flag = database.update("Customer",values,"Id=?",new String[]{c.getId()+""});
        return flag;
    }

    public long removeCustomer(SQLiteDatabase database, String id){
        int flag = database.delete("Customer","Id=?",new String[]{id});
        return flag;
    }
}
