package br.com.nanodegree.pinablink.engine.network;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;

import br.com.nanodegree.pinablink.R;


/**
 * Disponibiliza as URLs que atende a Aplicação
 * Created by Pinablink on 14/04/2018.
 */
public class PopularMoviesNetworkConfig {

    /**
     *
     */
    private Uri uriFilterMoviePopular;

    /**
     *
     */
    private Uri uriFilterMovieTopRated;

    /**
     *
     */
    private AppCompatActivity activityRefer;

    /**
     *
     */
    public PopularMoviesNetworkConfig(AppCompatActivity pActivityRefer) {
        super();
        this.activityRefer = pActivityRefer;
        this.init();
    }

    /**
     *
     */
    private void init() {
        this.uriFilterMoviePopular = this.createUriFilterMoviePopular();
        this.uriFilterMovieTopRated = this.createUriFilterMovieTopRated();
    }

    /**
     *
     * @return
     */
    public URL getURLPopularMovies () {

        URL urlReturn = null;

        try {
            urlReturn = new URL(this.uriFilterMoviePopular.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return urlReturn;
    }

    /**
     *
     * @return
     */
    public URL getURLTopRatedMovies () {
        URL urlReturn = null;

        try {
            urlReturn = new URL(this.uriFilterMovieTopRated.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlReturn;
    }

    /**
     *
     * @return
     */
    private Uri createUriFilterMoviePopular() {
        final String cfg_pathString =   this.activityRefer.getString(R.string.cfg_app_path_url_popular_movies);
        final String cfg_appApiKey  =   this.activityRefer.getString(R.string.cfg_app_api_key);
        final String value_apiKey   =   this.activityRefer.getString(R.string.cfg_app_api_key_value);

        Uri uriReturn = Uri.parse(cfg_pathString).buildUpon()
                .appendQueryParameter(cfg_appApiKey, value_apiKey).build();

        return uriReturn;
    }

    /**
     *
     * @return
     */
    private Uri createUriFilterMovieTopRated() {
        final String cfg_pathString =   this.activityRefer.getString(R.string.cfg_app_path_url_top_rated_movies);
        final String cfg_appApiKey  =   this.activityRefer.getString(R.string.cfg_app_api_key);
        final String value_apiKey   =   this.activityRefer.getString(R.string.cfg_app_api_key_value);

        Uri uriReturn = Uri.parse(cfg_pathString).buildUpon()
                .appendQueryParameter(cfg_appApiKey, value_apiKey).build();

        return uriReturn;
    }
}
