package com.itmg.bucket.service;

import com.itmg.bucket.NewsContent;

/**
 * Created by User on 12.03.14.
 */
public interface NewsService {

    public NewsContent loadDetailsContent(String newsID, String countryCode, String remoteIp, String offsetInMinutes) throws Exception;
    String buildDetailsNewsUrl(String newsId, String offsetInMinutes, String countryCode);
}
