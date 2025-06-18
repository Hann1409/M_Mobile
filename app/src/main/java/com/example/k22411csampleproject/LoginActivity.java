package com.example.k22411csampleproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.connectors.EmployeeConnector;
import com.example.connectors.SQLiteConnector;
import com.example.models.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName;
    EditText edtPassword;
    CheckBox chkSaveLogin;

    String DATABASE_NAME="Sale_Database.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;

    BroadcastReceiver networkReceiver=null;

    Button btnLogin;
    TextView txtNetworkType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        processCopy();
        setupBroadcastReceiver();
    }

    private void setupBroadcastReceiver() {
        // Initialize and register the BroadcastReceiver for network changes
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                checkNetworkStatus();
            }
        };
    }

    private boolean checkNetworkStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            btnLogin.setVisibility(View.VISIBLE);
            int type = networkInfo.getType();
            int subType = networkInfo.getSubtype();
            String typeName = "";
            int bgColor = 0xFFCCCCCC; // Default gray
            if (type == ConnectivityManager.TYPE_WIFI) {
                typeName = "WiFi";
                bgColor = 0xFF2196F3; // Blue
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                switch (subType) {
                    case 13: // LTE
                        typeName = "4G";
                        bgColor = 0xFF4CAF50; // Green
                        break;
                    case 3: // UMTS
                    case 5: // EVDO_0
                    case 6: // EVDO_A
                    case 8: // HSDPA
                    case 9: // HSUPA
                    case 10: // HSPA
                    case 12: // EVDO_B
                    case 15: // HSPAP
                        typeName = "3G";
                        bgColor = 0xFFFFC107; // Amber
                        break;
                    default:
                        typeName = "Mobile";
                        bgColor = 0xFFFF9800; // Orange
                }
            } else {
                typeName = networkInfo.getTypeName();
            }
            if (txtNetworkType != null) {
                txtNetworkType.setText(typeName);
                txtNetworkType.setBackgroundColor(bgColor);
            }
            return true;
        } else {
            btnLogin.setVisibility(View.INVISIBLE);
            if (txtNetworkType != null) {
                txtNetworkType.setText("No Connection");
                txtNetworkType.setBackgroundColor(0xFFF44336); // Red
            }
            return false;
        }
    }

    private void addViews() {
        edtUserName=findViewById(R.id.edtUserName);
        edtPassword=findViewById(R.id.edtPassword);
        chkSaveLogin=findViewById(R.id.chkSaveLoginInfor);
        btnLogin=findViewById(R.id.btnLogin);
        txtNetworkType=findViewById(R.id.txtNetworkType);
    }

    public void do_login(View view) {
        Resources res=getResources();

        String usr=edtUserName.getText().toString();
        String pwd=edtPassword.getText().toString();
        EmployeeConnector ec=new EmployeeConnector();

//        Employee emp = ec.login(new SQLiteConnector(this).openDatabase(),usr,pwd);
        SQLiteConnector sqLiteConnector = new SQLiteConnector(this);
        sqLiteConnector.openDatabase();
        Employee emp=ec.login(sqLiteConnector.getDatabase(),usr,pwd);
        if(emp!=null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,
                    res.getText(R.string.title_login_fair_message),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void do_exit(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        Resources res=getResources();
        //tiêu đề:
        builder.setTitle(res.getText(R.string.confirm_exit_title));
        //nội dung cửa sổ:
        builder.setMessage(res.getText(R.string.confirm_exit_message));
        //biểu tượng:
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        //thiết lập tương tác YES:
        builder.setPositiveButton(res.getText(R.string.confirm_exit_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //System.exit(0);
                finish();
            }
        });
        builder.setNegativeButton(res.getText(R.string.confirm_exit_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public void saveLoginInformation(){
        SharedPreferences preferences = getSharedPreferences("LOGIN_INFORMATION",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String usr=edtUserName.getText().toString();
        String pwd=edtPassword.getText().toString();
        boolean isSave = chkSaveLogin.isChecked();
        editor.putString("USERNAME",usr);
        editor.putString("PASSWORD",pwd);
        editor.putBoolean("SAVED",isSave);
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLoginInformation();
        if(networkReceiver!=null){
            unregisterReceiver(networkReceiver);
        }
    }
    public void restoreLoginInformation(){
        SharedPreferences preferences = getSharedPreferences("LOGIN_INFORMATION",MODE_PRIVATE);
        String usr = preferences.getString("USERNAME","");
        String pwd = preferences.getString("PASSWORD","");
        boolean isSave = preferences.getBoolean("SAVED",true);
        if(isSave){
            edtUserName.setText(usr);
            edtPassword.setText(pwd);
            chkSaveLogin.setChecked(isSave);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreLoginInformation();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver,filter);
    }

    private void processCopy() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists())
        {
            try
            {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset()
    {
        try {
            InputStream myInput;

            myInput = getAssets().open(DATABASE_NAME);


            // Path to the just created empty db
            String outFileName = getDatabasePath();

            // if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}