package com.example.models;

import java.util.ArrayList;

public class ListEmployee {
    private ArrayList<Employee> employees;

    public ListEmployee() {
        employees = new ArrayList<>();

    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
    public void gen_dataset(){
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("John");
        e1.setEmail("John@gmail.com");
        e1.setPhone("0986666976");
        e1.setUsername("Hann");
        e1.setPassword("123");
        employees.add(e1);

        Employee e2 = new Employee();
        e2.setId(2);
        e2.setName("Fen");
        e2.setEmail("Fen@gmail.com");
        e2.setPhone("0956444331");
        e2.setUsername("Zack");
        e2.setPassword("642");
        employees.add(e2);

        Employee e3 = new Employee();
        e3.setId(3);
        e3.setName("Tom");
        e3.setEmail("Tom@gmail.com");
        e3.setPhone("0943847592");
        e3.setUsername("nnn");
        e3.setPassword("867");
        employees.add(e3);
    }
}
