package com.github.matvapps.filmviewer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.matvapps.filmviewer.R;
import com.github.matvapps.filmviewer.fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, new DetailFragment()).commit();

    }
}
