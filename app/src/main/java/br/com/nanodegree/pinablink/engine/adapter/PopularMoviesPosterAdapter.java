package br.com.nanodegree.pinablink.engine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.RequestCreator;
import br.com.nanodegree.pinablink.R;
import java.util.List;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.engine.holder.PopularMoviesPosterAdapterViewHolder;
import br.com.nanodegree.pinablink.engine.listener.PopularMoviesPosterOnClick;
import br.com.nanodegree.pinablink.engine.listener.popularMoviesInterface.PopularMoviesConvertImageBase64On;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesBase64ImageExtractor;

/**
 * Created by Pinablink on 16/04/2018.
 */
public class PopularMoviesPosterAdapter
        extends RecyclerView.Adapter<PopularMoviesPosterAdapterViewHolder>
        implements PopularMoviesConvertImageBase64On {

    private List<Movie> refListMovie;
    private Context refContext;



    public PopularMoviesPosterAdapter() {
        super();

    }

    public void setListMovie (List<Movie> pRefListMovie) {
        this.refListMovie = pRefListMovie;
    }

    @Override
    public PopularMoviesPosterAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.refContext = parent.getContext();
        int idLayoutHolder = R.layout.poster_view;
        LayoutInflater layoutInflater = LayoutInflater.from(this.refContext);
        View view = layoutInflater.inflate(idLayoutHolder, parent, false);
        return new PopularMoviesPosterAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopularMoviesPosterAdapterViewHolder holder, int position) {
        ImageView imageView = holder.getImageView();
        Movie movie = refListMovie.get(position);
        PopularMoviesPosterOnClick popularMoviesEvent = new PopularMoviesPosterOnClick(movie, this.refContext);

        imageView.setOnClickListener(popularMoviesEvent);
        RequestCreator requestCreator = movie.getRefRequesImg();
        PopularMoviesBase64ImageExtractor popularMoviesBitmapExtractor =
                new PopularMoviesBase64ImageExtractor(movie, this);
        //Transformar a Imagem obtida em Base64 para transporte e persistencia em base
        new Thread(popularMoviesBitmapExtractor).start();
        //
        requestCreator.into(holder.getImageView());
    }


    @Override
    public int getItemCount() {
        return refListMovie != null ? refListMovie.size() : 0;
    }

    @Override
    public void onConvertImageBase64(Movie movie, String strImageBase64) {
        movie.setPosterImageBase64(strImageBase64);
    }
}
