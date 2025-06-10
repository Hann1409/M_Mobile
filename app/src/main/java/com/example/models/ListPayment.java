package com.example.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ListPayment {
    ArrayList<Payment> payments;

    public ListPayment(){
        payments = new ArrayList<>();
    }

    public ListPayment(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }
    public void gen_payments(){
        payments.add(new Payment(1, "Banking Account", "Chuyển khoản ngân hàng"));
        payments.add(new Payment(2,"MOMO","Thanh toán ví MOMO"));
        payments.add(new Payment(3, "CASH", "Thanh toán tiền mặt"));
        payments.add(new Payment(4, "COD", "Nhận hàng rồi thanh toán"));
    }

    public ListPayment getAllPayments(SQLiteDatabase database) {
        payments.clear(); // Xóa danh sách hiện tại để tránh trùng lặp
        Cursor cursor = database.rawQuery("SELECT * FROM Payment", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
                payments.add(new Payment(id, name, description));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return this;
    }

}
