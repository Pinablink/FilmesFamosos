package br.com.nanodegree.pinablink.engine.listener.popularMoviesInterface;

import br.com.nanodegree.pinablink.dataObject.Movie;

/**
 * Created by Pinablink on 25/05/2018.
 */
public interface PopularMoviesConvertImageBase64On {

    public void onConvertImageBase64 (Movie movie, String strImageBase64);
}
