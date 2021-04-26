package com.rafslab.pagination;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rafslab.pagination.adapter.RecyclerAdapter;
import com.rafslab.pagination.model.Data;

import java.util.ArrayList;
import java.util.List;

import static com.rafslab.pagination.PaginationListener.PAGE_START;

public class MainActivity extends AppCompatActivity {

    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.list);
        adapter = new RecyclerAdapter(this, new ArrayList<>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        getData();
        recyclerView.setHasFixedSize(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
                @Override
                protected void loadMoreItems() {
                    isLoading = true;
                    currentPage++;
                    getData();
                }

                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }
            });
        }
    }
    private void getData(){
        final List<Data> dataList = new ArrayList<>();
        new Handler(getMainLooper()).postDelayed(() -> {
            for (int i = 0; i < 10; i++) {
                itemCount++;
                Data data = new Data();
                data.setTitle("Item " + i);
                data.setDesc("Description " + i);
                data.setProfile("https://mmc.tirto.id/image/2016/06/24/TomandJerry_ratio-16x9.jpg");
                dataList.add(data);
            }
            if (currentPage != PAGE_START) adapter.removeLoading();
            adapter.addItems(dataList);
            if (currentPage < totalPage) {
                adapter.addLoading();
            } else {
                isLastPage = true;
            }
            isLoading = false;
        }, 300);
    }
}