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
    public static String getMenuItemsUrl(String countryCode) {
        return String.format("%s%s%s%s&countryCode=%s", "http://newshub.org", "/api",
                "/getMenuItems?", "accessToken=ec5e7622a39ba5a09e87fabcce102851", countryCode);
    }

    public static String getSliderNewsUrl(String countryCode) {
        return String.format("%s%s%s%s&countryCode=%s", "http://newshub.org", "/api",
                "/getSliderNews?", "accessToken=ec5e7622a39ba5a09e87fabcce102851", countryCode);
    }
    public static String getMainNewsUrl(String countryCode, String pageID, String fullContent, String offsetInMinutes) {
        return String.format("%s%s%s%s&countryCode=%s&pageID=%s&fullContent=%s&offsetInMinutes=%s", "http://newshub.org", "/api",
                "/getMainPageNews?", "accessToken=ec5e7622a39ba5a09e87fabcce102851", countryCode, pageID, fullContent, offsetInMinutes);
    }

    public static String getCategoriesByCountryUrl(String countryCode) {
        return String.format("%s%s%s%s&countryCode=%s", "http://newshub.org", "/api",
                "/getReferencedCategoriesList?", "accessToken=ec5e7622a39ba5a09e87fabcce102851", countryCode);
    }

    public static String getMenuNewsUrl(String menuName, String countryCode, String fullContent, String pageId, String offsetInMinutes) {
        return String.format("%s%s%s%s&countryCode=%s&fullContent=%s&menuItem=%s&pageID=%s&offsetInMinutes=%s", "http://newshub.org", "/api",
                "/listNewsByMenuItem?", "accessToken=ec5e7622a39ba5a09e87fabcce102851", countryCode, fullContent, menuName, pageId, offsetInMinutes);
    }

    public static String getSearchNewsLink(String searchParam, String countryCode, String categoryCode, String pageId, String offsetInMinutes) {
        return String.format("%s%s%s%s&searchParam=%s&countryCode=%s&categoryCode=%s&pageId=%s&offsetInMinutes=%s", "http://newshub.org", "/api",
                "/searchNewsBy?", "accessToken=ec5e7622a39ba5a09e87fabcce102851", searchParam, countryCode, categoryCode == null ? "all" : categoryCode, pageId, offsetInMinutes);
    }

    public static String getTopNewsLink(String countryCode, String offsetInMinutes) {
        return String.format("%s%s%s%s&countryCode=%s&offsetInMinutes=%s", "http://newshub.org", "/api",
                "/getTopNews?", "accessToken=ec5e7622a39ba5a09e87fabcce102851", countryCode, offsetInMinutes);
    }

    public static String getLocalizationBundleLink(String countryCode) {
        return String.format("%s%s%s%s&countryCode=%s", "http://newshub.org", "/api",
                "/getLocalizationBundleByCountry?", "accessToken=ec5e7622a39ba5a09e87fabcce102851", countryCode);
    }

    public static String getWeatherUrl() {
        return String.format("%s%s", "http://ua.newshub.org", "/weather/data");
    }
}
