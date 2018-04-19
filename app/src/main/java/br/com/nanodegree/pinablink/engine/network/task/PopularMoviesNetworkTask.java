package br.com.nanodegree.pinablink.engine.network.task;


import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import java.net.URL;
import br.com.nanodegree.pinablink.MainActivity;
import br.com.nanodegree.pinablink.dataObject.PopularMovies;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkRun;
import br.com.nanodegree.pinablink.engine.parser.PopularMoviesParserData;

/**
 * Created by Pinablink on 14/04/2018.
 */
public class PopularMoviesNetworkTask
        extends AsyncTask<URL, Void, PopularMovies>  {

    private PopularMoviesParserData parserData;
    private PopularMoviesNetworkRun networkRun;
    private MainActivity activityRefer;

    public PopularMoviesNetworkTask(MainActivity pMainActivity,
                                    PopularMoviesParserData pFormatData,
                                    PopularMoviesNetworkRun pNetworkRun) {

        this.activityRefer = pMainActivity;
        this.parserData = pFormatData;
        this.networkRun = pNetworkRun;

    }

    private void searchImages(PopularMovies pPopularMovies) {
        this.activityRefer.onSearchImages(pPopularMovies);
    }

    @Override
    protected PopularMovies doInBackground(URL... urls)  {
        URL urlRefer = urls[0];
        String strReturn = networkRun.getResponseDataInTheMovieDB(urlRefer);

        PopularMovies returnPopularMovies = this.parserData.process(strReturn);
        if (returnPopularMovies != null) {
            this.searchImages(returnPopularMovies);
        }

        return returnPopularMovies;
    }

    @Override
    protected void onPreExecute() {
        this.activityRefer.onInitProgressBar();
    }

    @Override
    protected void onPostExecute(PopularMovies popularMovies) {
        this.activityRefer.onPostFinished(popularMovies);
    }

}
