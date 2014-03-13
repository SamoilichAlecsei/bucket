package com.itmg.bucket.service;

import com.itmg.bucket.localization.LocalizationBundle;
import com.itmg.bucket.response.*;

import java.util.List;

/**
 * Created by User on 12.03.14.
 */
public interface NewsService {

    List<CountryAO> loadAllCountries() throws Exception;

    NewsContent loadDetailsContent(String newsID, String countryCode, String remoteIp, String offsetInMinutes) throws Exception;

    List<MenuItemAO> listMenuItems(String countryCode) throws Exception;

    List<NewsContent> listSliderNews(String countryCode) throws Exception;

    List<CategoryAO> loadCategoriesByCountry(String countryCode) throws Exception;

    List<NewsContent> loadNewsByMenuSectionAndCountry(String menuSection, String countryCode,
                                                      String pageId, String fullContent, String offsetInMinutes)
            throws Exception;

    WeatherData loadWeatherData() throws Exception;

    String fetchUsersLocale(String usersIp) throws Exception;

    SearchResultAO searchNewsBy(String searchParam, String countryCode, String categoryCode, String pageId,
                                String offsetInMinutes) throws Exception;

    NewsContent getTopNews(String countryCode, String offsetInMinutes) throws Exception;

    LocalizationBundle loadCountryBundle(String countryCode) throws Exception;

    List<NewsContent> getFulContentForNews(List<String> newsIdToGet, String offsetInMinutes,
                                           String countryCode) throws Exception;


}
