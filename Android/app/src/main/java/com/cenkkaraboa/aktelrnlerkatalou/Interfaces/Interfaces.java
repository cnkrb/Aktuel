package com.cenkkaraboa.aktelrnlerkatalou.Interfaces;


import com.cenkkaraboa.aktelrnlerkatalou.Models.Product;
import com.cenkkaraboa.aktelrnlerkatalou.Models.Stores;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Interfaces {



    @GET("api/getStore")
    Call<List<Stores>> getStores();

    @GET("api/allProduct")
    Call<List<Product>> allProduct();

    @GET("api/add")
    Call<List<Product>> add();


    @GET("api/getProduct/{id}")
    Call<List<Product>> getProduct(@Path("id") Integer id);

    @FormUrlEncoded
    @POST("api/searchStores")
    Call<List<Stores>> searchStores(@Field("name") String name);

}
