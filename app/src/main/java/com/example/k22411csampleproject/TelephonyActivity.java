package com.example.k22411csampleproject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.adapters.TelephonyInforAdapter;
import com.example.models.TelephonyInfor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TelephonyActivity extends AppCompatActivity {

    ListView lvTelephony;
    TelephonyInforAdapter adapter;
    ArrayList<TelephonyInfor> allContacts = new ArrayList<>();
    private static final int REQUEST_READ_CONTACTS = 100;
    private static final int REQUEST_CALL_PHONE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_telephony);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        } else {
            getAllContacts();
        }
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_telephony, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_filter_viettel) {
            filterByProvider("viettel");
            return true;
        } else if (id == R.id.menu_filter_mobile) {
            filterByProvider("mobile");
            return true;
        } else if (id == R.id.menu_filter_other) {
            filterByProvider("other");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAllContacts();
            }
        } else if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, thực hiện cuộc gọi
            } else {
                Toast.makeText(this, "Permission denied to make phone calls", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getAllContacts() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,null, null,null, null);
        adapter.clear();
        allContacts.clear();
        while (cursor.moveToNext()){
            int nameCol = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int phoneCol = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameCol);
            String phone = cursor.getString(phoneCol);
            TelephonyInfor infor = new TelephonyInfor();
            infor.setName(name);
            infor.setPhone(phone);
            adapter.add(infor);
            allContacts.add(infor);
        }
        cursor.close();
    }

    private void filterByProvider(String provider) {
        ArrayList<TelephonyInfor> filtered = new ArrayList<>();
        List<String> viettelPrefixes = Arrays.asList("086", "096", "097", "098", "032", "033", "034", "035", "036", "037", "038", "039");
        List<String> mobifonePrefixes = Arrays.asList("089", "090", "093", "070", "079", "077", "076", "078");
        for (TelephonyInfor infor : allContacts) {
            String phone = infor.getPhone().replaceAll("\\D", ""); // loại bỏ ký tự không phải số
            // Nếu số bắt đầu bằng 84 (sau khi loại bỏ ký tự đặc biệt), thay bằng 0
            if (phone.startsWith("84") && phone.length() > 9) {
                phone = "0" + phone.substring(2);
            }
            if (phone.length() >= 3) {
                String prefix = phone.substring(0, 3);
                boolean isViettel = viettelPrefixes.contains(prefix);
                boolean isMobifone = mobifonePrefixes.contains(prefix);
                if (provider.equals("viettel") && isViettel) {
                    filtered.add(infor);
                } else if (provider.equals("mobile") && isMobifone) {
                    filtered.add(infor);
                } else if (provider.equals("other") && !isViettel && !isMobifone) {
                    filtered.add(infor);
                }
            }
        }
        adapter.clear();
        adapter.addAll(filtered);
        adapter.notifyDataSetChanged();
    }

    private void addViews() {
        lvTelephony=findViewById(R.id.lvTelephony);
        adapter=new TelephonyInforAdapter(this,R.layout.item_telephony_infor);
        lvTelephony.setAdapter(adapter);
    }

    private void addEvents() {

    }

    public void directCall(TelephonyInfor ti){
        Uri uri = Uri.parse("tel:" + ti.getPhone());
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(uri);
        startActivity(intent);
    }

    public void dialupCall(TelephonyInfor ti){
        Uri uri = Uri.parse("tel:" + ti.getPhone());
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(uri);
        startActivity(intent);
    }

    }