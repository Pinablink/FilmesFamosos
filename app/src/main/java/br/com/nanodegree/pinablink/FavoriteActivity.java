package br.com.nanodegree.pinablink;


import android.database.Cursor;
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
    private PopularMoviesPosterFavAdapter popularMoviesPosterFavAdapter;
    private ProgressBar mfavProgressBar;
    private List<Movie> listMovie;
    private boolean activityRestore;
    private static final String RESTORE_FAVORITE_ACTIVITY = "Restore";
    private TextView textMsgEmptyFavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        this.strTitle = this.getString(R.string.title_activity_main_favorite);
        initResourceScreen();
        this.setTitle(this.strTitle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoaderCallbacks<Cursor> callBack = FavoriteActivity.this;
        if (this.activityRestore) {
            this.getSupportLoaderManager().restartLoader(FavoriteActivity.TASK_QUERY_DB, null, callBack);
        } else {
            this.getSupportLoaderManager().initLoader(FavoriteActivity.TASK_QUERY_DB, null, callBack);
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

        boolean boolRestore = savedInstanceState.getBoolean(RESTORE_FAVORITE_ACTIVITY);
        if (boolRestore) {
            this.activityRestore = true;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RESTORE_FAVORITE_ACTIVITY, true);
    }

    @Override
    protected void initResourceScreen() {
        this.recycleViewFavPosterPresentation = (RecyclerView) findViewById(R.id.recyclerview_fav_presentation_movies);
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
            this.popularMoviesPosterFavAdapter = new PopularMoviesPosterFavAdapter(FavoriteActivity.this);
            this.popularMoviesPosterFavAdapter.setListMovie(listMovie);
            this.mfavProgressBar.setVisibility(View.INVISIBLE);
            this.textMsgEmptyFavView.setVisibility(View.INVISIBLE);
            GridLayoutManager layoutManager = new GridLayoutManager(FavoriteActivity.this, 2);
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
            final int column_index_backDropImg64 =
                    dataCursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_BACK_DROP_IMAGE);
            final int column_index_overview =
                    dataCursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_OVERVIEW);
            final int column_index_releaseDate =
                    dataCursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_RELEASE_DATE);
            final int column_index_title =
                    dataCursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_TITLE);
            final int column_index_voteAverage =
                    dataCursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_VOTE_AVERAGE);
            final int column_index_voteCount =
                    dataCursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_VOTE_COUNT);

            final long idDB = dataCursor.getLong(column_index_id);
            final String strPosterImg64 = dataCursor.getString(column_index_posterImg64);
            final String strBackDropImg64 = dataCursor.getString(column_index_backDropImg64);
            final String strOverview = dataCursor.getString(column_index_overview);
            final String strReleaseData = dataCursor.getString(column_index_releaseDate);
            final String strTitle = dataCursor.getString(column_index_title);
            final String strVoteAverage = dataCursor.getString(column_index_voteAverage);
            final String strVoteCount = dataCursor.getString(column_index_voteCount);

            Movie movie = new Movie ();
            movie.setId(idDB);
            movie.setTitle(strTitle);
            movie.setPosterImageBase64(strPosterImg64);
            movie.setBackDropImageBase64(strBackDropImg64);
            movie.setOverview(strOverview);
            movie.setReleaseDate(strReleaseData);
            movie.setVoteAverage(strVoteAverage);
            movie.setVoteCount(strVoteCount);

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
