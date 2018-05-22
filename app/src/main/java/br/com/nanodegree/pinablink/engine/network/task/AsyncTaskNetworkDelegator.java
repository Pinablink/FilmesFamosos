package br.com.nanodegree.pinablink.engine.network.task;

import android.content.Context;

import br.com.nanodegree.pinablink.dataObject.PopularMovies;

/**
 * Created by Pinablink on 19/04/2018.
 */
public interface AsyncTaskNetworkDelegator {

    public void onSearchImages (Object pPopularMovies);
    public void onInitProgressBar();

}
