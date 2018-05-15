package br.com.nanodegree.pinablink.dataObject;

import java.util.List;

/**
 * Created by Pinablink on 12/05/2018.
 */
public class DetailVideoReviewMovie {

    private boolean existListReview;
    private boolean existListTrailerMovie;
    private List<Review> listReview;
    private List<MovieTrailer> listMovieTrailer;

    public List<Review> getListReview() {
        return listReview;
    }

    public void setListReview(List<Review> listReview) {
        this.listReview = listReview;
    }

    public boolean isExistListReview() {
        return existListReview;
    }

    public void setExistListReview(boolean existListReview) {
        this.existListReview = existListReview;
    }

    public List<MovieTrailer> getListMovieTrailer() {
        return listMovieTrailer;
    }

    public void setListMovieTrailer(List<MovieTrailer> listMovieTrailer) {
        this.listMovieTrailer = listMovieTrailer;
    }

    public boolean isExistListTrailerMovie() {
        return existListTrailerMovie;
    }

    public void setExistListTrailerMovie(boolean existListTrailerMovie) {
        this.existListTrailerMovie = existListTrailerMovie;
    }
}
