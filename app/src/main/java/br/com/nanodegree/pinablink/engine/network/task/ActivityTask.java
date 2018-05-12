package br.com.nanodegree.pinablink.engine.network.task;

import android.support.v7.app.AppCompatActivity;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkConfig;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkRun;
import br.com.nanodegree.pinablink.engine.parser.PopularMoviesParserData;

/**
 * Created by Pinablink on 12/05/2018.
 */
public abstract class ActivityTask extends AppCompatActivity
        implements AsyncTaskNetworkDelegator {

    protected PopularMoviesParserData parserData;
    protected PopularMoviesNetworkConfig networkConfig;
    protected PopularMoviesNetworkRun networkRun;

    protected void initResource() {
        this.parserData = new PopularMoviesParserData();
        this.networkConfig = new PopularMoviesNetworkConfig(this);
        this.networkRun = new PopularMoviesNetworkRun();
    }

    protected abstract void initResourceScreen();
}
