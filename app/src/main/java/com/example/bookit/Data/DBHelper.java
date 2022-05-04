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
    //SQLite Columns
    public static final String BOOK_TABLE = "BOOK_TABLE";
    public static final String BOOK_NAME = "BOOK_NAME";
    public static final String BOOK_AUTHOR = "BOOK_AUTHOR";
    public static final String BOOK_COVER = "BOOK_COVER";
    public static final String BOOK_CURRENT = "BOOK_CURRENT";


    public static final String BOOK_COMPLETED = "BOOK_COMPLETED";
    public static final String BOOK_LAST_READ = "BOOK_LAST_READ";

    //Current version
    public static final int VERSION = 3;

    public DBHelper(@Nullable Context context) {
        super(context, "books.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + BOOK_TABLE + " (" + BOOK_NAME + " TEXT, "
                + BOOK_AUTHOR + " TEXT, "
                + BOOK_COVER + " TEXT, "
                + BOOK_CURRENT + " INTEGER, "
                + BOOK_COMPLETED + " INTEGER, "
                + BOOK_LAST_READ + " TEXT)";
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
            String update2 = "ALTER TABLE " + BOOK_TABLE + "  ADD COLUMN  " + BOOK_COMPLETED + " INTEGER";
            sqLiteDatabase.execSQL(update2);
            String update3 = "ALTER TABLE " + BOOK_TABLE + "  ADD COLUMN  " + BOOK_LAST_READ + " TEXT";
            sqLiteDatabase.execSQL(update3);
        }

        if (i < 3) {
            String update2 = "ALTER TABLE " + BOOK_TABLE + "  ADD COLUMN  " + BOOK_COMPLETED + " INTEGER";
            sqLiteDatabase.execSQL(update2);
            String update3 = "ALTER TABLE " + BOOK_TABLE + "  ADD COLUMN  " + BOOK_LAST_READ + " TEXT";
            sqLiteDatabase.execSQL(update3);
        }
    }

    /**
     * Adds a book to the SQLite database. Requires name, author, and cover
     * @param b - Book instance to add into SQLite database
     * @return - Returns true if add was successful, false otherwise.
     */
    public boolean addOne(Book b) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BOOK_NAME, b.getTitle());
        cv.put(BOOK_AUTHOR, b.getAuthor());
        cv.put(BOOK_COVER, b.getImg());
        cv.put(BOOK_CURRENT, b.getPage());
        int completed;
        if(b.getCompleted()) {
            completed = 1;
        } else {
            completed = 0;
        }
        cv.put(BOOK_COMPLETED,completed);
        cv.put(BOOK_LAST_READ, b.getDate());

        long success = db.insert(BOOK_TABLE, null, cv);
        if (success != -1) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Deletes an existing book with the same name from the database. Does nothing if book does not
     * exist
     * @param b - Book instance to be removed from the SQLite database
     * @return - Returns true if delete was successful. False if not or if book is not in database
     */
    public boolean removeOne(Book b) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean rett = db.delete(BOOK_TABLE, BOOK_NAME + "=?", new String[]{b.getTitle()}) > 0;
        db.close();
        return rett;
    }

    public boolean updateOne(Book b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BOOK_NAME, b.getTitle());
        cv.put(BOOK_AUTHOR, b.getAuthor());
        cv.put(BOOK_COVER, b.getImg());
        cv.put(BOOK_CURRENT, b.getPage());
        if (b.getCompleted()) {
            cv.put(BOOK_COMPLETED,1);
        } else {
            cv.put(BOOK_COMPLETED,0);
        }
        cv.put(BOOK_LAST_READ,b.getDate());

        db.update(BOOK_TABLE, cv, BOOK_NAME+ "=?", new String[] {b.getTitle()});
        db.close();
        return true;


    }

    public ArrayList<Book> getOnGoingBooks() {
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
                int completed = cursor.getInt(4);
                if (completed==0) {
                    Book book = new Book(bookTitle, bookAuthor, page, 250, bookCover);
                    book.setCompleted(false);
                    returnList.add(book);
                }
            } while (cursor.moveToNext());

        } else {
            //FAIL

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<Book> getCompletedBooks() {
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
                int completed = cursor.getInt(4);
                if (completed==1) {
                    Book book = new Book(bookTitle, bookAuthor, page, 250, bookCover);
                    book.setCompleted(true);
                    returnList.add(book);
                }
            } while (cursor.moveToNext());

        } else {
            //FAIL

        }
        cursor.close();
        db.close();
        return returnList;
    }
}
