package com.comikaze.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ManList {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("manga_list")
    @Expose
    private ArrayList<Mans> mangaList = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Mans> getMangaList() {
        return mangaList;
    }

    public void setMangaList(ArrayList<Mans> mangaList) {
        this.mangaList = mangaList;
    }
}