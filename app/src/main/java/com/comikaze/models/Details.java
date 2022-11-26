package com.comikaze.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("manga_endpoint")
    @Expose
    private String mangaEndpoint;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("genre_list")
    @Expose
    private List<String> genreList = null;
    @SerializedName("synopsis")
    @Expose
    private String synopsis;
    @SerializedName("chapter")
    @Expose
    private ArrayList<Details_chapters> chapter = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMangaEndpoint() {
        return mangaEndpoint;
    }

    public void setMangaEndpoint(String mangaEndpoint) {
        this.mangaEndpoint = mangaEndpoint;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<String> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<String> genreList) {
        this.genreList = genreList;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public ArrayList<Details_chapters> getChapter() {
        return chapter;
    }

    public void setChapter(ArrayList<Details_chapters> chapter) {
        this.chapter = chapter;
    }

}