package com.example.bookit.Data;

import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private int currPage;
    private int totalPage;
    private String img;
    private LocalDate dayStarted;
    private boolean completed;

    public Book(String title, String author, int currPage, int totalPage, String img) {
        this.title = title;
        this.author = author;
        this.currPage = currPage;
        this.img = img;
        this.completed = false;
        this.totalPage = totalPage;
    }

    public void setCurrPage(int i) throws Exception {
        if (i > totalPage) throw new Exception();
        this.currPage = i;
        completed = currPage == totalPage;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPage() {
        return currPage;
    }

    public String getImg() {
        return img;
    }

    public boolean getCompleted() {
        return completed;
    }

    public int getPercentage() {
        return (int)((currPage * 100.0f) / totalPage);
    }
}
