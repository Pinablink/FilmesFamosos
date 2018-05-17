package br.com.nanodegree.pinablink.engine.listener;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import br.com.nanodegree.pinablink.R;
import br.com.nanodegree.pinablink.dataObject.MovieTrailer;

/**
 * Created by Pinablink on 16/05/2018.
 */
public class PopularMoviesTrailerOnClick implements View.OnClickListener{

    private MovieTrailer refMovieTrailer;
    private String key;

    public PopularMoviesTrailerOnClick (MovieTrailer pMovieTrailer) {
        this.refMovieTrailer = pMovieTrailer;
        this.key = pMovieTrailer.getKey();
    }


    private void callAppYoutube (View v) {
        Context context = v.getContext();
        String strCallAppPathFormat = "%s%s";
        String pathYoutube = context.getString(R.string.cfg_app_call_youtube_app);
        strCallAppPathFormat = String.format(strCallAppPathFormat, pathYoutube, key);
        Uri uriTrailerYoutube = Uri.parse(strCallAppPathFormat);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriTrailerYoutube);
        context.startActivity(intent);
    }

    private void callBrowserInYoutube (View v) {
        Context context = v.getContext();
        String strUrlYoutube = context.getString(R.string.cfg_app_call_youtube_webbrowser);
        String paramKeyName = context.getString(R.string.cfg_app_param_name_key);

        Uri uriTrailerYoutube = Uri.parse(strUrlYoutube).buildUpon()
                .appendQueryParameter(paramKeyName, key).build();

        Intent intent = new Intent(Intent.ACTION_VIEW, uriTrailerYoutube);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        try {
            this.callAppYoutube(v);
        } catch (ActivityNotFoundException e) {
            this.callBrowserInYoutube(v);
        }

    }
}
