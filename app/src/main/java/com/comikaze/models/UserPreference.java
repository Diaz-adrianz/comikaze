package com.comikaze.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserPreference {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("endpoint")
    @Expose
    private String endpoint;

    public UserPreference() {
    }

    public UserPreference(String title, String subtitle, String endpoint) {
        super();
        this.title = title;
        this.subtitle = subtitle;
        this.endpoint = endpoint;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getEndpoint() {
        return endpoint;
    }

}