package br.com.nanodegree.pinablink;


import android.database.Cursor;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.dataProvider.PopularMoviesContract;
import br.com.nanodegree.pinablink.dataProvider.task.PopularMoviesFavDbTask;
import br.com.nanodegree.pinablink.engine.adapter.PopularMoviesPosterFavAdapter;
import br.com.nanodegree.pinablink.engine.network.task.ActivityFilmesFamosos;
import br.com.nanodegree.pinablink.engine.network.task.AsyncTaskNetworkDelegator;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends ActivityFilmesFamosos
        implements LoaderManager.LoaderCallbacks<Cursor>, AsyncTaskNetworkDelegator {

    private String strTitle;
    private static final int TASK_QUERY_DB = 3;
    private RecyclerView recycleViewFavPosterPresentation;
    private GridLayoutManager layoutManager;
    private PopularMoviesPosterFavAdapter popularMoviesPosterFavAdapter;
    private ProgressBar mfavProgressBar;
    private List<Movie> listMovie;
    private TextView textMsgEmptyFavView;
    private static final String KEY_LIST_MOVIE_RESTORE = "keyListMovie";
    private static final String KEY_PARCELABLE_LAYOUT_MANAGER_RESTORE = "keyParcelableLayoutRestore";
    private Parcelable parcelableState;
    private boolean isDataLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ActionBar actionBar = getSupportActionBar();
        this.strTitle = this.getString(R.string.title_activity_main_favorite);
        initResourceScreen();
        this.setTitle(this.strTitle);
        actionBarEnabledDisplayHome(actionBar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!this.isDataLoaded) {
            LoaderCallbacks<Cursor> callBack = FavoriteActivity.this;
            if (this.listMovie == null
                    || this.listMovie.size() == 0) {
                this.getSupportLoaderManager().initLoader(FavoriteActivity.TASK_QUERY_DB, null, callBack);
            } else {
                this.popularMoviesPosterFavAdapter.setListMovie(this.listMovie);
                this.layoutManager.onRestoreInstanceState(this.parcelableState);
                this.textMsgEmptyFavView.setVisibility(View.INVISIBLE);
                this.recycleViewFavPosterPresentation.setLayoutManager(layoutManager);
                this.recycleViewFavPosterPresentation.setAdapter(this.popularMoviesPosterFavAdapter);
                this.recycleViewFavPosterPresentation.setVisibility(View.VISIBLE);
            }
        }


    }

    private void actionBarEnabledDisplayHome (ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.listMovie = savedInstanceState.getParcelableArrayList(KEY_LIST_MOVIE_RESTORE);
        this.parcelableState = savedInstanceState.getParcelable(KEY_PARCELABLE_LAYOUT_MANAGER_RESTORE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_LIST_MOVIE_RESTORE,
                (ArrayList<? extends Parcelable>) this.listMovie);
        this.parcelableState = this.layoutManager.onSaveInstanceState();
        outState.putParcelable(KEY_PARCELABLE_LAYOUT_MANAGER_RESTORE, this.parcelableState);
    }

    @Override
    protected void initResourceScreen() {
        this.recycleViewFavPosterPresentation = (RecyclerView) findViewById(R.id.recyclerview_fav_presentation_movies);
        this.layoutManager = new GridLayoutManager(FavoriteActivity.this, 2);
        this.popularMoviesPosterFavAdapter = new PopularMoviesPosterFavAdapter(FavoriteActivity.this);
        this.mfavProgressBar = (ProgressBar) findViewById(R.id.mFavProgress);
        this.textMsgEmptyFavView = (TextView) findViewById(R.id.txt_msg_fav_empty);
    }

    @Override
    protected void loadUrl() {
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        PopularMoviesFavDbTask popularMoviesFavDbTask = new PopularMoviesFavDbTask (FavoriteActivity.this, this);
        return popularMoviesFavDbTask;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.listMovie = this.listMovieQueryResult(data);
        int countItensListMovie = this.listMovie.size();

        if (countItensListMovie == 0) {
            this.mfavProgressBar.setVisibility(View.INVISIBLE);
            this.textMsgEmptyFavView.setVisibility(View.VISIBLE);
        } else {
            this.isDataLoaded = true;
            this.popularMoviesPosterFavAdapter.setListMovie(listMovie);
            this.mfavProgressBar.setVisibility(View.INVISIBLE);
            this.textMsgEmptyFavView.setVisibility(View.INVISIBLE);
            this.recycleViewFavPosterPresentation.setLayoutManager(layoutManager);
            this.recycleViewFavPosterPresentation.setAdapter(this.popularMoviesPosterFavAdapter);
            this.recycleViewFavPosterPresentation.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private List<Movie> listMovieQueryResult (Cursor dataCursor) {
        List<Movie> listMovie = new ArrayList<Movie>();

        while (dataCursor.moveToNext()) {
            final int column_index_id =
                    dataCursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_DATA_ID);
            final int column_index_posterImg64 =
                    dataCursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_POSTER_IMAGE);

            final long idDB = dataCursor.getLong(column_index_id);
            final String strPosterImg64 = dataCursor.getString(column_index_posterImg64);

            Movie movie = new Movie ();
            movie.setId(idDB);
            movie.setPosterImageBase64(strPosterImg64);

            listMovie.add(movie);
        }

        return listMovie;
    }

    @Override
    public void onSearchImages(Object pPopularMovies) {
    }

    @Override
    public void onInitProgressBar() {
        this.mfavProgressBar.setVisibility(View.VISIBLE);
    }
}
