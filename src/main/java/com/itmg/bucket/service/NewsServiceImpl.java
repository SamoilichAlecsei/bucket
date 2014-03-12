package com.itmg.bucket.service;

import com.itmg.bucket.NewsContent;
import com.itmg.bucket.handler.NewsDetailsResponseHandler;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by User on 12.03.14.
 */
public class NewsServiceImpl implements NewsService {


    @Override
    public NewsContent loadDetailsContent(String newsID, String countryCode, String remoteIp, String offsetInMinutes)  throws Exception {
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient httpclient = builder.build();
        final String url = buildDetailsNewsUrl(newsID, offsetInMinutes, countryCode);
        HttpGet get = new HttpGet(url);

        try {
            NewsContent myjson = httpclient.execute(get, new NewsDetailsResponseHandler());
            httpclient.close();
            return myjson;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public String buildDetailsNewsUrl(String newsId, String offsetInMinutes, String countryCode) {
        return String.format("%s%s%s%s&newsID=%s&withHtmlTags=%s&offsetInMinutes=%s&countryCode=%s", "http://newshub.org", "/api",
                "/getDetailedNewsContent?", "accessToken=ec5e7622a39ba5a09e87fabcce102851", newsId, "true", offsetInMinutes, countryCode);
    }
}
