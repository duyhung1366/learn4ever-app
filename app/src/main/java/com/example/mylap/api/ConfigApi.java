package com.example.mylap.api;

import com.example.mylap.services.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigApi {
    private ApiService apiService;

    public ConfigApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://learn4ever.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
