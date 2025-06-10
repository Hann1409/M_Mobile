package com.example.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.k22411csampleproject.R;
import com.example.models.Payment;

public class PaymentAdapter extends ArrayAdapter<Payment> {
    Activity context;
    int resource;
    Typeface typeface;
    public PaymentAdapter(@NonNull Activity context, int resource){
        super(context, resource);
        this.context = context;
        this.resource = resource;
        typeface = Typeface.createFromAsset(this.context.getAssets(),"font/TMC-Ong Do.TTF");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource,null);
        TextView txtPaymentName = item.findViewById(R.id.txtPaymentName);
        TextView textPaymentDescription = item.findViewById(R.id.txtPaymentDescription);

        Payment pm = getItem(position);
        txtPaymentName.setText(pm.getName());
        textPaymentDescription.setText(pm.getDescription());

        txtPaymentName.setTypeface(this.typeface);
        return item;
        

    }
}
