package br.com.nanodegree.pinablink.engine.util;

/**
 * Created by Pinablink on 15/05/2018.
 */
public enum TypeVideoView {
    NONE(""),
    TRAILER("Trailer"),
    CLIP("Clip"),
    TEASER("Teaser"),
    FEATURETTE("Featurette");

    public String value;

    TypeVideoView(String val) {
        value = val;
    }

}
