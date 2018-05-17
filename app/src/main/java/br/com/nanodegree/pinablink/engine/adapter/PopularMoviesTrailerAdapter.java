package br.com.nanodegree.pinablink.engine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.RequestCreator;

import java.util.List;
import br.com.nanodegree.pinablink.R;
import br.com.nanodegree.pinablink.dataObject.MovieTrailer;
import br.com.nanodegree.pinablink.engine.holder.PopularMoviesTrailerAdapterViewHolder;
import br.com.nanodegree.pinablink.engine.listener.PopularMoviesTrailerOnClick;

/**
 * Created by Pinablink on 16/05/2018.
 */
public class PopularMoviesTrailerAdapter extends
        RecyclerView.Adapter<PopularMoviesTrailerAdapterViewHolder> {


    private List<MovieTrailer> listMovieTrailer;
    private Context refContext;


    public PopularMoviesTrailerAdapter (List<MovieTrailer> pListMovieTrailer) {
        this.listMovieTrailer = pListMovieTrailer;
    }

    @Override
    public PopularMoviesTrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.refContext =  parent.getContext();
        int idLayout = R.layout.trailer_movie;
        LayoutInflater layoutInflater = LayoutInflater.from (this.refContext);
        View viewReturn = layoutInflater.inflate(idLayout, parent, false);
        return new PopularMoviesTrailerAdapterViewHolder(viewReturn);
    }

    @Override
    public void onBindViewHolder(PopularMoviesTrailerAdapterViewHolder holder, int position) {
        ImageView imageThumbnailView = holder.getImageViewThumbnail();
        MovieTrailer movieTrailer = this.listMovieTrailer.get(position);

        PopularMoviesTrailerOnClick popularMoviesTrailerOnClick =
                new PopularMoviesTrailerOnClick(movieTrailer);

        imageThumbnailView.setOnClickListener(popularMoviesTrailerOnClick);

        RequestCreator requestCreatorRef = movieTrailer.getRefRequestImg();
        requestCreatorRef.into(imageThumbnailView);
    }

    @Override
    public int getItemCount() {
        return (this.listMovieTrailer != null ? this.listMovieTrailer.size() : 0);
    }
}
