package com.github.matvapps.filmviewer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.matvapps.filmviewer.R;
import com.github.matvapps.filmviewer.Utility;
import com.github.matvapps.filmviewer.adapters.CastsListAdapter;
import com.github.matvapps.filmviewer.entity.Cast;
import com.github.matvapps.filmviewer.entity.Genre;
import com.github.matvapps.filmviewer.entity.Movie;
import com.github.matvapps.filmviewer.loaders.CastLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<String> {

    private final String TAG = DetailFragment.class.getSimpleName();

    private final int CASTS_LOADER_ID = 121;
    private Movie movie;

    @BindView(R.id.movie_poster)
    ImageView posterImage;
    @BindView(R.id.movie_poster_land)
    ImageView backdropImage;
    @BindView(R.id.btn_movie_favourite)
    ImageView favouriteImage;
    @BindView(R.id.movie_title)
    TextView titleText;
    @BindView(R.id.movie_votes_text)
    TextView voteText;
    @BindView(R.id.movie_mark_text)
    TextView markText;
    @BindView(R.id.movie_release_text)
    TextView dateText;
    @BindView(R.id.movie_categories)
    TextView categoriesText;
    @BindView(R.id.movie_overview_text)
    TextView overviewText;

    @BindView(R.id.movie_casts_container) RecyclerView castsView;
    @Nullable @BindView(R.id.arrow_back) ImageView arrowBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_detail, null);

        ButterKnife.bind(this, rootView);

        castsView.setFocusable(false);

        posterImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        backdropImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        movie = (Movie) getActivity().getIntent().getSerializableExtra("MovieObj");

        if (movie != null) {
            setMovieInformation(movie);

            if (!Utility.isMovieInFavourites(movie.getMovie_id())) {
                favouriteImage.setImageResource(R.drawable.ic_favorite);

            } else {
                favouriteImage.setImageResource(R.drawable.ic_favorite_red);

            }

            favouriteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utility.isMovieInFavourites(movie.getMovie_id())) {
                        ((ImageView) v).setImageResource(R.drawable.ic_favorite);

                        movie.setId((long) movie.getMovie_id());
                        movie.delete();

                    } else {
                        ((ImageView) v).setImageResource(R.drawable.ic_favorite_red);

                        movie.setId((long) movie.getMovie_id());
                        movie.setGenresString(Utility.getGenresAsString(movie));
                        movie.save();
                    }

                }
            });

            getActivity().getSupportLoaderManager().initLoader(CASTS_LOADER_ID, null, this).forceLoad();
        }

        if(arrowBack != null)
            arrowBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

        return rootView;
    }

    public void setCastsInformation(ArrayList<Cast> castsInformation) {

        CastsListAdapter castsListAdapter = new CastsListAdapter(getContext(), castsInformation);
        castsView.setAdapter(castsListAdapter);

    }

    public void setMovieInformation(Movie movieInformation) {
        titleText.setText(movieInformation.getTitle());
        voteText.setText(movieInformation.getVote_count() + " votes");
        markText.setText(movieInformation.getVote_average() + "");
        dateText.setText(movieInformation.getRelease_date());
        overviewText.setText(movieInformation.getOverview());

        List<Genre> genres = Genre.listAll(Genre.class);
        categoriesText.setText(Utility.getGenresAsString(movieInformation));

        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w185" + movieInformation.getPoster_path())
                .into(posterImage);

        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w500" + movieInformation.getBackdrop_path())
                .into(backdropImage);
    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> mLoader = null;

        switch (id) {
            case CASTS_LOADER_ID: {
                mLoader = new CastLoader(getContext(), movie.getMovie_id(), "ru-RU");
                break;
            }
        }

        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data != null) {
            switch (loader.getId()) {
                case CASTS_LOADER_ID: {

                    setCastsInformation(Utility.parseCastsJSON(data));

                    break;
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
