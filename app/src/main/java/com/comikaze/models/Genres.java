package com.comikaze.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genres{

    @SerializedName("genre_name")
    @Expose
    private String genreName;
    @SerializedName("endpoint")
    @Expose
    private String endpoint;

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}