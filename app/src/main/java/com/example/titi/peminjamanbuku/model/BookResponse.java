package com.example.titi.peminjamanbuku.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookResponse {

    @SerializedName("buku")
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }
}
