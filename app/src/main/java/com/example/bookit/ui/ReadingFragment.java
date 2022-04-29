package com.example.bookit.ui;

import static androidx.fragment.app.FragmentManager.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookit.AddNew;
import com.example.bookit.Data.Book;
import com.example.bookit.Data.DBHelper;
import com.example.bookit.R;
import com.example.bookit.RecycleView.bookAdapter;
import com.example.bookit.RecycleView.bookCompletedAdapter;
import com.example.bookit.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReadingFragment extends Fragment implements bookAdapter.NoteListener {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private View root;

    //Edit book popup
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView titleName;
    private EditText pageEdit;
    private EditText descEdit;
    private Button update;
    private Button cancel;

    private DBHelper database;
    private ArrayList<Book> bookList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public void refresh() {
        mAdapter = new bookAdapter(this.getContext(), this);
        recyclerView.setAdapter(mAdapter);
        bookList = database.getBooks();

        new ItemTouchHelper(itemTouchHelp).attachToRecyclerView(recyclerView);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        database = new DBHelper(this.getContext());
        bookList = database.getBooks();

        recyclerView = root.findViewById(R.id.rv_Books);
        recyclerView.setHasFixedSize(true);recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        refresh();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        requireActivity().getMenuInflater().inflate(R.menu.addbook, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.onGoing_Add) {
            Intent i = new Intent(this.getActivity(), AddNew.class);
            startActivity(i);
            return true;
        } else if (id == R.id.onGoing_sort) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void createContactDialog(String title, int currentPage, String author, String image) {
        dialogBuilder = new AlertDialog.Builder(this.getContext());
        final View contactPopup = getLayoutInflater().inflate(R.layout.current_book_popup, null);

        titleName = contactPopup.findViewById(R.id.popTitle);
        titleName.setText(title);
        pageEdit = contactPopup.findViewById(R.id.popupEditPage);
        pageEdit.setText(String.valueOf(currentPage));

        descEdit = contactPopup.findViewById(R.id.popupEditDescrip);

        cancel = contactPopup.findViewById(R.id.popupCancelBut);
        update = contactPopup.findViewById(R.id.popSaveBut);

        dialogBuilder.setView(contactPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newPage = Integer.parseInt(pageEdit.getText().toString());
                database.updateOne(title, newPage, author, image, currentPage);
                refresh();
                dialog.dismiss();

            }
        });


    }

    ItemTouchHelper.SimpleCallback itemTouchHelp = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Book b = bookList.get(viewHolder.getAdapterPosition());
            database.removeOne(b);
            refresh();
        }
    };

    @Override
    public void onClick(int position) {
        Book b = bookList.get(position);
        createContactDialog(b.getTitle(), b.getPage(), b.getAuthor(), b.getImg());
    }
}