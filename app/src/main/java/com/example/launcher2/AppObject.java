package com.example.launcher2;

import android.graphics.drawable.Drawable;

public class AppObject {
    private String name,packageName;
    private Drawable image;

    public AppObject(String packageName,String name,  Drawable image) {
        this.packageName = packageName;
        this.name = name;
        this.image = image;
    }

    public AppObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}

