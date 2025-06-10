package com.example.k22411csampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.OrdersDetailAdapter;
import com.example.connectors.OrdersDetailConnector;
import com.example.connectors.SQLiteConnector;
import com.example.models.OrderDetail;
import com.example.models.OrdersViewer;

import java.util.ArrayList;

public class OrdersDetailActivity extends AppCompatActivity {

    private TextView txtOrderId;
    private TextView txtOrderCode;
    private TextView txtOrderDate;
    private TextView txtCustomerName;
    private TextView txtEmployeeName;
    private TextView txtTotalOrderValue;
    private ListView lvOrderDetails;
    private OrdersDetailAdapter orderDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addViews();
        loadOrdersDetails();
    }

    private void addViews() {
        txtOrderId = findViewById(R.id.txtOrderId);
        txtOrderCode = findViewById(R.id.txtOrderCode);
        txtOrderDate = findViewById(R.id.txtOrderDate);
        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtEmployeeName = findViewById(R.id.txtEmployeeName);
        txtTotalOrderValue = findViewById(R.id.txtTotalOrderValue);
        lvOrderDetails = findViewById(R.id.lvOrderDetails);

        orderDetailAdapter = new OrdersDetailAdapter(this, R.layout.item_orders_detail);
        lvOrderDetails.setAdapter(orderDetailAdapter);
    }
    private void loadOrdersDetails() {
        Intent intent = getIntent();
        if (intent.hasExtra("SELECTED_ORDER")) {
            OrdersViewer order = (OrdersViewer) intent.getSerializableExtra("SELECTED_ORDER");

            // Set order header information
            txtOrderId.setText(String.valueOf(order.getId()));
            txtOrderCode.setText(order.getCode());
            txtOrderDate.setText(order.getOrderDate());
            txtCustomerName.setText(order.getCustomerName());
            txtEmployeeName.setText(order.getEmployeeName());
            txtTotalOrderValue.setText(String.format("%.2f", order.getTotalOrderValue()));

            // Load order details from database
            SQLiteConnector connector = new SQLiteConnector(this);
            connector.openDatabase();
            OrdersDetailConnector detailConnector = new OrdersDetailConnector();
            ArrayList<OrderDetail> details = detailConnector.getOrderDetailsByOrderId(connector.getDatabase(), order.getId());

            // Add data to adapter
            orderDetailAdapter.clear();
            for (OrderDetail detail : details) {
                orderDetailAdapter.add(detail);
            }
            orderDetailAdapter.notifyDataSetChanged();
        }
    }
}