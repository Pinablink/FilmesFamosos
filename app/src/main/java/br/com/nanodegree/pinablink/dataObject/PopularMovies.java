package br.com.nanodegree.pinablink.dataObject;


import android.os.Parcelable;

import java.util.List;

import br.com.nanodegree.pinablink.engine.annotation.Param;
import br.com.nanodegree.pinablink.engine.annotation.ParamInJson;

/**
 * Created by Pinablink on 13/04/2018.
 */
public final class PopularMovies {

    private int page;
    private long totalResults;
    private int totalPages;
    private List<Movie> listMovie;

    public PopularMovies() {
        super();
    }

    public int getPage() {
        return page;
    }

    @ParamInJson(name = "page", valueType = Param.VALUE_TYPE_INT)
    public void setPage(int page) {
        this.page = page;
    }

    public long getTotalResults() {
        return totalResults;
    }


    @ParamInJson(name = "total_results", valueType = Param.VALUE_TYPE_LONG)
    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }


    @ParamInJson(name = "total_pages", valueType = Param.VALUE_TYPE_INT)
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getListMovie() {
        return listMovie;
    }

    @ParamInJson(name = "results", isArrayData = true)
    public void setListMovie(List<Movie> listMovie) {
        this.listMovie = listMovie;
    }

}
