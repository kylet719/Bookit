package com.example.bookit.Data;

import android.app.Application;

import java.util.ArrayList;

//NOTE: OBSELETE
public class Data extends Application {
    private static ArrayList<Book> onGoingBooks = new ArrayList<>();
    private static ArrayList<Book> completedBooks = new ArrayList<>();

    public Data () {
//        initializeBooks();
    }

    public static ArrayList<Book> getOnGoingBooks() {
        return onGoingBooks;
    }
    public static ArrayList<Book> getCompletedBooks() { return completedBooks;}

    public static void setOnGoingBooks(ArrayList<Book> onGoingBooks) {
        Data.onGoingBooks = onGoingBooks;
    }

    private void initializeBooks() {
        Book b1 = new Book ("Dune", "Frank Herbert" , 1, 256, "https://images-na.ssl-images-amazon.com/images/I/81ym3QUd3KL.jpg");
        Book b2 = new Book ("Neuromancer", "William Gibson" , 1, 234, "https://images-na.ssl-images-amazon.com/images/I/51OATljr5VL._SX331_BO1,204,203,200_.jpg");
        Book b3 = new Book ("Dune", "Frank Herbert" , 1, 52,"https://images-na.ssl-images-amazon.com/images/I/81ym3QUd3KL.jpg");
        Book b4 = new Book ("Fahrenheit 495", "Ray Bradburn", 250, 500, "https://images-na.ssl-images-amazon.com/images/I/71OFqSRFDgL.jpg" );

        Book b5 = new Book ("Dune Messiah", "Frank Herbert", 250, 300, "https://images.penguinrandomhouse.com/cover/9780593201732");
        Book b6 = new Book ("Dune", "Frank Herbert" , 1, 25,"https://images-na.ssl-images-amazon.com/images/I/81ym3QUd3KL.jpg");
        Book b7 = new Book ("Neuromancer", "William Gibson" , 1, 25, "https://images-na.ssl-images-amazon.com/images/I/51OATljr5VL._SX331_BO1,204,203,200_.jpg");
        Book b8 = new Book ("Dune", "Frank Herbert" , 1, 123, "https://images-na.ssl-images-amazon.com/images/I/81ym3QUd3KL.jpg");
        Book b9 = new Book ("Fahrenheit 495", "Ray Bradburn", 250, 1000,"https://images-na.ssl-images-amazon.com/images/I/71OFqSRFDgL.jpg" );
        Book b10 = new Book ("Dune Messiah", "Frank Herbert", 250 , 500, "https://images.penguinrandomhouse.com/cover/9780593201732");

        onGoingBooks.add(b1);
        onGoingBooks.add(b2);
        onGoingBooks.add(b3);
        onGoingBooks.add(b4);

        completedBooks.add(b5);
//        books.add(b5);
//        books.add(b6);
//        books.add(b7);
//        books.add(b8);
//        books.add(b9);
//        books.add(b10);
    }
}
