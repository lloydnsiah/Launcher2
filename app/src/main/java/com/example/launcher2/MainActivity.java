package com.example.launcher2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<AppObject> arrayList = new ArrayList<>();
    private AppAdapter adapter;
    private AppAdapterLinear adaterlinear;
    private Context context;
    private ImageView imageView;
    private MyPreferences myPrefrences;
    private Boolean view;
    private LinearLayout mainlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        recyclerView = findViewById(R.id.recyclerview);
        imageView = findViewById(R.id.home_image);
        myPrefrences = MyPreferences.getInstance(context);
        arrayList= getInstalledAppList();
        view = myPrefrences.getGRID();
        mainlayout = findViewById(R.id.layoutLinear);

        changeView(view);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Boolean grid = myPrefrences.getGRID();
//                if (grid){
//                    myPrefrences.saveGRID(false);
//                    changeView(grid);
//                }else{
//                    myPrefrences.saveGRID(true);
//                    changeView(grid);
//                }
//            }
//        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, "Change Screen Layout..... ", Snackbar.LENGTH_LONG)
                        .setAction("Change", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Boolean grid = myPrefrences.getGRID();
                                if (grid){
                                    myPrefrences.saveGRID(false);
                                    changeView(grid);
                                }else{
                                    myPrefrences.saveGRID(true);
                                    changeView(grid);
                                }
                            }
                        }).setTextColor(Color.YELLOW).show();
                return true;
            }
        });


    }

    private void changeView(Boolean value){
        value = myPrefrences.getGRID();
        if (value){
            GridLayoutManager manager = new GridLayoutManager(context,2);
            recyclerView.setLayoutManager(manager);
            adapter = new AppAdapter(context,arrayList);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

        }else{
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adaterlinear = new AppAdapterLinear(context,arrayList);
            adaterlinear.notifyDataSetChanged();
            recyclerView.setAdapter(adaterlinear);
        }
    }

    private ArrayList<AppObject> getInstalledAppList() {
        ArrayList<AppObject> list = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ArrayList<ResolveInfo> untreateAppList = (ArrayList<ResolveInfo>) getApplicationContext().getPackageManager().queryIntentActivities(intent,0);
        for (ResolveInfo info : untreateAppList){
            String appname = info.activityInfo.loadLabel(getPackageManager()).toString();
            String appPackagename = info.activityInfo.packageName;
            Drawable appImage = info.activityInfo.loadIcon(getPackageManager());
            AppObject object = new AppObject(appPackagename,appname,appImage);
            if (!list.contains(object)){
                list.add(object);
            }
        }

        return list;
    }


}