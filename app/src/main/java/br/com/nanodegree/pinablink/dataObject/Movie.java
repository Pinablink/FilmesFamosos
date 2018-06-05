package br.com.nanodegree.pinablink.dataObject;


import android.os.Parcel;
import android.os.Parcelable;
import com.squareup.picasso.RequestCreator;
import br.com.nanodegree.pinablink.engine.annotation.Param;
import br.com.nanodegree.pinablink.engine.annotation.ParamInJson;

/**
 * Created by Pinablink on 13/04/2018.
 */
public final class Movie implements Parcelable {

    private String voteCount;
    private long id;
    private boolean video;
    private String voteAverage;
    private String title;
    private String popularity;
    private String posterPath;
    private String originalLanguage;
    private String orginalTitle;
    private String backdropPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private RequestCreator refRequesBackDropImg;
    private RequestCreator refRequestPosterImg;
    private DetailVideoReviewMovie detailVideoReviewMovie;
    private String posterImageBase64;
    private String backDropImageBase64;

    /**
     *
     */
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {
        super();
    }

    protected Movie(Parcel in) {
        voteCount = in.readString();
        id = in.readLong();
        video = in.readByte() != 0;
        voteAverage = in.readString();
        title = in.readString();
        popularity = in.readString();
        posterPath = in.readString();
        originalLanguage = in.readString();
        orginalTitle = in.readString();
        backdropPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
    }

    public String getVoteCount() {
        return voteCount;
    }

    @ParamInJson(name = "vote_count")
    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public long getId() {
        return id;
    }

    @ParamInJson(name = "id", valueType = Param.VALUE_TYPE_LONG)
    public void setId(long id) {
        this.id = id;
    }

    @ParamInJson(name = "video", valueType = Param.VALUE_TYPE_BOOLEAN)
    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    @ParamInJson(name = "vote_average")
    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    @ParamInJson(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    @ParamInJson(name = "popularity")
    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    @ParamInJson(name = "poster_path", concatLeft = "http://image.tmdb.org/t/p/w185")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    @ParamInJson(name = "original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOrginalTitle() {
        return orginalTitle;
    }

    @ParamInJson(name = "original_title")
    public void setOrginalTitle(String orginalTitle) {
        this.orginalTitle = orginalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    @ParamInJson(name = "backdrop_path", concatLeft = "http://image.tmdb.org/t/p/w185")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    @ParamInJson(name = "adult", valueType = Param.VALUE_TYPE_BOOLEAN)
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    @ParamInJson(name = "overview")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @ParamInJson(name = "release_date")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isVideo() {
        return video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public DetailVideoReviewMovie getDetailVideoReviewMovie() {
        return detailVideoReviewMovie;
    }

    public void setDetailVideoReviewMovie(DetailVideoReviewMovie detailVideoReviewMovie) {
        this.detailVideoReviewMovie = detailVideoReviewMovie;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(voteCount);
        dest.writeLong(id);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(voteAverage);
        dest.writeString(title);
        dest.writeString(popularity);
        dest.writeString(posterPath);
        dest.writeString(originalLanguage);
        dest.writeString(orginalTitle);
        dest.writeString(backdropPath);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    public String getPosterImageBase64() {
        return posterImageBase64;
    }

    public void setPosterImageBase64(String posterImageBase64) {
        this.posterImageBase64 = posterImageBase64;
    }

    public String getBackDropImageBase64() {
        return backDropImageBase64;
    }

    public void setBackDropImageBase64(String backDropImageBase64) {
        this.backDropImageBase64 = backDropImageBase64;
    }

    public RequestCreator getRefRequesBackDropImg() {
        return refRequesBackDropImg;
    }

    public void setRefRequesBackDropImg(RequestCreator refRequesBackDropImg) {
        this.refRequesBackDropImg = refRequesBackDropImg;
    }

    public RequestCreator getRefRequestPosterImg() {
        return refRequestPosterImg;
    }

    public void setRefRequestPosterImg(RequestCreator refRequestPosterImg) {
        this.refRequestPosterImg = refRequestPosterImg;
    }
}
