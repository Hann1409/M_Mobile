package com.example.models;

public class Orders {
    private int id;
    private String Code;
    private String Name;
    private int EmployeeId;
    private int CustomerId;
    private String OrderDate;
    private String Description;
    private int Status;
    private String StatusDescription;

    public Orders() {
    }

    public Orders(int id, String code, String name, int employeeId, int customerId, String orderDate, String description, int status, String statusDescription) {
        this.id = id;
        Code = code;
        Name = name;
        EmployeeId = employeeId;
        CustomerId = customerId;
        OrderDate = orderDate;
        Description = description;
        Status = status;
        StatusDescription = statusDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int employeeId) {
        EmployeeId = employeeId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }
}
