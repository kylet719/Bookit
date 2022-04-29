package com.example.bookit.RecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookit.Data.Book;
import com.example.bookit.Data.DBHelper;
import com.example.bookit.Data.Data;
import com.example.bookit.R;
import com.example.bookit.ui.ReadingFragment;

import java.util.ArrayList;

public class bookAdapter extends RecyclerView.Adapter<bookAdapter.myViewHolder> {
    ArrayList<Book> books;
    DBHelper d;
    Context context;


    public bookAdapter(Context context) {
//        Data d = new Data();
        this.d = new DBHelper(context);
        this.books = d.getBooks();
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneview,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String testAuthor = books.get(position).getAuthor();
        if (testAuthor.length() >= 30) {
            testAuthor = testAuthor.substring(0,25);
            testAuthor = testAuthor + "...";
        }

        holder.author.setText("By: " + testAuthor);
        String testTitle = books.get(position).getTitle();
        if (testTitle.length() >= 30) {
            testTitle = testTitle.substring(0,25);
            testTitle = testTitle + "...";
        }
        holder.title.setText(testTitle);
        holder.currentPage.setText("p"+ String.valueOf(books.get(position).getPage()));
        Glide.with(context).load(books.get(position).getImg()).into(holder.cover);

    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView author;
        TextView title;
        TextView currentPage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.iV_cover);
            author = itemView.findViewById((R.id.tv_Author));
            title = itemView.findViewById(R.id.tv_Book);
            currentPage = itemView.findViewById(R.id.tv_PageTeller);
        }
    }
}
