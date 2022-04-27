package com.example.bookit.Data;

import static java.sql.Types.NULL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    public static final String BOOK_TABLE = "BOOK_TABLE";
    public static final String BOOK_NAME = "BOOK_NAME";
    public static final String BOOK_AUTHOR = "BOOK_AUTHOR";
    public static final String BOOK_COVER = "BOOK_COVER";

    public DBHelper(@Nullable Context context) {
        super(context, "books.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + BOOK_TABLE + " (" + BOOK_NAME + " TEXT, " + BOOK_AUTHOR + " TEXT, " + BOOK_COVER + " TEXT)";


        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(Book b) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BOOK_NAME, b.getTitle());
        cv.put(BOOK_AUTHOR, b.getAuthor());
        cv.put(BOOK_COVER, b.getImg());

        long success = db.insert(BOOK_TABLE, null,cv);
        if (success != -1 ) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Book> getBooks() {
        ArrayList<Book> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + BOOK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String bookTitle = cursor.getString(0);
                String bookAuthor = cursor.getString(1);
                String bookCover = cursor.getString(2);

                Book book = new Book(bookTitle,bookAuthor,1,250,bookCover);
                returnList.add(book);

            } while(cursor.moveToNext());

        } else {
            //FAIL

        }




        return returnList;
    }
}
