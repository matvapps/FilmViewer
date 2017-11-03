package com.github.matvapps.filmviewer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.matvapps.filmviewer.R;
import com.github.matvapps.filmviewer.entity.Cast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexandr on 02/11/2017.
 */

public class CastsListAdapter extends RecyclerView.Adapter<CastsListAdapter.ViewHolder> {

    private ArrayList<Cast> casts;
    private Context context;

    public CastsListAdapter(Context context, ArrayList<Cast> casts) {
        this.casts = casts;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_actor_image)
        ImageView actorImageView;
        @BindView(R.id.movie_actor_name)
        TextView actorNameView;
        @BindView(R.id.movie_actor_character)
        TextView actorCharacterView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            actorImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }


    @Override
    public CastsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_casts_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CastsListAdapter.ViewHolder holder, int position) {
        holder.actorCharacterView.setText(casts.get(position).getCharacter());
        holder.actorNameView.setText(casts.get(position).getName());

        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185" + casts.get(position).getProfile_path())
                .into(holder.actorImageView);
    }

    @Override
    public int getItemCount() {
        if (casts == null)
            return 0;
        return casts.size();
    }
}
