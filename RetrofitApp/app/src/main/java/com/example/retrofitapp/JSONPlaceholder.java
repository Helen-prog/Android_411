package com.example.retrofitapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceholder {

    @GET("posts")
    Call<List<Post>> getPost();

//    @GET("posts/{id}/comments")
//    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET("comments")
    Call<List<Comment>> getComments(@Query("postId") int postId);
}















