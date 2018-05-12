package br.com.nanodegree.pinablink;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

import br.com.nanodegree.pinablink.dataObject.DetailVideoReviewMovie;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.dataObject.PopularMovies;
import br.com.nanodegree.pinablink.dataObject.Review;
import br.com.nanodegree.pinablink.engine.network.task.ActivityTask;
import br.com.nanodegree.pinablink.engine.network.task.VideoMovieReviewNetworkTask;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesCertAcessNetwork;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesMsg;

/**
 *
 */
public class DetailActivity
        extends ActivityTask {

    private Movie refMovie;
    private TextView textTitle;
    private TextView textSinopse;
    private TextView textVoteAverage;
    private TextView textReleaseDate;

    protected void initResourceScreen() {
        this.textTitle = (TextView) findViewById(R.id.mTitle);
        this.textSinopse = (TextView) findViewById(R.id.mSinopse);
        this.textVoteAverage = (TextView) findViewById(R.id.mVoteAverage);
        this.textReleaseDate = (TextView) findViewById(R.id.mReleaseDate);
    }

    private void loadMovie() {

        String keyStringExtra = getString(R.string.name_movie_trans_activity);
        Intent intentOrigin = getIntent();
        this.refMovie  = intentOrigin.getExtras().getParcelable(keyStringExtra);
        boolean isNetworkOk = PopularMoviesCertAcessNetwork.isNetworkAcessOK(this.getApplicationContext());

        if (!isNetworkOk) {
            String msgErro = this.getString(R.string.app_erro_network_acess_detail);
            new PopularMoviesMsg().showMessageErro(msgErro, DetailActivity.this);
        } else {
            new VideoMovieReviewNetworkTask(this.networkConfig,
                    this.parserData, this.networkRun, this).execute(new Movie[]{this.refMovie});
        }
        /*String idMovie = this.refMovie.getId();

        String strBackDropImage = this.refMovie.getBackdropPath();
        RequestCreator refRequestImg = Picasso.with(this.getApplicationContext()).load(strBackDropImage);

        this.refMovie.setRefRequesImg(refRequestImg);*/

    }

    /**
     *
     */
    private void createScreen() {
        String title = this.refMovie.getTitle();
        String sinopse = this.refMovie.getOverview();
        String voteAverage = this.refMovie.getVoteAverage();
        String releaseDate = this.refMovie.getReleaseDate();

        ImageView imageBackDrop = (ImageView) findViewById(R.id.backDrop_image);
        RequestCreator requestCreator = this.refMovie.getRefRequesImg();
        requestCreator.into(imageBackDrop);

        this.textTitle.setText(title);
        this.textSinopse.setText(sinopse);
        this.textVoteAverage.setText(voteAverage);
        this.textReleaseDate.setText(releaseDate);
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
        //this.createScreen();
        //this.actionBarEnabledDisplayHome(actionBar);



    }


    @Override
    public void onPostFinished(Object detailMovies) {
        DetailVideoReviewMovie detailMovie = (DetailVideoReviewMovie)detailMovies;
        List<Review> listReview = detailMovie.getListReview();
        int len = listReview.size();
        String lenStr = ""+ len;
    }

    @Override
    public void onSearchImages(Object pDetailMovies) {
    }

    @Override
    public void onInitProgressBar() {
    }
}
