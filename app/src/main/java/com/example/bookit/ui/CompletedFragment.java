package com.example.bookit.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookit.AddNew;
import com.example.bookit.Data.Book;
import com.example.bookit.Data.DBHelper;
import com.example.bookit.R;
import com.example.bookit.RecycleView.bookAdapter;
import com.example.bookit.RecycleView.bookCompletedAdapter;
import com.example.bookit.databinding.FragmentDashboardBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CompletedFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private View root;

    private ArrayList<Book> completedBooks;
    private DBHelper database;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = root.findViewById(R.id.rv_Completed);
        database = new DBHelper(this.getContext());
        recyclerView.setHasFixedSize(true);recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(root.getContext(), R.dimen.cardview_default_radius);
        recyclerView.addItemDecoration(itemDecoration);
        refresh();
        return root;
    }

    public void refresh() {
        mAdapter = new bookCompletedAdapter(this.getContext());
        recyclerView.setAdapter(mAdapter);
        completedBooks = database.getCompletedBooks();
        new ItemTouchHelper(swipeBackToReading).attachToRecyclerView(recyclerView);

    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        requireActivity().getMenuInflater().inflate(R.menu.addcompletedbook, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.completed_Add) {
            Intent i = new Intent(this.getActivity(), AddNew.class);
            startActivity(i);
            return true;
        } else if (id == R.id.completed_sort) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }

    ItemTouchHelper.SimpleCallback swipeBackToReading = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Book b = completedBooks.get(viewHolder.getAdapterPosition());
            b.setCompleted(false);
            b.setCurrPage(1);
            database.updateOne(b);
            refresh();
        }
    };
}