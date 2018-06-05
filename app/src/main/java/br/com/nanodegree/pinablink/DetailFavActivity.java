package br.com.nanodegree.pinablink;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.dataProvider.PopularMoviesContract;
import br.com.nanodegree.pinablink.dataProvider.task.PopularMoviesDetailDbTask;
import br.com.nanodegree.pinablink.engine.persistence.PopularMoviesAddRemoveFav;
import br.com.nanodegree.pinablink.engine.persistence.PopularMoviesStateFav;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesBase64ImageExtractor;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesMsg;

public class DetailFavActivity
        extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView txtvTitle;
    private TextView txtvSinopse;
    private TextView txtvVoteAverage;
    private TextView txtvReleaseDate;
    private ImageView imgBackDrop;
    private Movie refMovie;
    private String idRefMovie;
    private ScrollView scrollViewRefer;
    private ProgressBar progressBarRefer;
    private final String STATE_REF_MOVIE = "MOVIE_CURRENT";
    private static final int COD_DETAIL_FAV_TASK = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fav);
        ActionBar actionBar = getSupportActionBar();
        this.txtvTitle  = (TextView) this.findViewById(R.id.dfavMTitle);
        this.txtvSinopse = (TextView) this.findViewById(R.id.dfavMSinopse);
        this.txtvVoteAverage = (TextView) this.findViewById(R.id.dfavMVoteAverage);
        this.txtvReleaseDate = (TextView) this.findViewById(R.id.dfavMReleaseDate);
        this.imgBackDrop = (ImageView) this.findViewById(R.id.dfavBackDrop_image);
        this.scrollViewRefer = (ScrollView) this.findViewById(R.id.dfavscrollViewDetail);
        this.progressBarRefer = (ProgressBar) this.findViewById(R.id.dfavProgress);
        this.actionBarEnabledDisplayHome(actionBar);
    }

    private void actionBarEnabledDisplayHome (ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.refMovie = new Movie();

        if (this.idRefMovie == null) {
            Intent mIntent = this.getIntent();
            String keyPutExtra = this.getApplicationContext().
                    getString(R.string.name_movie_trans_activity);
            long idMovie = mIntent.getExtras().getLong(keyPutExtra);
            this.refMovie.setId(idMovie);
            this.idRefMovie = String.valueOf(idMovie);

            getSupportLoaderManager().initLoader(DetailFavActivity.COD_DETAIL_FAV_TASK, null,
                    DetailFavActivity.this);
        } else {
            this.refMovie.setId(Long.parseLong(this.idRefMovie));
            getSupportLoaderManager().restartLoader(DetailFavActivity.COD_DETAIL_FAV_TASK, null,
                    DetailFavActivity.this);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_REF_MOVIE, this.idRefMovie);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.idRefMovie = savedInstanceState.getString(STATE_REF_MOVIE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_popular_movies_detail_fav_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = this.getApplicationContext();
        int idSelected = item.getItemId();
        int idMenuItem = R.id.menu_item_detail_fav;
        String msg = context.getString(R.string.app_remove_favorite);

        if (idSelected == idMenuItem) {
            PopularMoviesStateFav.removeFav(context, this.refMovie);
            PopularMoviesAddRemoveFav popularMoviesRemoveFav =
                    new PopularMoviesAddRemoveFav(this.refMovie, context);
            popularMoviesRemoveFav.remove();
            new PopularMoviesMsg().showToastMsgInfo(msg, context);
            Intent intentReturn = new Intent(context, FavoriteActivity.class);
            startActivity(intentReturn);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        PopularMoviesDetailDbTask popularMoviesDetailDbTask =
                new PopularMoviesDetailDbTask(this.getApplicationContext(), this.refMovie);

        return popularMoviesDetailDbTask;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        while (data.moveToNext()) {
            final int columnIdIndex =
                    data.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_DATA_ID);
            final int columnTitleIndex  =
                    data.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_TITLE);
            final int columnOverviewIndex =
                    data.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_OVERVIEW);
            final int columnVoteAverageIndex =
                    data.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_VOTE_AVERAGE);
            final int columnBackDropImageIndex =
                    data.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_BACK_DROP_IMAGE);
            final int columnReleaseDateIndex =
                    data.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_RELEASE_DATE);

            final String movieTitle = data.getString(columnTitleIndex);
            final String movieOverview = data.getString(columnOverviewIndex);
            final String movieVoteAverage = data.getString(columnVoteAverageIndex);
            final String movieReleaseDate = data.getString(columnReleaseDateIndex);
            final String movieBackDropImg64 = data.getString(columnBackDropImageIndex);

            Bitmap bitmap = PopularMoviesBase64ImageExtractor.getBitmapStrEncoded(movieBackDropImg64);

            this.txtvTitle.setText(movieTitle );
            this.txtvSinopse.setText(movieOverview);
            this.txtvVoteAverage.setText(movieVoteAverage);
            this.txtvReleaseDate.setText(movieReleaseDate);
            this.imgBackDrop.setImageBitmap(bitmap);

            this.scrollViewRefer.setVisibility(View.VISIBLE);
            this.progressBarRefer.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
