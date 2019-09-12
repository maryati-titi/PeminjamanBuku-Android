package com.example.titi.peminjamanbuku.service;

import com.example.titi.peminjamanbuku.model.BorrowResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Webservice tcc
 */
public interface ApiInterface2 {

    @FormUrlEncoded
    @POST("pinjam.php")
    Call<BorrowResponse> borrowBook( @Field("id_buku") String bookId,
                                    @Field("nik") String nik,
                                    @Field("nama") String nama,
                                    @Field("tanggal") String tanggal
                                   );
}
