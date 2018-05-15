package br.com.nanodegree.pinablink;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.Iterator;
import java.util.List;
import br.com.nanodegree.pinablink.dataObject.DetailVideoReviewMovie;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.dataObject.MovieTrailer;
import br.com.nanodegree.pinablink.dataObject.Review;
import br.com.nanodegree.pinablink.engine.adapter.PopularMoviesReviewAdapter;
import br.com.nanodegree.pinablink.engine.network.task.ActivityTask;
import br.com.nanodegree.pinablink.engine.network.task.VideoMovieReviewNetworkTask;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesCertAcessNetwork;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesMsg;

/**
 *
 */
public class DetailActivity
        extends ActivityTask {

    private TextView textTitle;
    private TextView textSinopse;
    private TextView textVoteAverage;
    private TextView textReleaseDate;
    private TextView textEmptyMsg;
    private ImageView imageBackDrop;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private RecyclerView recycleViewReviewPresentation;

    protected void initResourceScreen() {
        this.textTitle = (TextView) findViewById(R.id.mTitle);
        this.textSinopse = (TextView) findViewById(R.id.mSinopse);
        this.textVoteAverage = (TextView) findViewById(R.id.mVoteAverage);
        this.textReleaseDate = (TextView) findViewById(R.id.mReleaseDate);
        this.textEmptyMsg = (TextView) findViewById(R.id.emptyReviewMsg);
        this.imageBackDrop  = (ImageView) findViewById(R.id.backDrop_image);
        this.progressBar    = (ProgressBar) findViewById(R.id.dProgress);
        this.scrollView = (ScrollView) findViewById(R.id.scrollViewDetail);
        this.recycleViewReviewPresentation = (RecyclerView) findViewById(R.id.recyclerview_review_movie);
    }

    private void loadMovie() {

        String keyStringExtra = getString(R.string.name_movie_trans_activity);
        Intent intentOrigin = getIntent();
        Movie refMovie  = intentOrigin.getExtras().getParcelable(keyStringExtra);
        boolean isNetworkOk = PopularMoviesCertAcessNetwork.isNetworkAcessOK(this.getApplicationContext());

        if (!isNetworkOk) {
            String msgErro = this.getString(R.string.app_erro_network_acess_detail);
            new PopularMoviesMsg().showMessageErro(msgErro, DetailActivity.this);
        } else {
            new VideoMovieReviewNetworkTask(this.networkConfig,
                    this.parserData, this.networkRun, this).execute(new Movie[]{refMovie});
        }

    }

    private void inputDataScreen(Movie refMovie) {
        String title = refMovie.getTitle();
        String sinopse = refMovie.getOverview();
        String voteAverage = refMovie.getVoteAverage();
        String releaseDate = refMovie.getReleaseDate();

        this.textTitle.setText(title);
        this.textSinopse.setText(sinopse);
        this.textVoteAverage.setText(voteAverage);
        this.textReleaseDate.setText(releaseDate);

        DetailVideoReviewMovie detailVideoReviewMovie =
                refMovie.getDetailVideoReviewMovie();

        if (detailVideoReviewMovie.isExistListReview()) {
            List<Review> reviewList =  detailVideoReviewMovie.getListReview();
            PopularMoviesReviewAdapter adapter = new PopularMoviesReviewAdapter (reviewList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(),
                    LinearLayoutManager.VERTICAL, false){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };

            this.recycleViewReviewPresentation.setLayoutManager(linearLayoutManager);
            this.recycleViewReviewPresentation.setAdapter(adapter);
            this.recycleViewReviewPresentation.setVisibility(View.VISIBLE);


        } else {
            this.textEmptyMsg.setVisibility(View.VISIBLE);
        }

    }

    private void actionBarEnabledDisplayHome (ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(R.string.title_activity_detail);
        ActionBar actionBar = getSupportActionBar();
        this.initResource();
        this.initResourceScreen();
        this.loadMovie();
        this.actionBarEnabledDisplayHome(actionBar);
    }


    @Override
    public void onPostFinished(Object detailMovies) {
        final int POS_INI_X_SCROLL = 0;
        final int POS_INI_Y_SCROLL = 0;
        Movie detailMovie = (Movie)detailMovies;
        RequestCreator requestCreatorObject = detailMovie.getRefRequesImg();

        this.progressBar.setVisibility(View.INVISIBLE);
        this.scrollView.setVisibility(View.VISIBLE);
        this.scrollView.scrollTo(POS_INI_X_SCROLL, POS_INI_Y_SCROLL);

        this.inputDataScreen(detailMovie);
        requestCreatorObject.into(this.imageBackDrop);


    }

    @Override
    public void onSearchImages(Object pDetailMovies) {
        Movie movie = (Movie) pDetailMovies;
        String strBackDropImage = movie.getBackdropPath();

        RequestCreator refRequestImg = Picasso.with(this.getApplicationContext()).load(strBackDropImage);
        movie.setRefRequesImg(refRequestImg);

        DetailVideoReviewMovie detailMovie = movie.getDetailVideoReviewMovie();
        List<MovieTrailer> listMovieTrailer =  detailMovie.getListMovieTrailer();

        if (detailMovie.isExistListTrailerMovie()) {
            Iterator<MovieTrailer> iterList = listMovieTrailer.iterator();

            while (iterList.hasNext()) {
                MovieTrailer movieTrailer = iterList.next();
                String pathThumbnail = movieTrailer.getPathThumbnail();
                RequestCreator refRequest1 = Picasso.with(this.getApplicationContext()).load(pathThumbnail);
                movieTrailer.setRefRequestImg(refRequest1);
            }
        }
    }

    @Override
    public void onInitProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }
}
