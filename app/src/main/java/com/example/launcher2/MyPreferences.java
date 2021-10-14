package com.example.launcher2;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {

    private static com.example.launcher2.MyPreferences myPreference;
    private SharedPreferences sharedPreferences;

    public static com.example.launcher2.MyPreferences getInstance(Context context) {
        if (myPreference == null) {
            myPreference = new com.example.launcher2.MyPreferences(context);
        }
        return myPreference;
    }

    private MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("com.example.launcher2",Context.MODE_PRIVATE);
    }
       //grid of not grid
    public void saveGRID(Boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putBoolean("GRID", value);
        prefsEditor.commit();
    }

    public boolean getGRID() {
        if (sharedPreferences!= null) {
            return sharedPreferences.getBoolean("GRID", false);
        }
        return false;
    }


//    MyPreferences myPrefrences = MyPreferences.getInstance(context);
//    myPreferences.saveData(YOUR_KEY,YOUR_VALUE);
//
//    String value = yourPreferences.getData(YOUR_KEY);
}
