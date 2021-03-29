package com.cenkkaraboa.aktelrnlerkatalou.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stores {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("storeName")
    @Expose
    private String storeName;
    @SerializedName("picturePatch")
    @Expose
    private String picturePatch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPicturePatch() {
        return picturePatch;
    }

    public void setPicturePatch(String picturePatch) {
        this.picturePatch = picturePatch;
    }

}

