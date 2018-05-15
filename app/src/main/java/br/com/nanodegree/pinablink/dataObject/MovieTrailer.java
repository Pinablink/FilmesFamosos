package br.com.nanodegree.pinablink.dataObject;

import com.squareup.picasso.RequestCreator;
import br.com.nanodegree.pinablink.engine.annotation.Format;
import br.com.nanodegree.pinablink.engine.annotation.ParamInJson;
import br.com.nanodegree.pinablink.engine.annotation.SourceVideo;
import br.com.nanodegree.pinablink.engine.util.ChannelVideo;
import br.com.nanodegree.pinablink.engine.util.TypeVideoView;

/**
 * Created by Pinablink on 14/05/2018.
 */
@SourceVideo(channelVideo = ChannelVideo.YOUTUBE, typeVideoView = TypeVideoView.TRAILER)
public class MovieTrailer {

    private String id;
    private String iso6391;
    private String iso31661;
    private String key;
    private String name;
    private String site;
    private String size;
    private String pathThumbnail;
    private RequestCreator refRequestImg;

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

    public String getSize() {
        return size;
    }

    @ParamInJson(name="size")
    public void setSize(String size) {
        this.size = size;
    }

    public String getPathThumbnail() {
        return pathThumbnail;
    }

    @Format(input="key", valueFormat = "http://img.youtube.com/vi/%s/default.jpg")
    public void setPathThumbnail(String pathThumbnail) {
        this.pathThumbnail = pathThumbnail;
    }

    public RequestCreator getRefRequestImg() {
        return refRequestImg;
    }

    public void setRefRequestImg(RequestCreator refRequestImg) {
        this.refRequestImg = refRequestImg;
    }
}
