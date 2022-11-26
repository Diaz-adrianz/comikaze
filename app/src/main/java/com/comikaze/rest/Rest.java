package com.comikaze.rest;

import com.comikaze.models.Chapters;
import com.comikaze.models.Details;
import com.comikaze.models.GenreList;
import com.comikaze.models.ManList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Rest {
    @GET("mangapi/api/{method}/{selection}")
    Call<ManList> getManList(@Path("method") String method, @Path("selection") String selection);

    @GET("mangapi/api/detail/{selection}")
    Call<Details> getDetails(@Path(value = "selection", encoded = true ) String selection);

    @GET("mangapi/api/genres")
    Call<GenreList> getGenres();

    @GET("mangapi/api/genres/{method}/{selection}")
    Call<ManList> getManListByGenre(@Path("method") String method, @Path("selection") String selection);

    @GET("mangapi/api/chapter/{selection}")
    Call<Chapters> getChapter(@Path(value = "selection", encoded = true ) String selection);


}
