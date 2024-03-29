package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class BookModel {

    @SerializedName("id")
    private String id;

    @SerializedName("category")
    private String category;

    @SerializedName("name")
    private String name;

    @SerializedName("author")
    private String author;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    @SerializedName("image_2")
    private String image2;

    @SerializedName("image_3")
    private String image3;

    @SerializedName("image_4")
    private String image4;

    @SerializedName("published_year")
    private String published_year;

    @SerializedName("price")
    private String price;

    public BookModel() {
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getPublished_year() {
        return published_year;
    }

    public String getPrice() {
        return price;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPublished_year(String published_year) {
        this.published_year = published_year;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }
}
