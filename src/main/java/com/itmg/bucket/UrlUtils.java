package com.itmg.bucket;

/**
 * Created by Alecsei on 13.03.14.
 */
public final class UrlUtils {

    public static String getCountriesListUrl() {
        return String.format("%s%s%s%s", "http://newshub.org", "/api",
                "/getCountriesList?","accessToken=ec5e7622a39ba5a09e87fabcce102851");
    }

    public static String getDetailsNewsUrl(String newsId, String offsetInMinutes, String countryCode) {
        return String.format("%s%s%s%s&newsID=%s&withHtmlTags=%s&offsetInMinutes=%s&countryCode=%s", "http://newshub.org", "/api",
                "/getDetailedNewsContent?", "accessToken=ec5e7622a39ba5a09e87fabcce102851", newsId, "true", offsetInMinutes, countryCode);
    }
}
