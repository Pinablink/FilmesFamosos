package br.com.nanodegree.pinablink.engine.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;
import br.com.nanodegree.pinablink.R;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.engine.holder.PopularMoviesPosterFavAdapterViewHolder;
import br.com.nanodegree.pinablink.engine.listener.PopularMoviesFavPosterOnClick;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesBase64ImageExtractor;

/**
 * Created by Pinablink on 28/05/2018.
 */
public class PopularMoviesPosterFavAdapter
        extends RecyclerView.Adapter<PopularMoviesPosterFavAdapterViewHolder> {

    private List<Movie> refListMovie;
    private Context refContext;

    public PopularMoviesPosterFavAdapter (Context context) {
        super();
        this.refContext = context;
    }

    @Override
    public PopularMoviesPosterFavAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int idLayout = R.layout.fav_poster_view;
        Context refContext = parent.getContext();
        View mView = LayoutInflater.from(refContext).inflate(idLayout, parent, false);

        return new PopularMoviesPosterFavAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(PopularMoviesPosterFavAdapterViewHolder holder, int position) {
        ImageView imageView = holder.getImageView();
        Movie movie = this.refListMovie.get(position);
        PopularMoviesFavPosterOnClick eventClick = new PopularMoviesFavPosterOnClick(movie, this.refContext);
        imageView.setOnClickListener(eventClick);
        String strImgBase64 = movie.getPosterImageBase64();
        Bitmap bitMap = PopularMoviesBase64ImageExtractor.getBitmapStrEncoded(strImgBase64);
        imageView.setImageBitmap(bitMap);
    }

    @Override
    public int getItemCount() {
        return (this.refListMovie != null ? this.refListMovie.size() : 0);
    }

    public void setListMovie (List<Movie> pListMovie) {
        this.refListMovie = pListMovie;
    }


}
