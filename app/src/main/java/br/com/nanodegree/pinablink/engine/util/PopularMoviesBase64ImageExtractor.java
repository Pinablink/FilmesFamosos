package br.com.nanodegree.pinablink.engine.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.squareup.picasso.RequestCreator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.engine.listener.PopularMoviesImgParser64On;


/**
 * Created by Pinablink on 25/05/2018.
 */
public class PopularMoviesBase64ImageExtractor implements Runnable {

    private Movie refMovie;
    private PopularMoviesImgParser64On popularParserOnRef;

    public PopularMoviesBase64ImageExtractor(Movie movie,
                                             PopularMoviesImgParser64On pPopularParserOnRef) {
        this.refMovie = movie;
        this.popularParserOnRef = pPopularParserOnRef;
    }


    public static Bitmap getBitmapStrEncoded (String str64Coded) {
        byte[] decString = Base64.decode(str64Coded, Base64.NO_WRAP);
        Bitmap bitMap = BitmapFactory.decodeByteArray(decString, 0, decString.length);

        return bitMap;
    }

    @Override
    public void run() {

        Bitmap bitmapBackDrop;
        Bitmap bitmapPoster;
        String imageBackDropBase64 = null;
        String imagePosterBase64 = null;

        try {

            RequestCreator requestBackDropCreator = this.refMovie.getRefRequesBackDropImg();
            bitmapBackDrop = requestBackDropCreator.get();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapBackDrop.compress(Bitmap.CompressFormat.PNG, 100, baos);

            byte[] byteArrayBitmap = baos.toByteArray();
            imageBackDropBase64 = Base64.encodeToString(byteArrayBitmap, Base64.DEFAULT);

            RequestCreator requestPosterCreator = this.refMovie.getRefRequestPosterImg();
            bitmapPoster = requestPosterCreator.get();

            baos = new ByteArrayOutputStream();
            bitmapPoster.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byteArrayBitmap = baos.toByteArray();
            imagePosterBase64 = Base64.encodeToString(byteArrayBitmap, Base64.DEFAULT);

        } catch (IOException e) {
            imageBackDropBase64 = "";
            imagePosterBase64 = "";
        } finally {
           this.popularParserOnRef.onParser(imageBackDropBase64, imagePosterBase64);
        }

    }
}
