package br.com.nanodegree.pinablink.engine.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.nanodegree.pinablink.DetailActivity;
import br.com.nanodegree.pinablink.R;
import br.com.nanodegree.pinablink.dataObject.Movie;

/**
 * Created by Pinablink on 17/04/2018.
 */
public class PopularMoviesPosterOnClick implements View.OnClickListener{

    /**
     *
     */
    private Movie refMovie;

    /**
     *
     */
    private Context refContext;

    /**
     *
     * @param movie
     * @param pContext
     */
    public PopularMoviesPosterOnClick (Movie movie, Context pContext) {
        this.refMovie = movie;
        this.refContext = pContext;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.refContext,DetailActivity.class);
        String keyPutExtra = this.refContext.getString(R.string.name_movie_trans_activity);
        intent.putExtra(keyPutExtra, this.refMovie);
        this.refContext.startActivity(intent);
    }
}
