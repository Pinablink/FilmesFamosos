package br.com.nanodegree.pinablink.engine.network.task;


import android.content.Context;
import java.net.URL;
import br.com.nanodegree.pinablink.dataObject.DetailVideoReviewMovie;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkConfig;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkRun;
import br.com.nanodegree.pinablink.engine.parser.PopularMoviesParserData;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Pinablink on 12/05/2018.
 */
public class VideoMovieReviewNetworkTask
        extends AsyncTaskLoader<Movie> {

    private PopularMoviesNetworkConfig pPopularMoviesNetworkConfig;
    private PopularMoviesParserData parserData;
    private PopularMoviesNetworkRun networkRun;
    private AsyncTaskNetworkDelegator activityRefer;
    private Movie movieRefer;
    private Movie movieResult;

    public VideoMovieReviewNetworkTask(Context context,
                                       Movie movie,
                                       PopularMoviesParserData pParserData,
                                       PopularMoviesNetworkRun pNetworkRun,
                                       PopularMoviesNetworkConfig popularMoviesNetworkConfig,
                                       AsyncTaskNetworkDelegator pActivityRefer) {
        super(context);
        this.parserData = pParserData;
        this.networkRun = pNetworkRun;
        this.activityRefer = pActivityRefer;
        this.movieRefer = movie;
        this.pPopularMoviesNetworkConfig = popularMoviesNetworkConfig;
    }

    @Override
    public Movie loadInBackground() {
        String idMovie = this.movieRefer.getId();
        URL urlReview = this.pPopularMoviesNetworkConfig.getURLMovieReview(idMovie);
        URL urlTrailer = this.pPopularMoviesNetworkConfig.getURLMovieTrailer(idMovie);
        String data = this.networkRun.getResponseDataInTheMovieDB(urlReview);
        DetailVideoReviewMovie detailMovie = this.parserData.processDetail(data);

        if (detailMovie == null) {
            detailMovie = new DetailVideoReviewMovie();
        }

        this.movieRefer.setDetailVideoReviewMovie(detailMovie);
        String dataTrailer = this.networkRun.getResponseDataInTheMovieDB(urlTrailer);
        this.parserData.processDetailTrailer(dataTrailer, detailMovie);

        this.activityRefer.onSearchImages(this.movieRefer);

        return this.movieRefer;
    }

    @Override
    protected void onStartLoading() {
        this.activityRefer.onInitProgressBar();
        this.forceLoad();
    }

    @Override
    public void deliverResult(Movie data) {
        super.deliverResult(data);
        this.movieResult = data;
    }
}
