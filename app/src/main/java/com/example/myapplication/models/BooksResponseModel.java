package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BooksResponseModel {

   @SerializedName("books")
    private List<BookModel> books;

    public List<BookModel> getBooks() {
        return books;
    }
}
