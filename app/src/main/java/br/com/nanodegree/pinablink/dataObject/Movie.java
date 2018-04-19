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

    /**
     *
     */
    private String voteCount;
    /**
     *
     */
    private String id;
    /**
     *
     */
    private boolean video;
    /**
     *
     */
    private String voteAverage;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String popularity;
    /**
     *
     */
    private String posterPath;
    /**
     *
     */
    private String originalLanguage;
    /**
     *
     */
    private String orginalTitle;
    /**
     *
     */
    private String backdropPath;
    /**
     *
     */
    private boolean adult;
    /**
     *
     */
    private String overview;
    /**
     *
     */
    private String releaseDate;

    /**
     *
     */
    private RequestCreator refRequesImg;

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

    /**
     *
     */
    public Movie() {
        super();
    }

    /**
     *
     * @param in
     */
    protected Movie(Parcel in) {
        voteCount = in.readString();
        id = in.readString();
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


    /**
     * @return
     */
    public String getVoteCount() {
        return voteCount;
    }

    /**
     * @param voteCount
     */
    @ParamInJson(name = "vote_count")
    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    @ParamInJson(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param video
     */
    @ParamInJson(name = "video", valueType = Param.VALUE_TYPE_BOOLEAN)
    public void setVideo(boolean video) {
        this.video = video;
    }

    /**
     * @return
     */
    public String getVoteAverage() {
        return voteAverage;
    }

    /**
     * @param voteAverage
     */
    @ParamInJson(name = "vote_average")
    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    @ParamInJson(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public String getPopularity() {
        return popularity;
    }

    /**
     * @param popularity
     */
    @ParamInJson(name = "popularity")
    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    /**
     * @return
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * @param posterPath
     */
    @ParamInJson(name = "poster_path", concatLeft = "http://image.tmdb.org/t/p/w185")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * @return
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * @param originalLanguage
     */
    @ParamInJson(name = "original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * @return
     */
    public String getOrginalTitle() {
        return orginalTitle;
    }

    /**
     * @param orginalTitle
     */
    @ParamInJson(name = "original_title")
    public void setOrginalTitle(String orginalTitle) {
        this.orginalTitle = orginalTitle;
    }

    /**
     * @return
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * @param backdropPath
     */
    @ParamInJson(name = "backdrop_path", concatLeft = "http://image.tmdb.org/t/p/w185")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     * @return
     */
    public boolean isAdult() {
        return adult;
    }

    /**
     * @param adult
     */
    @ParamInJson(name = "adult", valueType = Param.VALUE_TYPE_BOOLEAN)
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    /**
     * @return
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview
     */
    @ParamInJson(name = "overview")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate
     */
    @ParamInJson(name = "release_date")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     *
     * @return
     */
    public boolean isVideo() {
        return video;
    }

    /**
     *
     * @return
     */
    public RequestCreator getRefRequesImg() {
        return refRequesImg;
    }

    /**
     *
     * @param refRequesImg
     */
    public void setRefRequesImg(RequestCreator refRequesImg) {
        this.refRequesImg = refRequesImg;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(voteCount);
        dest.writeString(id);
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
}
