package com.github.matvapps.filmviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.matvapps.filmviewer.R;
import com.github.matvapps.filmviewer.Utility;
import com.github.matvapps.filmviewer.entity.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> movies;
    private ViewHolder viewHolder;

    public MovieGridAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View rootView;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.grid_item_movie, parent, false);

            viewHolder = new ViewHolder(rootView);

            rootView.setTag(viewHolder);
        } else {
            rootView = convertView;
            viewHolder = (ViewHolder) rootView.getTag();
        }

        final Movie movie = movies.get(position);

        viewHolder.movie_title_txt.setText(movie.getTitle());
        viewHolder.movie_poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185" + movie.getPoster_path())
                .into(viewHolder.movie_poster);


        viewHolder.movie_categories_txt.setText(Utility.getGenresAsString(movie));

        if (!Utility.isMovieInFavourites(movie.getMovie_id())) {
            viewHolder.movie_favourite_img.setImageResource(R.drawable.ic_favorite);

        } else {
            viewHolder.movie_favourite_img.setImageResource(R.drawable.ic_favorite_red);

        }

        viewHolder.movie_favourite_img.setOnClickListener(new View.OnClickListener() {
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

        return rootView;
    }


    public class ViewHolder {

        @BindView(R.id.movie_poster)
        ImageView movie_poster;
        @BindView(R.id.movie_title)
        TextView movie_title_txt;
        @BindView(R.id.movie_categories)
        TextView movie_categories_txt;
        @BindView(R.id.btn_movie_favourite)
        ImageView movie_favourite_img;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
