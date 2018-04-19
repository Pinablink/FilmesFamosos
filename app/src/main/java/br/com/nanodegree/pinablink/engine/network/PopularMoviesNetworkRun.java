package br.com.nanodegree.pinablink.engine.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Pinablink on 14/04/2018.
 */
public class PopularMoviesNetworkRun {
    /**
     *
     */
    public PopularMoviesNetworkRun() {
        super();
    }

    /**
     * @param url
     * @return
     */
    public String getResponseDataInTheMovieDB(URL url) {

        String strReturn = null;
        HttpURLConnection httpUrlConnection = null;
        try {
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStreamObj = httpUrlConnection.getInputStream();
            Scanner scanStream = new Scanner(inputStreamObj);
            scanStream.useDelimiter("\\A");
            boolean nextOk = scanStream.hasNext();

            if (nextOk) {
                strReturn = scanStream.next();
            }

        } catch (IOException e) {
            strReturn = "";
        } finally {
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
        }

        return strReturn;
    }
}
