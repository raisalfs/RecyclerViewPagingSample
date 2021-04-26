package com.rafslab.pagination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String profileURL = getIntent().getStringExtra("profile");
        String titleText = getIntent().getStringExtra("title");
        String descText = getIntent().getStringExtra("desc");

        ImageView profile = findViewById(R.id.profile);
        TextView title = findViewById(R.id.title);
        TextView desc = findViewById(R.id.desc);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back);
        toolbar.setNavigationOnClickListener(v-> onBackPressed());
        Glide.with(this).load(profileURL).apply(new RequestOptions().centerCrop()).into(profile);
        title.setText(titleText);
        desc.setText(descText);
    }
}