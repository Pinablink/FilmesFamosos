package br.com.nanodegree.pinablink.dataObject;


import java.util.List;

import br.com.nanodegree.pinablink.engine.annotation.Param;
import br.com.nanodegree.pinablink.engine.annotation.ParamInJson;

/**
 * Created by Pinablink on 13/04/2018.
 */
public final class PopularMovies {
    /**
     *
     */
    private int page;
    /**
     *
     */
    private long totalResults;
    /**
     *
     */
    private int totalPages;

    /**
     *
     */
    private List<Movie> listMovie;

    /**
     *
     */
    private transient PopularMoviesError pError;

    /**
     * Conterá os objetos que representa um filme famoso
     */
    public PopularMovies() {
        super();
    }

    /**
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page
     */
    @ParamInJson(name = "page", valueType = Param.VALUE_TYPE_INT)
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return
     */
    public long getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults
     */
    @ParamInJson(name = "total_results", valueType = Param.VALUE_TYPE_LONG)
    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * @return
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages
     */
    @ParamInJson(name = "total_pages", valueType = Param.VALUE_TYPE_INT)
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * @return
     */
    public List<Movie> getListMovie() {
        return listMovie;
    }

    /**
     * @param listMovie
     */
    @ParamInJson(name = "results", isArrayData = true)
    public void setListMovie(List<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    /**
     *
     * @return
     */
    public PopularMoviesError getpError() {
        return pError;
    }

    /**
     *
     * @param pError
     */
    public void setpError(PopularMoviesError pError) {
        this.pError = pError;
    }
}
