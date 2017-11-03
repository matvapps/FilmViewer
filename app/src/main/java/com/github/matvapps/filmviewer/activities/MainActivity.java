package com.github.matvapps.filmviewer.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.github.matvapps.filmviewer.EndlessScrollListener;
import com.github.matvapps.filmviewer.R;
import com.github.matvapps.filmviewer.Utility;
import com.github.matvapps.filmviewer.adapters.MovieGridAdapter;
import com.github.matvapps.filmviewer.contracts.MovieUrlContract;
import com.github.matvapps.filmviewer.entity.Genre;
import com.github.matvapps.filmviewer.entity.Movie;
import com.github.matvapps.filmviewer.fragments.DetailFragment;
import com.github.matvapps.filmviewer.loaders.GenresLoader;
import com.github.matvapps.filmviewer.loaders.MovieListLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements TabLayout.OnTabSelectedListener,
        NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<String> {

    private final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.films_container) GridView moviesGridView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tab_layout) TabLayout tabLayout;

    private MovieGridAdapter movieGridAdapter;

    private ArrayList<Movie> movies;

    private final int MOVIES_LOADER_ID = 1;
    private final int GENRES_LOADER_ID = 3;

    private int page = 1;
    private String movieCategory;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTwoPane = findViewById(R.id.detail_container) != null;
        tabLayout.addOnTabSelectedListener(this);

        setSupportActionBar(toolbar);

        moviesGridView.setNumColumns(2);

        movies = new ArrayList<>();
        movieGridAdapter = new MovieGridAdapter(MainActivity.this, movies);
        moviesGridView.setAdapter(movieGridAdapter);

        movieCategory = MovieUrlContract.CATEGORY_POPULAR_FIELD;

        moviesGridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                MainActivity.this.page++;
                getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, null, MainActivity.this).forceLoad();

                return true;
            }
        });
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
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("MovieObj", (Movie) parent.getItemAtPosition(position));
                    startActivity(intent);
                }


            }
        });


        getSupportLoaderManager().initLoader(MOVIES_LOADER_ID, null, this).forceLoad();
        getSupportLoaderManager().initLoader(GENRES_LOADER_ID, null, this).forceLoad();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (mTwoPane) {

            DetailFragment fragment = new DetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_favourites: {
                Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(intent);

                break;
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> mLoader = null;

        switch (id) {
            case MOVIES_LOADER_ID: {
                mLoader = new MovieListLoader(MainActivity.this, movieCategory, "ru-RU", page);
                break;
            }
            case GENRES_LOADER_ID: {
                mLoader = new GenresLoader(this, "ru-RU");
                break;
            }
        }

        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        if (data != null) {
            switch (loader.getId()) {
                case MOVIES_LOADER_ID: {

                    movies.addAll(Utility.parseMoviesJSON(data));
                    movieGridAdapter.notifyDataSetChanged();
                    break;
                }
                case GENRES_LOADER_ID: {
                    List<Genre> genres = Utility.parseGenresJSON(data);

                    for (Genre genre: genres) {
                        genre.setId((long) genre.getGenreId());
                        genre.save();
                    }

                    break;
                }
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        page = 1;

        switch (tab.getPosition()) {
            case 0: {
                movieCategory = MovieUrlContract.CATEGORY_POPULAR_FIELD;
                break;
            }
            case 1: {
                movieCategory = MovieUrlContract.CATEGORY_NOW_PLAYING_FIELD;
                break;
            }
            case 2: {
                movieCategory = MovieUrlContract.CATEGORY_UPCOMING_FIELD;
                break;
            }
        }

        movies.clear();
        moviesGridView.setAdapter(movieGridAdapter);
        getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, null, MainActivity.this).forceLoad();

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
