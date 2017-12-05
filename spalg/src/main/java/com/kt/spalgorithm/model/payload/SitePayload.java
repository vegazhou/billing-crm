package com.kt.spalgorithm.model.payload;

import com.kt.biz.types.Location;
import com.kt.biz.types.TimeZone;
import com.kt.biz.site.LanguageMatrix;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class SitePayload {
    private TimeZone timeZone;
    private String countryCode;
    private LanguageMatrix languages;
    private Location location;


    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public LanguageMatrix getLanguages() {
        return languages;
    }

    public void setLanguages(LanguageMatrix languages) {
        this.languages = languages;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SitePayload deepClone() {
        SitePayload clone = new SitePayload();
        if (languages != null) {
            clone.languages = languages.deepClone();
        }
        clone.timeZone = timeZone;
        clone.countryCode = countryCode;
        clone.location = location;
        return clone;
    }
}
