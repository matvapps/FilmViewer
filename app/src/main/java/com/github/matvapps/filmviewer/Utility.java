package com.github.matvapps.filmviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.matvapps.filmviewer.entity.Cast;
import com.github.matvapps.filmviewer.entity.Genre;
import com.github.matvapps.filmviewer.entity.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Utility {

    public static ArrayList<Movie> parseMoviesJSON(String jsonString) {

        try {
            ArrayList<Movie> movies = new ArrayList<>();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            // root json
            JSONObject rootObject = new JSONObject(jsonString);
            // get results from root json
            JSONArray resultsObject = rootObject.getJSONArray("results");

            for (int i = 0; i < resultsObject.length(); i++) {

                // json object for each of movie as string
                String movieJSON = resultsObject.getJSONObject(i).toString();
                // add to list converted json
                movies.add(gson.fromJson(movieJSON, Movie.class));

            }

            return movies;

        } catch (JSONException e) {
            Log.e(TAG, "parseJSON: Error in Json", e);
        }

        return null;
    }

    public static ArrayList<Genre> parseGenresJSON(String jsonString) {
        try {
            ArrayList<Genre> movies = new ArrayList<>();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            // root json
            JSONObject rootObject = new JSONObject(jsonString);
            // get results from root json
            JSONArray resultsObject = rootObject.getJSONArray("genres");

            for (int i = 0; i < resultsObject.length(); i++) {

                // json object for each of movie as string
                String genreJson = resultsObject.getJSONObject(i).toString();
                // add to list converted json
                movies.add(gson.fromJson(genreJson, Genre.class));
            }
            return movies;

        } catch (JSONException e) {
            Log.e(TAG, "parseJSON: Error in Json", e);
        }

        return null;

    }

    public static ArrayList<Cast> parseCastsJSON(String jsonString) {
        try {
            ArrayList<Cast> movies = new ArrayList<>();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            // root json
            JSONObject rootObject = new JSONObject(jsonString);
            // get results from root json
            JSONArray resultsObject = rootObject.getJSONArray("cast");

            for (int i = 0; i < resultsObject.length(); i++) {

                // json object for each of movie as string
                String genreJson = resultsObject.getJSONObject(i).toString();
                // add to list converted json
                movies.add(gson.fromJson(genreJson, Cast.class));
            }
            return movies;

        } catch (JSONException e) {
            Log.e(TAG, "parseJSON: Error in Json", e);
        }

        return null;

    }

    public static String getGenresAsString(@NonNull Movie movie) {
        String categories = "";
        List<Genre> genres = Genre.listAll(Genre.class);

        if (movie.getGenre_ids() == null) {
            Log.e(TAG, "getGenresAsString: " + movie.getGenresString() );
            return movie.getGenresString();
        }

        int movieGenreLength = movie.getGenre_ids().length;

        for (int i = 0; i < movieGenreLength; i++) {
            for (int j = 0; j < genres.size(); j++) {
                if (movie.getGenre_ids()[i]
                        == genres.get(j).getGenreId()) {

                    if (i < movieGenreLength - 1) {
                        categories += genres.get(j).getGenreName() + ", ";
                    } else {
                        categories += genres.get(j).getGenreName();
                    }

                }
            }
        }

        return categories;
    }

    public static boolean isMovieInFavourites(int movieId) {
        Movie movie = Movie.findById(Movie.class, movieId);
        return movie != null;

    }

    public static byte[] GetByteFromBitmap(Bitmap bmp)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] byteArrayToBeCOnvertedIntoBitMap)
    {
        return BitmapFactory.decodeByteArray(
                byteArrayToBeCOnvertedIntoBitMap, 0,
                byteArrayToBeCOnvertedIntoBitMap.length);
    }

}
