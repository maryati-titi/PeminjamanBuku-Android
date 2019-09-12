package com.example.titi.peminjamanbuku.service;

import com.example.titi.peminjamanbuku.model.BookResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Webservice 000webhost
 */
public interface ApiInterface1 {

    @GET("buku.php")
    Call<BookResponse> getBooks();

    @GET("buku.php")
    Call<BookResponse> getBook(@Query("id") String id);

    @GET("buku.php")
    Call<BookResponse> getBookByName(@Query("nama") String nama);
}
