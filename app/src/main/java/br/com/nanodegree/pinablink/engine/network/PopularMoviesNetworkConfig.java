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


    private Uri uriFilterMoviePopular;
    private Uri uriFilterMovieTopRated;
    private AppCompatActivity activityRefer;


    public PopularMoviesNetworkConfig(AppCompatActivity pActivityRefer) {
        super();
        this.activityRefer = pActivityRefer;
        this.init();
    }


    private void init() {
        this.uriFilterMoviePopular = this.createUriFilterMoviePopular();
        this.uriFilterMovieTopRated = this.createUriFilterMovieTopRated();
    }

    public URL getURLPopularMovies () {

        URL urlReturn = null;

        try {
            urlReturn = new URL(this.uriFilterMoviePopular.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return urlReturn;
    }

    public URL getURLTopRatedMovies () {
        URL urlReturn = null;

        try {
            urlReturn = new URL(this.uriFilterMovieTopRated.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlReturn;
    }

    public URL getURLMovieReview (String id) {
        URL urlReturn = null;
        Uri uUri = this.createUriFilterMovieReview(id);

        try {
            urlReturn = new URL (uUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return urlReturn;
    }

    public URL getURLMovieTrailer (String id) {
        URL urlReturn = null;
        Uri uUri = this.createUriFilterMovieTrailer(id);

        try {
            urlReturn = new URL (uUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return urlReturn;
    }

    private Uri createUriFilterMoviePopular() {
        final String cfg_pathString =   this.activityRefer.getString(R.string.cfg_app_path_url_popular_movies);
        final String cfg_appApiKey  =   this.activityRefer.getString(R.string.cfg_app_api_key);
        final String value_apiKey   =   this.activityRefer.getString(R.string.cfg_app_api_key_value);

        Uri uriReturn = Uri.parse(cfg_pathString).buildUpon()
                .appendQueryParameter(cfg_appApiKey, value_apiKey).build();

        return uriReturn;
    }

    private Uri createUriFilterMovieTopRated() {
        final String cfg_pathString =   this.activityRefer.getString(R.string.cfg_app_path_url_top_rated_movies);
        final String cfg_appApiKey  =   this.activityRefer.getString(R.string.cfg_app_api_key);
        final String value_apiKey   =   this.activityRefer.getString(R.string.cfg_app_api_key_value);

        Uri uriReturn = Uri.parse(cfg_pathString).buildUpon()
                .appendQueryParameter(cfg_appApiKey, value_apiKey).build();

        return uriReturn;
    }

    private Uri createUriFilterMovieReview(String id) {
        String model =  "%s%s%s";
        final String cfg_pathString1 = this.activityRefer.getString(R.string.cfg_app_path_url_review1);
        final String cfg_pathString2 = this.activityRefer.getString(R.string.cfg_app_path_url_review2);
        final String cfg_appApiKey  =   this.activityRefer.getString(R.string.cfg_app_api_key);
        final String value_apiKey   =   this.activityRefer.getString(R.string.cfg_app_api_key_value);
        String pathUrlStr = String.format(model, cfg_pathString1, id, cfg_pathString2);

        Uri uriReturn = Uri.parse(pathUrlStr).buildUpon()
                .appendQueryParameter(cfg_appApiKey, value_apiKey).build();

        return uriReturn;
    }

    private Uri createUriFilterMovieTrailer (String id) {
        String model = "%s%s%s";
        final String cfg_pathString1 = this.activityRefer.getString(R.string.cfg_app_path_url_video1);
        final String cfg_pathString2 = this.activityRefer.getString(R.string.cfg_app_path_url_video2);
        final String cfg_appApiKey  =   this.activityRefer.getString(R.string.cfg_app_api_key);
        final String value_apiKey = this.activityRefer.getString(R.string.cfg_app_api_key_value);
        String pathUrlStr = String.format(model, cfg_pathString1, id, cfg_pathString2);

        Uri uriReturn = Uri.parse(pathUrlStr).buildUpon()
                .appendQueryParameter(cfg_appApiKey, value_apiKey).build();

        return uriReturn;
    }

}
