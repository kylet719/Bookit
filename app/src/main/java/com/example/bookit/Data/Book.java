package com.example.bookit.Data;

import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private int currPage;
    private int totalPage;
    private String img;
    private String blurb;
    private String date;
    private boolean completed;
    //    private LocalDate dayStarted;

    /**
     * Default constructor for a book object
     * @param title - Book title
     * @param author - Author of book
     * @param currPage - Current page user is on
     * @param totalPage - Total pages in book
     * @param img - URL of a PNG or JPG of book cover
     */
    public Book(String title, String author, int currPage, int totalPage, String img) {
        this.title = title;
        this.author = author;
        this.currPage = currPage;
        this.img = img;
        this.completed = false;
        this.totalPage = totalPage;
        this.date = "";
    }

    public void setBlurb(String s) {
        this.blurb = s;
    }

    public void setCurrPage(int i)  {
        this.currPage = i;
    }

    public void setCompleted(boolean v) {
        this.completed = v;
    }


    //getters
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
    public String getBlurb() {return blurb;}
    public boolean getCompleted() {
        return completed;
    }
    public int getPercentage() {
        return (int)((currPage * 100.0f) / totalPage);
    }
    public String getDate() {
        return date;
    }
}
