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
    public static final String BOOK_CURRENT = "BOOK_CURRENT";

    public static final int VERSION = 2;

    public DBHelper(@Nullable Context context) {
        super(context, "books.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + BOOK_TABLE + " (" + BOOK_NAME + " TEXT, " + BOOK_AUTHOR + " TEXT, " + BOOK_COVER + " TEXT, " + BOOK_CURRENT + " INTEGER)";
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i < 1) {
            String createTable = "CREATE TABLE " + BOOK_TABLE + " (" + BOOK_NAME + " TEXT, " + BOOK_AUTHOR + " TEXT, " + BOOK_COVER + " TEXT, " + BOOK_CURRENT + " INTEGER)";
            sqLiteDatabase.execSQL(createTable);
        }

        if (i < 2) {
            String update = "ALTER TABLE " + BOOK_TABLE + "  ADD COLUMN  " + BOOK_CURRENT + " INTEGER";
            sqLiteDatabase.execSQL(update);
        }

    }

    public boolean addOne(Book b) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BOOK_NAME, b.getTitle());
        cv.put(BOOK_AUTHOR, b.getAuthor());
        cv.put(BOOK_COVER, b.getImg());
        cv.put(BOOK_CURRENT, b.getPage());

        long success = db.insert(BOOK_TABLE, null, cv);
        if (success != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean removeOne(Book b) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean rett = db.delete(BOOK_TABLE, BOOK_NAME + "=?", new String[]{b.getTitle()}) > 0;
        db.close();
        return rett;
    }

    public boolean updateOne(String title, int up, String auth, String img, int old) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BOOK_NAME, title);
        cv.put(BOOK_AUTHOR, auth);
        cv.put(BOOK_COVER, img);
        cv.put(BOOK_CURRENT, up);

        db.update(BOOK_TABLE, cv, BOOK_NAME+ "=?", new String[] {title});
        db.close();
        return true;


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
                int page = cursor.getInt(3);

                Book book = new Book(bookTitle, bookAuthor, page, 250, bookCover);
                returnList.add(book);

            } while (cursor.moveToNext());

        } else {
            //FAIL

        }
        cursor.close();
        db.close();
        return returnList;
    }
}
