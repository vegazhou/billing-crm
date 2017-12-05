package com.kt.biz.site;

import com.kt.biz.types.Location;
import com.kt.biz.types.TimeZone;

import javax.persistence.Column;

/**
 * Created by Vega Zhou on 2016/3/29.
 */
public class WebExSitePrimaryFields {
    private String siteName;

    private LanguageMatrix  languages;

    private TimeZone timeZone;

    private Location location;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public LanguageMatrix getLanguages() {
        return languages;
    }

    public void setLanguages(LanguageMatrix languages) {
        this.languages = languages;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
