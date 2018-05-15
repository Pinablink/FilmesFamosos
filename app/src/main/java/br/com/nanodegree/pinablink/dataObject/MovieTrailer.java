package br.com.nanodegree.pinablink.dataObject;

import br.com.nanodegree.pinablink.engine.annotation.ParamInJson;

/**
 * Created by Pinablink on 14/05/2018.
 */
public class MovieTrailer {

    private String id;
    private String iso6391;
    private String iso31661;
    private String key;
    private String name;
    private String site;
    private int size;

    public String getId() {
        return id;
    }

    @ParamInJson(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    @ParamInJson(name = "iso_639_1")
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    @ParamInJson(name = "iso_3166_1")
    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    @ParamInJson(name = "key")
    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    @ParamInJson(name="name")
    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    @ParamInJson(name="site")
    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    @ParamInJson(name="size")
    public void setSize(int size) {
        this.size = size;
    }
}
