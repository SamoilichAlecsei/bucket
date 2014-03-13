package com.itmg.bucket.service;

import com.itmg.bucket.CountryAO;
import com.itmg.bucket.NewsContent;
import com.itmg.bucket.UrlUtils;
import com.itmg.bucket.handler.CountriesResponseHandler;
import com.itmg.bucket.handler.NewsDetailsResponseHandler;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Created by User on 12.03.14.
 */
public class NewsServiceImpl implements NewsService {

    private CloseableHttpClient createClient() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        return builder.build();
    }

    public List<CountryAO> loadAllCountries() throws Exception {
        CloseableHttpClient httpclient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getCountriesListUrl());

        try {
            List<CountryAO> myjson = httpclient.execute(get, new CountriesResponseHandler("countries"));
            httpclient.close();
            return myjson;

        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
    @Override
    public NewsContent loadDetailsContent(String newsID, String countryCode, String remoteIp, String offsetInMinutes)  throws Exception {
        CloseableHttpClient httpclient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getDetailsNewsUrl(newsID, offsetInMinutes, countryCode));

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
}
