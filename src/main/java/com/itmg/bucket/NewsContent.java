package com.itmg.bucket;

import java.io.Serializable;

/**
 * Content object.
 *
 * @author a.samoilich
 */
public class NewsContent implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6492499098564221788L;
    /**
     * News id.
     */
    private String news_id;
    /**
     * News url.
     */
    private String news_url;
    /**
     * Image source.
     */
    private String img_src;
    /**
     * Image alt.
     */
    private String img_alt;
    /**
     * Updated date.
     */
    private String date_updated;
    /**
     * News title.
     */
    private String news_title;
    /**
     * News content.
     */
    private String news_content;
    /**
     * Parsed flag.
     */
    private boolean parsed;
    /**
     * Short url.
     */
    private String short_url;

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public String getImg_alt() {
        return img_alt;
    }

    public void setImg_alt(String img_alt) {
        this.img_alt = img_alt;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public boolean isParsed() {
        return parsed;
    }

    public void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

}
