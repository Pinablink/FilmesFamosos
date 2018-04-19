package br.com.nanodegree.pinablink.engine.network.task;

import br.com.nanodegree.pinablink.dataObject.PopularMovies;

/**
 * Created by Pinablink on 19/04/2018.
 */
public interface AsyncTaskNetworkDelegator {

    public void onPostFinished (PopularMovies popularMovies);
    public void onSearchImages (PopularMovies pPopularMovies);
    public void onInitProgressBar();

}
