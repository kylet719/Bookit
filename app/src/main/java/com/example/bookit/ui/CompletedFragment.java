package com.example.bookit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookit.AddNew;
import com.example.bookit.R;
import com.example.bookit.RecycleView.bookCompletedAdapter;
import com.example.bookit.databinding.FragmentDashboardBinding;

import org.jetbrains.annotations.NotNull;

public class CompletedFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = root.findViewById(R.id.rv_Completed);
        recyclerView.setHasFixedSize(true);recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        mAdapter = new bookCompletedAdapter(this.getContext());
        recyclerView.setAdapter(mAdapter);
        return root;
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
}