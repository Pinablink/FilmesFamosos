package br.com.nanodegree.pinablink.engine.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import br.com.nanodegree.pinablink.R;

/**
 * Created by Pinablink on 16/05/2018.
 */
public class PopularMoviesTrailerAdapterViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageViewThumbnail;

    public PopularMoviesTrailerAdapterViewHolder(View itemView) {
        super(itemView);
        this.imageViewThumbnail = (ImageView) itemView.findViewById(R.id.thumbnailMovie);
    }

    public ImageView getImageViewThumbnail () {
        return this.imageViewThumbnail;
    }
}
