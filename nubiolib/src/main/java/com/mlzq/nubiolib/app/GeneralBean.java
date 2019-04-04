package com.mlzq.nubiolib.app;

/**
 * Created by Dev on 2018/4/29.
 * desc :
 */

public class GeneralBean {

    private int id;//标识
    private String name;//名称
    private int url;//图片地址R.drawable.icon

    public GeneralBean(int id, String name, int url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


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










}
