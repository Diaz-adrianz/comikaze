package com.comikaze.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chapters {

    @SerializedName("chapter_endpoint")
    @Expose
    private String chapterEndpoint;
    @SerializedName("chapter_prev_endpoint")
    @Expose
    private String chapterPrevEndpoint;
    @SerializedName("chapter_next_endpoint")
    @Expose
    private String chapterNextEndpoint;
    @SerializedName("chapter_name")
    @Expose
    private String chapterName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("chapter_pages")
    @Expose
    private Integer chapterPages;
    @SerializedName("chapter_image")
    @Expose
    private List<String> chapterImage = null;

    public String getChapterEndpoint() {
        return chapterEndpoint;
    }

    public void setChapterEndpoint(String chapterEndpoint) {
        this.chapterEndpoint = chapterEndpoint;
    }

    public String getChapterPrevEndpoint() {
        return chapterPrevEndpoint;
    }

    public void setChapterPrevEndpoint(String chapterPrevEndpoint) {
        this.chapterPrevEndpoint = chapterPrevEndpoint;
    }

    public String getChapterNextEndpoint() {
        return chapterNextEndpoint;
    }

    public void setChapterNextEndpoint(String chapterNextEndpoint) {
        this.chapterNextEndpoint = chapterNextEndpoint;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getChapterPages() {
        return chapterPages;
    }

    public void setChapterPages(Integer chapterPages) {
        this.chapterPages = chapterPages;
    }

    public List<String> getChapterImage() {
        return chapterImage;
    }

    public void setChapterImage(List<String> chapterImage) {
        this.chapterImage = chapterImage;
    }

}