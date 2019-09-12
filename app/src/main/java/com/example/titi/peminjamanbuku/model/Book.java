package com.example.titi.peminjamanbuku.model;

import com.google.gson.annotations.SerializedName;

public class Book {

    private String id;
    @SerializedName("nama_buku")
    private String name;
    @SerializedName("jenis_buku")
    private String type;
    @SerializedName("tahun_buku")
    private String year;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }
}
