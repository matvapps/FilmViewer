package com.github.matvapps.filmviewer.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
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


public class GenresLoader extends AsyncTaskLoader<String> {

    private final String TAG = GenresLoader.class.getSimpleName();
    private String language;

    public GenresLoader(Context context, @NonNull String language) {
        super(context);
        this.language = language;
    }

    @Override
    public String loadInBackground() {

        // create request url
        URL requestUrl = new HttpUrl.Builder()
                .scheme(MovieUrlContract.PROTOCOL)
                .host(MovieUrlContract.PARENT_PATH)
                .addPathSegment(MovieUrlContract.API_VERSION)
                .addPathSegment(MovieUrlContract.GENRE_PATH)
                .addPathSegment(MovieUrlContract.MOVIE_PATH)
                .addPathSegment(MovieUrlContract.LIST_PATH)
                .addQueryParameter(MovieUrlContract.API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .addQueryParameter(MovieUrlContract.LANG_PARAM, language)
                .build().url();


        OkHttpClient client = new OkHttpClient();

        // request from request url
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        try {
            String responseJSONString;

            Log.d(TAG, "loadInBackground: request start...");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "loadInBackground: request end...");

            // get response body as string
            responseJSONString = response.body().string();

            return responseJSONString;


        } catch (IOException | NullPointerException e) {
            Log.e(TAG, "loadInBackground: " + e.getMessage());
        }

        return null;
    }

}
