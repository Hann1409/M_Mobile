package com.example.k22411csampleproject;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.PaymentAdapter;
import com.example.connectors.SQLiteConnector;
import com.example.models.ListPayment;

public class PaymentManagementActivity extends AppCompatActivity {

    ListView lvPayment;
    PaymentAdapter adapter;
    ListPayment lpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addView();
    }

    private void addView() {
        lvPayment = findViewById(R.id.lvPayment);
        adapter = new PaymentAdapter(PaymentManagementActivity.this,R.layout.item_payment);
        lvPayment.setAdapter(adapter);
        lpm = new ListPayment();
//        lpm.gen_payments();
//        adapter.addAll(lpm.getPayments());
        SQLiteConnector connector = new SQLiteConnector(this);
        lpm = lpm.getAllPayments(connector.openDatabase());
        adapter.addAll(lpm.getPayments());
    }
}