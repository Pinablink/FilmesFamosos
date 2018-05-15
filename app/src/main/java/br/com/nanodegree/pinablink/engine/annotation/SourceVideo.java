package br.com.nanodegree.pinablink.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import br.com.nanodegree.pinablink.engine.util.ChannelVideo;
import br.com.nanodegree.pinablink.engine.util.TypeVideoView;

/**
 * Created by Pinablink on 15/05/2018.
 */
@Target(ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SourceVideo {
    ChannelVideo channelVideo() default ChannelVideo.NONE;
    TypeVideoView typeVideoView() default TypeVideoView.NONE;
}
