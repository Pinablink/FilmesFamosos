package br.com.nanodegree.pinablink.dataProvider.task;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.dataProvider.PopularMoviesContract;

/**
 * Created by Pinablink on 02/06/2018.
 */
public class PopularMoviesDetailDbTask extends AsyncTaskLoader<Cursor> {

    private Context mContext;
    private Movie mMovie;
    private Cursor mCursor;

    public PopularMoviesDetailDbTask(Context context, Movie movie) {
        super(context);
        this.mContext = context;
        this.mMovie = movie;
    }

    @Override
    public Cursor loadInBackground() {

        Cursor cursorReturn;

        try {

            ContentResolver contentResolver = mContext.getContentResolver();
            Uri uri = PopularMoviesContract.PopularMoviesEntry.CONTENT_URI;
            long id = this.mMovie.getId();
            String strId = String.valueOf(id);
            String SELECT_CLAUSULE = PopularMoviesContract.PopularMoviesEntry.COLUMN_DATA_ID + " = ?";

            cursorReturn = contentResolver.query(uri, null,
                    SELECT_CLAUSULE, new String[]{strId}, null);


        } catch (Exception ex) {
            cursorReturn = null;
        }

        return cursorReturn;
    }

    @Override
    protected void onStartLoading() {
        if (this.mCursor != null) {
            deliverResult(this.mCursor);
        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(Cursor data) {
        this.mCursor = data;
        super.deliverResult(data);
    }
}
