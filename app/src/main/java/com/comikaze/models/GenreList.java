package com.comikaze.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenreList {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("list_genre")
    @Expose
    private List<Genres> listGenre = null;

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

    public List<Genres> getListGenre() {
        return listGenre;
    }

    public void setListGenre(List<Genres> listGenre) {
        this.listGenre = listGenre;
    }

}