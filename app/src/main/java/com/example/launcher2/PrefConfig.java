package com.example.launcher2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefConfig {

    private static final String LIST_KEY = "list_key" ;

    public static void writelist(Context context, ArrayList<String> arrayList){
        Gson gson = new Gson();
        String jsonString = gson.toJson(arrayList);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY,jsonString);
        editor.apply();
    }

    public static ArrayList<String> readList(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY,"");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> arrayList = gson.fromJson(jsonString,type);

        return arrayList;
    }

}
