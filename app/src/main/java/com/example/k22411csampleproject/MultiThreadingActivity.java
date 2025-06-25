package com.example.k22411csampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MultiThreadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multi_threading);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void openHandlerSendMessageActivity(View view) {
        Intent intent = new Intent(MultiThreadingActivity.this, HandlerSendMessageActivity.class);
        startActivity(intent);
    }
    public void openHandlerPostMessageActivity(View view){
        Intent intent = new Intent(MultiThreadingActivity.this, HandlerPostMessageActivity.class);
        startActivity(intent);
    }
    public void openAsyncTaskActivity(View view){
        Intent intent = new Intent(MultiThreadingActivity.this, AsyncTaskActivity.class);
        startActivity(intent);
    }
}