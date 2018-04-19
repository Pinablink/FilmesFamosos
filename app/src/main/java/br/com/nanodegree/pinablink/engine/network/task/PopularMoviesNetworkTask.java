package br.com.nanodegree.pinablink.engine.network.task;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.dataObject.PopularMovies;
import br.com.nanodegree.pinablink.dataObject.PopularMoviesError;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkRun;
import br.com.nanodegree.pinablink.engine.parser.PopularMoviesParserData;
import br.com.nanodegree.pinablink.engine.adapter.PopularMoviesPosterAdapter;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesMsg;
import br.com.nanodegree.pinablink.exception.PMJSonErrorReader;

/**
 * Created by Pinablink on 14/04/2018.
 */
public class PopularMoviesNetworkTask extends AsyncTask<URL, Void, PopularMovies> {

    /**
     *
     */
    private PopularMoviesParserData parserData;

    /**
     *
     */
    private PopularMoviesNetworkRun networkRun;

    /**
     *
     */
    private Context context;

    /**
     *
     */
    private ProgressBar refProgressBar;

    /**
     *
     */
    private RecyclerView refRecyclerViewPosterPresentation;

    /**
     * @param pContext
     * @param pProgressBar
     * @param pNetworkRun
     * @param pRecycleViewPosterPresentation
     */
    public PopularMoviesNetworkTask(Context pContext,
                                    ProgressBar pProgressBar,
                                    PopularMoviesParserData pFormatData,
                                    PopularMoviesNetworkRun pNetworkRun,
                                    RecyclerView pRecycleViewPosterPresentation) {
        this.context = pContext;
        this.refProgressBar = pProgressBar;
        this.parserData = pFormatData;
        this.networkRun = pNetworkRun;
        this.refRecyclerViewPosterPresentation = pRecycleViewPosterPresentation;
    }

    /**
     * @param pPopularMovies
     */
    private void searchImages(PopularMovies pPopularMovies) {
        List<Movie> listMovie = pPopularMovies.getListMovie();
        Movie refMovieLine = null;
        RequestCreator requestCreatorRefLine0 = null;
        String strPosterPath = null;
        String strBackDropPath = null;
        Iterator<Movie> iterMovie = listMovie.iterator();

        while (iterMovie.hasNext()) {
            refMovieLine = iterMovie.next();
            strPosterPath = refMovieLine.getPosterPath();
            strBackDropPath = refMovieLine.getBackdropPath();
            requestCreatorRefLine0 = Picasso.with(this.context).load(strPosterPath);
            refMovieLine.setRefRequesImg(requestCreatorRefLine0);
        }

    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param urls The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected PopularMovies doInBackground(URL... urls) {
        PopularMovies returnPopularMovies = null;
        URL urlRefer = urls[0];
        String strReturn = networkRun.getResponseDataInTheMovieDB(urlRefer);

        try {
            returnPopularMovies = this.parserData.process(strReturn);
            this.searchImages(returnPopularMovies);
        } catch (PMJSonErrorReader pmjSonErrorReader) {
            PopularMoviesError popularMoviesError = new PopularMoviesError();
            popularMoviesError.setThrowable(pmjSonErrorReader);
            returnPopularMovies = new PopularMovies();
            returnPopularMovies.setpError(popularMoviesError);
        }

        return returnPopularMovies;
    }

    @Override
    protected void onPreExecute() {
        this.refProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * @param popularMovies
     */
    @Override
    protected void onPostExecute(PopularMovies popularMovies) {
        this.refProgressBar.setVisibility(View.INVISIBLE);
        PopularMoviesError popularMoviesError = popularMovies.getpError();
        boolean existError = (popularMoviesError != null);

        if (existError) {
            PMJSonErrorReader jsonErrorReader = (PMJSonErrorReader) popularMoviesError.getThrowable();
            new PopularMoviesMsg().showMessageErro(jsonErrorReader.getMessage(), this.context);
        } else {
            this.refRecyclerViewPosterPresentation.setVisibility(View.VISIBLE);
            List<Movie> refListMovies = popularMovies.getListMovie();
            PopularMoviesPosterAdapter popularMoviesPosterAdapter = new PopularMoviesPosterAdapter(refListMovies);
            GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
            this.refRecyclerViewPosterPresentation.setLayoutManager(layoutManager);
            this.refRecyclerViewPosterPresentation.setAdapter(popularMoviesPosterAdapter);
        }
    }
}
