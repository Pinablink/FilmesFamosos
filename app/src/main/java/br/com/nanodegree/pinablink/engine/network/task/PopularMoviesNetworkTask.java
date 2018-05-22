package br.com.nanodegree.pinablink.engine.network.task;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.net.URL;
import br.com.nanodegree.pinablink.dataObject.PopularMovies;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkRun;
import br.com.nanodegree.pinablink.engine.parser.PopularMoviesParserData;

/**
 * Created by Pinablink on 14/04/2018.
 */
public class PopularMoviesNetworkTask
        extends AsyncTaskLoader<PopularMovies> {

    private PopularMoviesParserData parserData;
    private PopularMoviesNetworkRun networkRun;
    private AsyncTaskNetworkDelegator activityRefer;
    private URL urlRefer;
    private PopularMovies popularMoviesRef;

    public PopularMoviesNetworkTask(Context context,
                                    AsyncTaskNetworkDelegator pMainActivity,
                                    URL pUrl,
                                    PopularMoviesParserData pFormatData,
                                    PopularMoviesNetworkRun pNetworkRun) {
        super(context);
        this.urlRefer = pUrl;
        this.activityRefer = pMainActivity;
        this.parserData = pFormatData;
        this.networkRun = pNetworkRun;
    }

    @Override
    public PopularMovies loadInBackground() {
        String strReturn = networkRun.getResponseDataInTheMovieDB(urlRefer);
        PopularMovies returnPopularMovies = this.parserData.process(strReturn);
        if (returnPopularMovies != null) {
            this.searchImages(returnPopularMovies);
        }

        return returnPopularMovies;
    }


    @Override
    public void deliverResult(PopularMovies data) {
        this.popularMoviesRef = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        this.activityRefer.onInitProgressBar();
        this.forceLoad();
    }

    private void searchImages(PopularMovies pPopularMovies) {
        this.activityRefer.onSearchImages(pPopularMovies);
    }

}
