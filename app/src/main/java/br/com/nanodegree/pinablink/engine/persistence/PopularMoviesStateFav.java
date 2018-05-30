package br.com.nanodegree.pinablink.engine.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import br.com.nanodegree.pinablink.R;
import br.com.nanodegree.pinablink.dataObject.Movie;

/**
 * Created by Pinablink on 25/05/2018.
 */
public final class PopularMoviesStateFav {

    public static void setFav (Context pContext, Movie movie) {
        String strCfgKey = pContext.getString(R.string.cfg_state_fav);
        String idMovie = String.valueOf(movie.getId());
        SharedPreferences sharedPreferenceMovie = pContext.getSharedPreferences(strCfgKey,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editorPreferences = sharedPreferenceMovie.edit();
        editorPreferences.putBoolean(idMovie, true);
        editorPreferences.apply();
    }

    public static void removeFav (Context pContext, Movie movie) {
        String strCfgKey = pContext.getString(R.string.cfg_state_fav);
        String idMovie = String.valueOf(movie.getId());
        SharedPreferences sharedPreferenceMovie = pContext.getSharedPreferences(strCfgKey,
                Context.MODE_PRIVATE);
        boolean contains = sharedPreferenceMovie.contains(idMovie);

        if (contains) {
            SharedPreferences.Editor editorPreferences = sharedPreferenceMovie.edit();
            editorPreferences.remove(idMovie);
            editorPreferences.apply();
        }
    }

    public static void clear (Context pContext, Movie movie) {
        String strCfgKey = pContext.getString(R.string.cfg_state_fav);
        SharedPreferences sharedPreferenceMovie = pContext.getSharedPreferences(strCfgKey,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editorPreferences = sharedPreferenceMovie.edit();
        editorPreferences.clear();
        editorPreferences.apply();
    }

    public static boolean isFav (Context pContext, Movie movie) {
        boolean returnRequest = false;
        String strCfgKey = pContext.getString(R.string.cfg_state_fav);
        SharedPreferences sharedPreferenceMovie = pContext.getSharedPreferences(strCfgKey,
                Context.MODE_PRIVATE);
        String idMovie = String.valueOf(movie.getId());
        boolean contains = sharedPreferenceMovie.contains(idMovie);

        if (contains) {
            returnRequest = sharedPreferenceMovie.getBoolean(idMovie, false);
        }

        return returnRequest;
    }
}
