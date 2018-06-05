package br.com.nanodegree.pinablink.dataProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Pinablink on 28/05/2018.
 */
public class PopularMoviesContentProvider extends ContentProvider{

    public static final int TASK = 100;
    public static final int TASK_WITH_ID = 101;
    private PopularMoviesDBHelper popularMoviesDbHelper;
    private static final UriMatcher refUriMatcher = getUriMatcher();

    static UriMatcher getUriMatcher () {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PopularMoviesContract.AUTHORITY, PopularMoviesContract.PATH_TASKS, TASK);
        uriMatcher.addURI(PopularMoviesContract.AUTHORITY, PopularMoviesContract.PATH_TASKS + "/#", TASK_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = this.getContext();
        popularMoviesDbHelper = new PopularMoviesDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        Cursor retCursor;
        SQLiteDatabase sqLiteDb = this.popularMoviesDbHelper.getReadableDatabase();
        int iMatch = refUriMatcher.match(uri);

        switch (iMatch) {
            case TASK:
                retCursor = sqLiteDb.query(PopularMoviesContract.PopularMoviesEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default: throw new UnsupportedOperationException("NOT URI: " + uri);
        }

        if (retCursor.getCount() > 0) {
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Uri retUri = null;
        SQLiteDatabase sqLiteDb = this.popularMoviesDbHelper.getReadableDatabase();
        int iMatch = refUriMatcher.match(uri);

        switch (iMatch) {
            case TASK:

                long idRet = sqLiteDb.insert(PopularMoviesContract.PopularMoviesEntry.TABLE_NAME, null, values);

                if (idRet > 0) {
                    retUri = ContentUris.withAppendedId(PopularMoviesContract.PopularMoviesEntry.CONTENT_URI, idRet);
                } else {
                    throw new android.database.SQLException("FAILED : Not Insert " + uri);
                }
                break;

            default: throw new UnsupportedOperationException("NOT URI: " + uri);
        }


        getContext().getContentResolver().notifyChange(uri, null);

        return retUri;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase sqLiteDb = this.popularMoviesDbHelper.getReadableDatabase();
        int iMatch = refUriMatcher.match(uri);
        int iDeleted;

        switch (iMatch) {
            case TASK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                iDeleted =  sqLiteDb.delete(PopularMoviesContract.PopularMoviesEntry.TABLE_NAME,
                        PopularMoviesContract.PopularMoviesEntry.COLUMN_DATA_ID + "=?",
                        new String[]{id});
            break;

            default:
                throw new UnsupportedOperationException("NOT URI: " + uri);
        }

        if (iDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return iDeleted;
    }


    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        //SEM OPERAÇÃO
        return 0;
    }
}
