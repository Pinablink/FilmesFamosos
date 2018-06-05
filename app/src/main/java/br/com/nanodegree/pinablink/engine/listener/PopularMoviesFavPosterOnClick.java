package br.com.nanodegree.pinablink.engine.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import br.com.nanodegree.pinablink.DetailFavActivity;
import br.com.nanodegree.pinablink.R;
import br.com.nanodegree.pinablink.dataObject.Movie;

/**
 * Created by Pinablink on 29/05/2018.
 */
public class PopularMoviesFavPosterOnClick implements View.OnClickListener {

    private Movie refMovie;
    private Context refContext;

    public PopularMoviesFavPosterOnClick (Movie movie, Context pContext) {
        this.refMovie = movie;
        this.refContext = pContext;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.refContext,DetailFavActivity.class);
        String keyPutExtra = this.refContext.getString(R.string.name_movie_trans_activity);
        intent.putExtra(keyPutExtra, this.refMovie.getId());
        this.refContext.startActivity(intent);
    }


}
