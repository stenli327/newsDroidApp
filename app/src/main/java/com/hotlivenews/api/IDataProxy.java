package com.hotlivenews.api;

import com.hotlivenews.api.model.ArticleResponse;
import com.hotlivenews.api.model.SourceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/***
 *  Endpoints
 *  Using News API is simple. There are only 2 endpoints:
 *  GET https://newsapi.org/v1/articles
 *  GET https://newsapi.org/v1/sources
 *
 *  https://newsapi.org/
 */
public interface IDataProxy {

    @GET("sources")
    Call<SourceResponse> getSources(
            @Query("category")String category,
            @Query("language")String language,
            @Query("country")String country);

    @GET("articles")
    Call<ArticleResponse> getArticles(
            @Query("source")String source,
            @Query("sortBy")String sortBy);
}
