package br.com.nanodegree.pinablink.dataProvider.task;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import br.com.nanodegree.pinablink.dataProvider.PopularMoviesContract;
import br.com.nanodegree.pinablink.engine.network.task.AsyncTaskNetworkDelegator;

/**
 * Created by Pinablink on 28/05/2018.
 */
public class PopularMoviesFavDbTask extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;
    private Context mContext;
    private AsyncTaskNetworkDelegator referAsyncTask;

    public PopularMoviesFavDbTask(Context context, AsyncTaskNetworkDelegator pAsyncTaskNetworkDelegator) {
        super(context);
        this.mContext = context;
        this.referAsyncTask = pAsyncTaskNetworkDelegator;
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
    public Cursor loadInBackground() {

        Cursor cursorReturn;

        try {
            this.referAsyncTask.onInitProgressBar();
            ContentResolver contentResolver = mContext.getContentResolver();
            Uri uri = PopularMoviesContract.PopularMoviesEntry.CONTENT_URI;
            String str = uri.toString();
            cursorReturn = contentResolver.query(uri, null, null, null, null);
        } catch (Exception ex) {
            cursorReturn = null;
        }

        return cursorReturn;
    }

    @Override
    public void deliverResult(Cursor data) {
        this.mCursor = data;
        super.deliverResult(data);
    }
}
