package br.com.nanodegree.pinablink.engine.network.task;

import android.support.v7.app.AppCompatActivity;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkConfig;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkRun;
import br.com.nanodegree.pinablink.engine.parser.PopularMoviesParserData;

/**
 * Created by Pinablink on 12/05/2018.
 */
public abstract class ActivityFilmesFamosos extends AppCompatActivity {

    protected PopularMoviesParserData parserData;
    protected PopularMoviesNetworkConfig networkConfig;
    protected PopularMoviesNetworkRun networkRun;
    protected final String ID_BUNDLE_URL_PARAM = "URL_TARGET";
    protected final int POPULAR_MOVIES = 1;
    protected final int TOP_RATED_MOVIES = 2;
    protected final int DETAIL_MOVIE = 3;
    protected static String KEY_TITLE_ACTIVITY = "keyStringTitleActivity";

    protected void initResource() {
        this.parserData = new PopularMoviesParserData();
        this.networkConfig = new PopularMoviesNetworkConfig(this);
        this.networkRun = new PopularMoviesNetworkRun();
    }

    protected abstract void initResourceScreen();
    protected abstract void loadUrl();
}
