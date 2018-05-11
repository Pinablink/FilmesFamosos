package br.com.nanodegree.pinablink.dataObject;

import br.com.nanodegree.pinablink.engine.annotation.ParamInJson;

/**
 * Created by Pinablink on 11/05/2018.
 */
public class Review {

    private String id;
    private String url;
    private String author;
    private String content;


    public String getId() {
        return id;
    }

    @ParamInJson(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    @ParamInJson(name = "url")
    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    @ParamInJson(name = "author")
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    @ParamInJson(name = "content")
    public void setContent(String content) {
        this.content = content;
    }
}
