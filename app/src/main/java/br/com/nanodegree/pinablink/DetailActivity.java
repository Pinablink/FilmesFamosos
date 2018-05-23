package br.com.nanodegree.pinablink;



import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.Iterator;
import java.util.List;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import br.com.nanodegree.pinablink.dataObject.DetailVideoReviewMovie;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.dataObject.MovieTrailer;
import br.com.nanodegree.pinablink.dataObject.Review;
import br.com.nanodegree.pinablink.engine.adapter.PopularMoviesReviewAdapter;
import br.com.nanodegree.pinablink.engine.adapter.PopularMoviesTrailerAdapter;
import br.com.nanodegree.pinablink.engine.network.task.ActivityFilmesFamosos;
import br.com.nanodegree.pinablink.engine.network.task.AsyncTaskNetworkDelegator;
import br.com.nanodegree.pinablink.engine.network.task.VideoMovieReviewNetworkTask;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesCertAcessNetwork;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesFormat;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesMsg;

/**
 *
 */
public class DetailActivity
        extends ActivityFilmesFamosos
        implements AsyncTaskNetworkDelegator, LoaderCallbacks<Movie> {

    private TextView textTitle;
    private TextView textSinopse;
    private TextView textVoteAverage;
    private TextView textReleaseDate;
    private TextView textEmptyMsg;
    private ImageView imageBackDrop;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private RecyclerView recycleViewReviewPresentation;
    private RecyclerView recycleViewTrailerPresentation;
    private MenuItem itemFav;
    private MenuItem itemAddFav;
    private Movie refMovie;
    private String sharedMovieTrailerKey;
    private final String id_state_itemMenuFav = "strItemMenuFav";
    private final String id_state_itemMenuAddFav = "strItemMenuAddFav";
    private final String id_sharedMovie_trailerkey = "strKeyTrailerkey";
    private boolean checkedMenuFav;
    private boolean checkedMenuAddFav;

    protected void loadUrl () {
    }

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
        this.recycleViewTrailerPresentation = (RecyclerView) findViewById(R.id.recyclerview_trailer_movie);
        this.checkedMenuAddFav = true;
        this.checkedMenuFav = false;
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
            LoaderCallbacks<Movie> callbacks = DetailActivity.this;
            getSupportLoaderManager().initLoader(DETAIL_MOVIE, null, callbacks );
        }

    }

    private void inputReview (DetailVideoReviewMovie refDetailVideoReviewMovie) {
        if (refDetailVideoReviewMovie.isExistListReview()) {
            List<Review> reviewList =  refDetailVideoReviewMovie.getListReview();
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

    private void inputTrailer (DetailVideoReviewMovie refDetailVideoReviewMovie) {
        if (refDetailVideoReviewMovie.isExistListTrailerMovie()) {
            List<MovieTrailer> movieTrailerList = refDetailVideoReviewMovie.getListMovieTrailer();
            PopularMoviesTrailerAdapter adapter = new PopularMoviesTrailerAdapter(movieTrailerList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(),
                    LinearLayoutManager.HORIZONTAL, false);

            this.recycleViewTrailerPresentation.setLayoutManager(linearLayoutManager);
            this.recycleViewTrailerPresentation.setAdapter(adapter);
            this.recycleViewTrailerPresentation.setVisibility(View.VISIBLE);
        } else {
            //
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

        MovieTrailer movieTrailer = detailVideoReviewMovie.getListMovieTrailer().get(0);
        this.sharedMovieTrailerKey = movieTrailer.getKey();
        this.inputReview(detailVideoReviewMovie);
        this.inputTrailer(detailVideoReviewMovie);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_popular_movies_detail_app, menu);
        this.itemFav = menu.findItem(R.id.menu_item_fav);
        this.itemAddFav = menu.findItem(R.id.menu_item_add_fav);
        this.itemFav.setVisible(this.checkedMenuFav);
        this.itemAddFav.setVisible(this.checkedMenuAddFav);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int idItem = item.getItemId();
        final int idFavAction = R.id.menu_item_add_fav;
        final int idFav = R.id.menu_item_fav;
        final int idShare = R.id.menu_item_share;

        if (idItem == idFavAction) {
            item.setVisible(false);
            this.itemFav.setVisible(true);
        } else if (idItem == idFav) {
            item.setVisible(false);
            this.itemAddFav.setVisible(true);
        } else if (idItem == idShare) {
            Uri uriSharedMovieTrailerYoutube = PopularMoviesFormat.requestHtmlYoutubeVideo(this.getApplicationContext(),
                    this.sharedMovieTrailerKey);
            String strPathTrailerYoutube = uriSharedMovieTrailerYoutube.toString();
            Intent intentShared = new Intent();
            intentShared.setAction(Intent.ACTION_SEND);
            intentShared.putExtra(Intent.EXTRA_TEXT, strPathTrailerYoutube);
            intentShared.setType("text/plain");
            this.startActivity(intentShared);
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {

        VideoMovieReviewNetworkTask videoMovieReviewNetworkTask =
                new VideoMovieReviewNetworkTask(DetailActivity.this, this.refMovie,
                        this.parserData, this.networkRun, this.networkConfig, this);

        return videoMovieReviewNetworkTask;
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        final int POS_INI_X_SCROLL = 0;
        final int POS_INI_Y_SCROLL = 0;
        Movie detailMovie = data;
        RequestCreator requestCreatorObject = detailMovie.getRefRequesImg();

        this.progressBar.setVisibility(View.INVISIBLE);
        this.scrollView.setVisibility(View.VISIBLE);
        this.scrollView.scrollTo(POS_INI_X_SCROLL, POS_INI_Y_SCROLL);

        this.inputDataScreen(detailMovie);
        requestCreatorObject.into(this.imageBackDrop);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(this.id_state_itemMenuFav, this.itemFav.isVisible());
        outState.putBoolean(this.id_state_itemMenuAddFav, this.itemAddFav.isVisible());
        outState.putString(this.id_sharedMovie_trailerkey, this.sharedMovieTrailerKey);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean containsControlMenu =
                (savedInstanceState.containsKey(this.id_state_itemMenuFav) &&
                savedInstanceState.containsKey(this.id_state_itemMenuAddFav));

        if (containsControlMenu) {
            this.checkedMenuFav = savedInstanceState.getBoolean(this.id_state_itemMenuFav);
            this.checkedMenuAddFav = savedInstanceState.getBoolean(this.id_state_itemMenuAddFav);
        }

    }
}
