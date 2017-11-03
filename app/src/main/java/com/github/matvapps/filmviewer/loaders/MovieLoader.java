package com.github.matvapps.filmviewer.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.github.matvapps.filmviewer.BuildConfig;
import com.github.matvapps.filmviewer.contracts.MovieUrlContract;

import java.io.IOException;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MovieLoader extends AsyncTaskLoader<String> {
    private final String TAG = MovieListLoader.class.getSimpleName();

    // LANG_PARAM in format en-US
    private String lang;
    private int movieId;

//    https://api.themoviedb.org/3/movie/550?api_key=e7baf62ae22f6e433e1feac808e69af5

    public MovieLoader(Context context, int movieId, String lang) {
        super(context);
        this.lang = lang;
        this.movieId = movieId;
    }


    @Override
    public String loadInBackground() {

        // create request url
        URL requestUrl = new HttpUrl.Builder()
                .scheme(MovieUrlContract.PROTOCOL)
                .host(MovieUrlContract.PARENT_PATH)
                .addPathSegment(MovieUrlContract.API_VERSION)
                .addPathSegment(MovieUrlContract.MOVIE_PATH)
                .addPathSegment(String.valueOf(movieId))
                .addQueryParameter(MovieUrlContract.API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .addQueryParameter(MovieUrlContract.LANG_PARAM, lang)
                .build().url();


        OkHttpClient client = new OkHttpClient();

        // request from request url
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        try {
            String responseJSONString;

            Response response = client.newCall(request).execute();

            // get response body as string
            responseJSONString = response.body().string();

            return responseJSONString;

        } catch (IOException | NullPointerException e) {
            Log.e(TAG, "loadInBackground: " + e.getMessage());
        }

        return null;
    }
}
