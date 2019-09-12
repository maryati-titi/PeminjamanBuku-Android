package com.example.titi.peminjamanbuku.model;

public class BorrowResponse {

    private boolean error;
    private String message;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}