package com.example.launcher2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class AllApps extends AppCompatActivity {
    private RecyclerView recyclerView,hiddenRecyclerview;
    private ArrayList<AppObject> arrayList = new ArrayList<>();
    private ArrayList<AppObject>  hiddenAppList = new ArrayList<>();
    private AllAppsAdapter adapter;
    private HiddenAppsAdapter adapter1;
    private ArrayList<String> hiddenList = new ArrayList<>();
    private Context context;
    public static Button btn_save;
    private ProgressDialog progressDialog;
    private CoordinatorLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps);

        context = getApplicationContext();
        recyclerView = findViewById(R.id.allAppsRecyclerView);
        hiddenRecyclerview = findViewById(R.id.hiddenAppsRecyclerview);
        btn_save = findViewById(R.id.btn_save);
        layout = findViewById(R.id.main_linear_layout);

        generalMethod();

//        arrayList = getInstalledAppList();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(Color.TRANSPARENT);
               progressDialog = new ProgressDialog(AllApps.this);
               progressDialog.setMax(100);
               progressDialog.setMessage("Apps changes in progress....");
               progressDialog.setTitle("Changing app settings.");
               progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
               progressDialog.show();

               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                      Intent intent = new Intent(context,MainActivity.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      startActivity(intent);
                      finish();
                      progressDialog.dismiss();
                   }
               },1000);
            }
        });


    }

    private ArrayList<AppObject> getInstalledAppList() {
        hiddenAppList.clear();

        ArrayList<AppObject> list = new ArrayList<>();
        ArrayList<String> arrayList = PrefConfig.readList(context);
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ArrayList<ResolveInfo> untreateAppList = (ArrayList<ResolveInfo>) getApplicationContext().getPackageManager().queryIntentActivities(intent,0);
        for (ResolveInfo info : untreateAppList){
            String appname = info.activityInfo.loadLabel(getPackageManager()).toString();
            String appPackagename = info.activityInfo.packageName;
            Drawable appImage = info.activityInfo.loadIcon(getPackageManager());
            AppObject object = new AppObject(appPackagename,appname,appImage);

            if (!list.contains(object)){
                if (!arrayList.contains(appname)){
                    list.add(object);
                }
                else{
                    hiddenAppList.add(object);
                }
            }
        }

        return list;
    }

    public void generalMethod(){
        ArrayList<AppObject> arrayList = getInstalledAppList();

        GridLayoutManager manager = new GridLayoutManager(context,3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new AllAppsAdapter(context,arrayList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager1 = new GridLayoutManager(context,3);
        hiddenRecyclerview.setLayoutManager(manager1);
        hiddenRecyclerview.setHasFixedSize(true);
        adapter1 = new HiddenAppsAdapter(context,hiddenAppList);
        adapter1.notifyDataSetChanged();
        hiddenRecyclerview.setAdapter(adapter1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context,MainActivity.class);
        startActivity(intent);
    }
}