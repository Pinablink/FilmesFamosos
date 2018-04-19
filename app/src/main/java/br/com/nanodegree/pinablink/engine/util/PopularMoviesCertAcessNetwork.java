package br.com.nanodegree.pinablink.engine.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Pinablink on 18/04/2018.
 */
public final class PopularMoviesCertAcessNetwork {

    /**
     * @param pContext
     * @return
     */
    public final static boolean isNetworkAcessOK(Context pContext) {

        boolean response = true;
        ConnectivityManager connManager = (ConnectivityManager) pContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        response = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return response;
    }


}
