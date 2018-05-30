package br.com.nanodegree.pinablink.dataProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pinablink on 28/05/2018.
 */
public class PopularMoviesDBHelper extends SQLiteOpenHelper{


    private static final String DB_NAME = "popularMovies.db";
    private static final int VERSION = 1;

    public PopularMoviesDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String STR_DEF_ID = " INTEGER PRIMARY KEY";
        final String STR_TEXT_NOT_NULL = " TEXT NOT NULL ";
        String createTable = "CREATE TABLE %s(%s%s,%s%s,%s%s,%s%s,%s%s,%s%s,%s%s,%s%s)";
        createTable = String.format(createTable, PopularMoviesContract.PopularMoviesEntry.TABLE_NAME,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_DATA_ID, STR_DEF_ID,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_POSTER_IMAGE, STR_TEXT_NOT_NULL,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_BACK_DROP_IMAGE, STR_TEXT_NOT_NULL,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_TITLE, STR_TEXT_NOT_NULL,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_OVERVIEW, STR_TEXT_NOT_NULL,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_RELEASE_DATE, STR_TEXT_NOT_NULL,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_VOTE_AVERAGE, STR_TEXT_NOT_NULL,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_VOTE_COUNT, STR_TEXT_NOT_NULL);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PopularMoviesContract.PopularMoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
