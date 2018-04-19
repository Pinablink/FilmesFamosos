package br.com.nanodegree.pinablink.engine.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import br.com.nanodegree.pinablink.R;

/**
 * Created by Pinablink on 16/04/2018.
 */
public class PopularMoviesPosterAdapterViewHolder extends RecyclerView.ViewHolder {

    /**
     *
     */
    private ImageView imageViewPoster;

    /**
     *
     * @param itemView
     */
    public PopularMoviesPosterAdapterViewHolder(View itemView) {
        super(itemView);
        this.imageViewPoster = (ImageView) itemView.findViewById(R.id.poster_image);
    }

    /**
     *
     * @return
     */
    public ImageView getImageView () {
        return  this.imageViewPoster;
    }
}
