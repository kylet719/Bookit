package com.example.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bookit.Data.Book;

import com.example.bookit.Data.DBHelper;
import com.example.bookit.Data.Data;

import java.util.ArrayList;

public class AddNew extends AppCompatActivity {
    private EditText title;
    private EditText author;
    private EditText url;
    private EditText curPage;
    private ImageView preview;

    Data myData = (Data) this.getApplication();
    ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        title = findViewById(R.id.edit_Title);
        author = findViewById(R.id.edit_Author);
        url = findViewById(R.id.edit_Image);
        url.setEnabled(false);
        curPage = findViewById(R.id.edit_curPage);
        books = myData.getOnGoingBooks();
        preview = findViewById(R.id.iV_coverPre);

        Intent i = getIntent();
        String[] ret = i.getStringArrayExtra("data");
        if (ret != null) {
            title.setText(ret[0]);
            author.setText(ret[1]);
            url.setText(ret[2]);
            Glide.with(this).load(ret[2]).into(preview);
        }
    }

    public void returnBut(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void addBook(View v) {
        String tit = title.getText().toString();
        String aut = author.getText().toString();
        String curP = curPage.getText().toString();
        int page = Integer.parseInt(curP);
        String image = url.getText().toString();
        if (image.length()==0) {
            image = "https://readersend.com/wp-content/uploads/2018/04/book-sample_preview-1.png";
        }
        Book b = new Book(tit, aut,page,250, image);

        DBHelper dbHelper = new DBHelper(AddNew.this);
        dbHelper.addOne(b);

        books.add(b);

        returnBut(v);
    }

    public void launchScan(View v) {
        Intent i = new Intent(this, ScanActivity.class);
        startActivity(i);
    }
}
