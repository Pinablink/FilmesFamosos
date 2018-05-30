package br.com.nanodegree.pinablink.dataProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Pinablink on 28/05/2018.
 */
public class PopularMoviesContract {

    public static final String AUTHORITY = "br.com.nanodegree.pinablink";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASKS = "tasks";


    public static final class PopularMoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().
                appendPath(PATH_TASKS).build();

        public static final String TABLE_NAME = "PopularMoviesFav";
        public static final String COLUMN_DATA_ID = "popm_id";
        public static final String COLUMN_POSTER_IMAGE = "popm_posterImage";
        public static final String COLUMN_BACK_DROP_IMAGE = "popm_backDropImage";
        public static final String COLUMN_TITLE = "popm_title";
        public static final String COLUMN_OVERVIEW = "popm_overview";
        public static final String COLUMN_RELEASE_DATE = "popm_releaseDate";
        public static final String COLUMN_VOTE_AVERAGE = "popm_voteAverage";
        public static final String COLUMN_VOTE_COUNT  = "popm_voteCount";
    }
}
