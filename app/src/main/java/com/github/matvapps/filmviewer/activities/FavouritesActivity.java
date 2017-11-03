package com.github.matvapps.filmviewer.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.github.matvapps.filmviewer.R;
import com.github.matvapps.filmviewer.adapters.MovieGridAdapter;
import com.github.matvapps.filmviewer.entity.Movie;
import com.github.matvapps.filmviewer.fragments.DetailFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.films_container)
    GridView moviesGridView;
    @BindView(R.id.arrow_back)
    ImageView arrowBack;

    private MovieGridAdapter movieGridAdapter;
    private ArrayList<Movie> movies;

    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTwoPane = findViewById(R.id.detail_container) != null;

        movies = (ArrayList<Movie>) Movie.listAll(Movie.class);

        movieGridAdapter = new MovieGridAdapter(FavouritesActivity.this, movies);
        moviesGridView.setAdapter(movieGridAdapter);

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mTwoPane) {

                    DetailFragment fragment = new DetailFragment();

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail_container, fragment)
                            .commit();

                    getIntent().putExtra("MovieObj", (Movie) parent.getItemAtPosition(position));


                } else {
                    Intent intent = new Intent(FavouritesActivity.this, DetailActivity.class);
                    intent.putExtra("MovieObj", (Movie) parent.getItemAtPosition(position));
                    startActivity(intent);
                }


            }
        });

        if (mTwoPane) {

            DetailFragment fragment = new DetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //code for portrait mode
            moviesGridView.setNumColumns(2);
        } else {
            //code for landscape mode
            moviesGridView.setNumColumns(3);
        }
    }


}
