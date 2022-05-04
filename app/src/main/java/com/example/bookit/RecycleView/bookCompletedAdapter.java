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

import java.util.ArrayList;

public class bookCompletedAdapter extends RecyclerView.Adapter<bookCompletedAdapter.myViewHolder> {
    ArrayList<Book> books;
    DBHelper d;
    Context context;


    public bookCompletedAdapter(Context context) {
        this.d = new DBHelper(context);
        this.books = d.getCompletedBooks();
        this.books = d.getCompletedBooks();
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneview_completed,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.author.setText("By: " + books.get(position).getAuthor());
        holder.title.setText(books.get(position).getTitle());
        holder.dateComp.setText("p"+ String.valueOf(books.get(position).getPage()));
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
        TextView dateComp;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.iv_CoverCompleted);
            author = itemView.findViewById((R.id.tv_AuthorComplete));
            title = itemView.findViewById(R.id.tv_TitleCompleted);
            dateComp = itemView.findViewById(R.id.tv_DateCompleted);
        }
    }
}
