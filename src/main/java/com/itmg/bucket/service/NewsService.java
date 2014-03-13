package com.itmg.bucket.service;

import com.itmg.bucket.CountryAO;
import com.itmg.bucket.NewsContent;

import java.util.List;

/**
 * Created by User on 12.03.14.
 */
public interface NewsService {

    List<CountryAO> loadAllCountries() throws Exception;

    public NewsContent loadDetailsContent(String newsID, String countryCode, String remoteIp, String offsetInMinutes) throws Exception;
}
