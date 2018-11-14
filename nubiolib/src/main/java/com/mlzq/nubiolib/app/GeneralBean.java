package com.mlzq.nubiolib.app;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dev on 2018/4/29.
 * desc :
 */

public class GeneralBean {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
    public GeneralBean() {
    }
    public GeneralBean(String name, int url) {
        this.name = name;
        this.url = url;
    }
    public GeneralBean(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "GeneralBean{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    private String name;

    @SerializedName("id")
    private int url;
    private String imgStr;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    private Uri uri;


    public String getImgStr() {
        return imgStr;
    }


    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }



}
