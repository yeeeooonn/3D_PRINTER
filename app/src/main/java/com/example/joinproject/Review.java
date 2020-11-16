package com.example.joinproject;

public class Review {
    private String title;
    private String image;
    private String content;
    private String author;
    private String date;



    public Review(){}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() { return image; }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}
