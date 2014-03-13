package com.itmg.bucket.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itmg.bucket.handler.*;
import com.itmg.bucket.localization.LocalizationBundle;
import com.itmg.bucket.localization.LocalizationBundleHandler;
import com.itmg.bucket.response.*;
import com.itmg.bucket.UrlUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
    public NewsContent loadDetailsContent(String newsID, String countryCode, String remoteIp, String offsetInMinutes) throws Exception {
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

    @Override
    public List<MenuItemAO> listMenuItems(String countryCode) throws Exception {
        CloseableHttpClient httpclient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getMenuItemsUrl(countryCode));

        try {
            List<MenuItemAO> myjson = httpclient.execute(get, new MenuItemsResponseHandler("menu_items"));
            httpclient.close();

            return myjson;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public List<NewsContent> listSliderNews(String countryCode) throws Exception {

        CloseableHttpClient httpclient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getSliderNewsUrl(countryCode));

        try {
            List<NewsContent> myjson = httpclient.execute(get, new NewsResponseHandler("slider_news"));
            httpclient.close();

            return myjson;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public List<CategoryAO> loadCategoriesByCountry(String countryCode) throws Exception {
        CloseableHttpClient httpclient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getCategoriesByCountryUrl(countryCode));

        try {
            List<CategoryAO> myjson = httpclient.execute(get, new CategoriesResponseHandler("categories"));
            httpclient.close();
            return myjson;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public List<NewsContent> loadNewsByMenuSectionAndCountry(String menuSection, String countryCode,
                                                             String pageId, String fullContent, String offsetInMinutes)
            throws Exception {

        CloseableHttpClient httpclient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getMenuNewsUrl(menuSection, countryCode, fullContent, pageId, offsetInMinutes));

        try {
            List<NewsContent> myjson = httpclient.execute(get, new NewsResponseHandler("menu_news"));
            httpclient.close();
            return myjson;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public List<NewsContent> getFreshNews(String accessToken, String countryCode, String menuItem, String lastNewsTimestamp,
                                          String fullContent, String offsetInMinutes) throws Exception {
        CloseableHttpClient httpclient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getFreshNewsUrl(accessToken, countryCode, menuItem, lastNewsTimestamp, fullContent, offsetInMinutes));

        try {
            List<NewsContent> myjson = httpclient.execute(get, new NewsResponseHandler("menu_news"));
            httpclient.close();
            return myjson;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }

    }

    @Override
    public WeatherData loadWeatherData() throws Exception {
        CloseableHttpClient httpClient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getWeatherUrl());

        try {
            WeatherData data = httpClient.execute(get, new WeatherResponseHandler());
            httpClient.close();
            return data;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public String fetchUsersLocale(String usersIp) throws Exception {
        try {
            HttpURLConnection con = (HttpURLConnection) (new URL("http://newshub.org")).openConnection();
            con.setInstanceFollowRedirects(false);
            if (usersIp.equals("127.0.0.1") || usersIp.equals("0:0:0:0:0:0:0:1%0") || usersIp.endsWith("::1"))
                usersIp = "176.106.1.193";

            con.addRequestProperty("x-forwarded-for", usersIp);
            con.connect();

            String code = extractLocaleCode(con.getHeaderField("Location"));
            con.disconnect();

            return code;
        } catch (MalformedURLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    private String extractLocaleCode(String location) {
        return location != null ? location.substring(location.indexOf("//") + 2, location.indexOf(".")) : "ua";
    }

    @Override
    public SearchResultAO searchNewsBy(String searchParam, String countryCode, String categoryCode, String pageId,
                                       String offsetInMinutes) throws Exception {
        CloseableHttpClient httpclient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getSearchNewsLink(searchParam, countryCode, categoryCode, pageId, offsetInMinutes));

        try {
            SearchResultAO data = httpclient.execute(get, new SearchResponseHandler("searched_news"));
            httpclient.close();

            return data;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
    @Override
    public NewsContent getTopNews(String countryCode, String offsetInMinutes) throws Exception {
        CloseableHttpClient httpclient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getTopNewsLink(countryCode, offsetInMinutes));

        try {
            List<NewsContent> myjson = httpclient.execute(get, new NewsResponseHandler("topnews"));
            httpclient.close();
            return myjson.get(0);
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
    @Override
    public LocalizationBundle loadCountryBundle(String countryCode) throws Exception {
        CloseableHttpClient httpclient = createClient();
        HttpGet get = new HttpGet(UrlUtils.getLocalizationBundleLink(countryCode));

        try {
            LocalizationBundle myjson = httpclient.execute(get, new LocalizationBundleHandler(countryCode));
            httpclient.close();
            return myjson;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public List<NewsContent> getFulContentForNews(List<String> newsIdToGet, String offsetInMinutes,
                                                  String countryCode) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectionRequestTimeout(5000).
                setConnectTimeout(5000).
                setSocketTimeout(5000).build();
        CloseableHttpAsyncClient asyncHttpClient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();

        final List<NewsContent> fullNewsContentList = new ArrayList<NewsContent>();

        try {
            asyncHttpClient.start();
            List<HttpGet> requestsList = createRequestListForNewsIds(newsIdToGet, offsetInMinutes, countryCode);
            final CountDownLatch countLatch = new CountDownLatch(requestsList.size());

            for (final HttpGet apiRequest : requestsList) {
                asyncHttpClient.execute(apiRequest, new FutureCallback<HttpResponse>() {

                    @Override
                    public void failed(Exception ex) {
                        countLatch.countDown();
                    }

                    @Override
                    public void completed(HttpResponse response) {

                        StatusLine statusLine = response.getStatusLine();
                        HttpEntity entity = response.getEntity();

                        if (statusLine.getStatusCode() >= 300 || entity == null) {
                            System.out.println(String.format("BATCH: FAILED to complete batch request:%s. Reason is:%s", apiRequest, statusLine.getReasonPhrase()));
                        }
                        try {
                            Reader reader = initReaderFromResponse(response);
                            Gson gson = new GsonBuilder().create();
                            // TODO: this is to much...
                            JsonParser parser = new JsonParser();
                            JsonObject object = (JsonObject) parser.parse(reader);

                            NewsContent detailedNews = gson.fromJson(object, NewsContent.class);
                            if (detailedNews == null) {
                                System.out.println(String.format("Parsed to NUll object from json response: %s", object));
                            } else {
                                System.out.println(String.format("Adding newsResponse to list: %s", detailedNews));
                                fullNewsContentList.add(detailedNews);
                            }
                            System.out.println(String.format("Completed batch request:%s", apiRequest));
                        } catch (Exception e) {
                            System.out.println(String.format("BATCH: FAILED to complete batch request:%s", apiRequest));

                        }
                        countLatch.countDown();
                    }

                    @Override
                    public void cancelled() {
                        countLatch.countDown();
                        System.out.println(String.format("BATCH: FAILED to complete batch request. Request was cancelled.%s", apiRequest));
                    }
                });
            }
            countLatch.await();
            System.out.println("HttGet finished all requests.");

        } catch (InterruptedException e) {
            System.out.println("BATCH: Failed to execute HttpClient requests." + e);
            throw e;
        } finally {
            try {
                asyncHttpClient.close();
            } catch (IOException e) {
                System.out.println("BATCH: Failed to close HttpClient" + e);
            }
        }
        return fullNewsContentList;
    }

    public Reader initReaderFromResponse(HttpResponse response) throws IllegalStateException, IOException {
        HttpEntity entity = response.getEntity();

        ContentType contentType = ContentType.getOrDefault(entity);
        Charset charset = contentType.getCharset();

        return new InputStreamReader(entity.getContent(), charset);
    }

    private List<HttpGet> createRequestListForNewsIds(List<String> newsIds, String offsetInMinutes, String countryCode) {
        List<HttpGet> requests = new ArrayList<HttpGet>();
        for (String newsId : newsIds) {
            HttpGet get = new HttpGet(UrlUtils.getDetailsNewsUrl(newsId, offsetInMinutes, countryCode));
            requests.add(get);
        }
        return requests;
    }

}
