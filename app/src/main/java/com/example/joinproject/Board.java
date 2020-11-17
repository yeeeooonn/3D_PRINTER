package com.example.joinproject;

public class Board {
    private String title;
    private String content;
    private String imageUrl;
    private String writer;

    public Board(){}

    Board(String title,String content, String imageUrl, String writer){
        this.title=title;
        this.content=content;
        this.imageUrl=imageUrl;
        this.writer=writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }




}