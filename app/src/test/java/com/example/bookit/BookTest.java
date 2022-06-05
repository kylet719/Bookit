package com.example.bookit;

import com.example.bookit.Data.Book;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {
    private Book testBook;

    @Before
    public void setTestBook() {
        testBook = new Book("Title", "Author", 0, 250, "");
    }

    @Test
    public void testSetters() {
        testBook.setBlurb("testBlurb");
        assertEquals(testBook.getBlurb().length(), 9);
        testBook.setCurrPage(25);
        assertEquals(testBook.getPage(),25);
        assertEquals(testBook.getPercentage(),10);
        assertEquals(testBook.getTitle(),"Title");
        assertEquals(testBook.getAuthor(), "Author");
    }
}
