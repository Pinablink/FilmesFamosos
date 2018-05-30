package br.com.nanodegree.pinablink.engine.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import br.com.nanodegree.pinablink.R;

/**
 * Created by Pinablink on 28/05/2018.
 */
public class PopularMoviesPosterFavAdapterViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageFavViewPoster;

    public PopularMoviesPosterFavAdapterViewHolder(View itemView) {
        super(itemView);
        this.imageFavViewPoster = itemView.findViewById(R.id.movie_fav_poster_image);
    }

    public ImageView getImageView () {
        return  this.imageFavViewPoster;
    }
}
