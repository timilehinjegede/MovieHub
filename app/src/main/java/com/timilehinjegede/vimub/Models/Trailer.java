package com.timilehinjegede.vimub.Models;

import com.timilehinjegede.vimub.Volley.URLs;

public class Trailer {

    private String source;
    private String name;
    String thumbnail;

    public Trailer(String source, String name,String thumbnail) {
        this.source = URLs.YOUTUBE_BASE+ source;
        this.name = name;
        this.thumbnail = thumbnail;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
