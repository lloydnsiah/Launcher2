package com.example.launcher2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;

public class AllApps extends AppCompatActivity {
    private RecyclerView recyclerView,hiddenRecyclerview;
    private ArrayList<AppObject> arrayList = new ArrayList<>();
    private ArrayList<AppObject> hiddenAppList = new ArrayList<>();
    private AllAppsAdapter adapter,adapter1;
    private AppAdapterLinear adaterlinear;
    private Context context;
    private ArrayList<String> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps);

        context = getApplicationContext();
        recyclerView = findViewById(R.id.allAppsRecyclerView);
        hiddenRecyclerview = findViewById(R.id.hiddenAppsRecyclerview);

        arr.add("Launcher2");
        arr.add("Play Store");

        arrayList = getInstalledAppList();

        GridLayoutManager manager = new GridLayoutManager(context,3);
        recyclerView.setLayoutManager(manager);
        adapter = new AllAppsAdapter(context,arrayList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager1 = new GridLayoutManager(context,3);
        hiddenRecyclerview.setLayoutManager(manager1);
        adapter1 = new AllAppsAdapter(context,hiddenAppList);
        adapter1.notifyDataSetChanged();
        hiddenRecyclerview.setAdapter(adapter1);





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
                if (!appname.equals("Launcher2")){
                    list.add(object);
                }else{
                    hiddenAppList.add(object);
                }
            }
        }

        return list;
    }
}