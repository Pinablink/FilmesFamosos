package br.com.nanodegree.pinablink.engine.util;

import android.content.Context;
import android.net.Uri;
import br.com.nanodegree.pinablink.R;

/**
 * Created by Pinablink on 22/05/2018.
 */
public final class PopularMoviesFormat {

    public static Uri requestHtmlYoutubeVideo (Context context,
                                                  String key) {

        String strUrlYoutube = context.getString(R.string.cfg_app_call_youtube_webbrowser);
        String paramKeyName = context.getString(R.string.cfg_app_param_name_key);

        Uri uriTrailerYoutube = Uri.parse(strUrlYoutube).buildUpon()
                .appendQueryParameter(paramKeyName, key).build();

        return  uriTrailerYoutube;
    }

}
