package com.example.connectors;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.models.Employee;
import com.example.models.ListEmployee;

public class EmployeeConnector {
    public Employee login(SQLiteDatabase database, String usr, String pwd){
        Cursor cursor = database.rawQuery(
                "SELECT * FROM Employee WHERE Username = ? AND Password = ?",
        new String[]{usr,pwd});
        Employee emp = null;
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String Name = cursor.getString(1);
            String Email = cursor.getString(2);
            String Phone = cursor.getString(3);
            String Username = cursor.getString(4);
            String Password = cursor.getString(5);
            emp = new Employee();
            emp.setId(id);
            emp.setName(Name);
            emp.setEmail(Email);
            emp.setPhone(Phone);
            emp.setUsername(Username);
            emp.setPassword(Password);
        }
        cursor.close();

        return emp;

    }
    public Employee login(String usr, String pwd)
    {
        ListEmployee le = new ListEmployee();
        le.gen_dataset();
        for (Employee emp : le.getEmployees())
        {
            if (emp.getUsername().equalsIgnoreCase(usr) && emp.getPassword().equals(pwd))
            {
                return emp;
            }
        }
        return null;
    }
}
