package br.com.nanodegree.pinablink.engine.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.squareup.picasso.RequestCreator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.engine.listener.popularMoviesInterface.PopularMoviesConvertImageBase64On;


/**
 * Created by Pinablink on 25/05/2018.
 */
public class PopularMoviesBase64ImageExtractor implements Runnable {

    private Movie refMovie;
    private PopularMoviesConvertImageBase64On refPopularMoviesConvertImageBase64On;

    public PopularMoviesBase64ImageExtractor(Movie movie,
                                             PopularMoviesConvertImageBase64On pPopularMoviesConvertImageBase64On ) {
        this.refMovie = movie;
        this.refPopularMoviesConvertImageBase64On = pPopularMoviesConvertImageBase64On;
    }


    public static Bitmap getBitmapStrEncoded (String str64Coded) {
        byte[] decString = Base64.decode(str64Coded, Base64.NO_WRAP);
        Bitmap bitMap = BitmapFactory.decodeByteArray(decString, 0, decString.length);

        return bitMap;
    }

    @Override
    public void run() {

        Bitmap bitmap;
        String imageBase64;

        try {

            RequestCreator requestCreator = this.refMovie.getRefRequesImg();
            bitmap = requestCreator.get();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] byteArrayBitmap = baos.toByteArray();
            imageBase64 = Base64.encodeToString(byteArrayBitmap, Base64.DEFAULT);

        } catch (IOException e) {
            imageBase64 = "";
        }

        this.refPopularMoviesConvertImageBase64On.onConvertImageBase64(this.refMovie, imageBase64);
    }
}
