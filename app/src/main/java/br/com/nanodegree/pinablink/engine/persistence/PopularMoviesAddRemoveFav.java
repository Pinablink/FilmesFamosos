package br.com.nanodegree.pinablink.engine.persistence;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.dataProvider.PopularMoviesContract;
import br.com.nanodegree.pinablink.engine.listener.PopularMoviesImgParser64On;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesBase64ImageExtractor;

/**
 * Created by Pinablink on 25/05/2018.
 */
public class PopularMoviesAddRemoveFav implements PopularMoviesImgParser64On{

    private Movie movie;
    private Context refContext;

    public PopularMoviesAddRemoveFav(Movie pMovie, Context pContext) {
        super();
        this.movie = pMovie;
        this.refContext = pContext;
    }

    public void insert () {
        PopularMoviesBase64ImageExtractor popularMoviesBitmapExtractor =
                new PopularMoviesBase64ImageExtractor(movie, this);
        //Transformar a Imagem obtida em Base64 para persistencia em base
        new Thread(popularMoviesBitmapExtractor).start();
        //
    }

    public void remove () {
        Uri uri = PopularMoviesContract.PopularMoviesEntry.CONTENT_URI;
        long id = this.movie.getId();
        uri = Uri.withAppendedPath(uri, String.valueOf(id));
        this.refContext.getContentResolver().delete(uri, null, null);
    }


    @Override
    public void onParser(String strBackDropImg64, String strPosterImg64) {
        Uri uri = PopularMoviesContract.PopularMoviesEntry.CONTENT_URI;
        ContentValues contentValue = new ContentValues();
        contentValue.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_DATA_ID, this.movie.getId());
        contentValue.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_OVERVIEW, this.movie.getOverview());
        contentValue.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_TITLE, this.movie.getTitle());
        contentValue.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_RELEASE_DATE, this.movie.getReleaseDate());
        contentValue.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_VOTE_AVERAGE, this.movie.getVoteAverage());
        contentValue.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_VOTE_COUNT, this.movie.getVoteCount());
        contentValue.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_POSTER_IMAGE, strPosterImg64);
        contentValue.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_BACK_DROP_IMAGE, strBackDropImg64);
        ContentResolver contentResolver = this.refContext.getContentResolver();
        Uri uriRet = contentResolver.insert(uri, contentValue);
        this.movie.setPosterImageBase64("");
        this.movie.setBackDropImageBase64("");
    }
}
