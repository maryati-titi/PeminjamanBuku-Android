package com.example.titi.peminjamanbuku.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static String BASE_URL = "http://tccsukses.000webhostapp.com/tcc/";      // Webservicemu
    // TODO: 12/7/2018 GANTI KE IP POSTGRESQL
    private static String BASE_URL_2 = "http://192.168.43.147/tcc/";                   // IPnya Po

    private static Retrofit retrofit = null;

    public static Retrofit getClient1() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getClient2() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
