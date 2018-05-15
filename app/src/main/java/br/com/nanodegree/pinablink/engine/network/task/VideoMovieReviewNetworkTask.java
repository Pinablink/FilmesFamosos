package br.com.nanodegree.pinablink.engine.network.task;


import android.os.AsyncTask;
import java.net.URL;
import br.com.nanodegree.pinablink.dataObject.DetailVideoReviewMovie;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkConfig;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkRun;
import br.com.nanodegree.pinablink.engine.parser.PopularMoviesParserData;

/**
 * Created by Pinablink on 12/05/2018.
 */
public class VideoMovieReviewNetworkTask
        extends AsyncTask<Movie, Void, Movie> {


    private PopularMoviesNetworkConfig popularMoviesNetworkConfig;
    private PopularMoviesParserData parserData;
    private PopularMoviesNetworkRun networkRun;
    private AsyncTaskNetworkDelegator activityRefer;


    public VideoMovieReviewNetworkTask (PopularMoviesNetworkConfig pPopularMoviesNetworkConfig,
                                        PopularMoviesParserData pParserData,
                                        PopularMoviesNetworkRun pNetworkRun,
                                        AsyncTaskNetworkDelegator pActivityRefer){

        this.popularMoviesNetworkConfig = pPopularMoviesNetworkConfig;
        this.parserData = pParserData;
        this.networkRun = pNetworkRun;
        this.activityRefer = pActivityRefer;
    }
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param dataInput The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Movie doInBackground(Movie... dataInput) {
        Movie movie = (Movie)dataInput[0];
        String idMovie = movie.getId();
        URL urlReview = this.popularMoviesNetworkConfig.getURLMovieReview(idMovie);
        URL urlTrailer = this.popularMoviesNetworkConfig.getURLMovieTrailer(idMovie);
        String data = this.networkRun.getResponseDataInTheMovieDB(urlReview);
        DetailVideoReviewMovie detailMovie = this.parserData.processDetail(data);

        if (detailMovie == null) {
            detailMovie = new DetailVideoReviewMovie();
        }

        movie.setDetailVideoReviewMovie(detailMovie);
        String dataTrailer = this.networkRun.getResponseDataInTheMovieDB(urlTrailer);
        this.parserData.processDetailTrailer(dataTrailer, detailMovie);

        this.activityRefer.onSearchImages(movie);

        return movie;
    }

    @Override
    protected void onPreExecute() {
        this.activityRefer.onInitProgressBar();
    }

    @Override
    protected void onPostExecute(Movie reviewMovie) {
        this.activityRefer.onPostFinished(reviewMovie);
    }
}
