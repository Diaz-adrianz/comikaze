package com.comikaze.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details_chapters {

    @SerializedName("chapter_title")
    @Expose
    private String chapterTitle;
    @SerializedName("chapter_endpoint")
    @Expose
    private String chapterEndpoint;

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getChapterEndpoint() {
        return chapterEndpoint;
    }

    public void setChapterEndpoint(String chapterEndpoint) {
        this.chapterEndpoint = chapterEndpoint;
    }

}