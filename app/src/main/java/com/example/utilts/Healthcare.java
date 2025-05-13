package com.example.utilts;

import android.app.Activity;

import com.example.k22411csampleproject.R;

public class Healthcare {
    public static BMIResult calculate(double heightCm, double weightKg, Activity context){
        double heightM = heightCm / 100.0; // chuyển từ cm sang mét
        double BMI = weightKg / Math.pow(heightM, 2);

        BMIResult result = new BMIResult();
        result.setBMI(BMI);

        String des = "";
        if (BMI < 18.5)
            des = context.getResources().getString(R.string.title_slim);
        else if (BMI < 23)
            des = context.getResources().getString(R.string.title_normal);
        else
            des = context.getResources().getString(R.string.title_fat);

        result.setDescription(des);
        return result;
    }
}

