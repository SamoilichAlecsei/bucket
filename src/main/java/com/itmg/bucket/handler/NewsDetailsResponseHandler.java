package com.itmg.bucket.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itmg.bucket.response.NewsContent;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by User on 12.03.14.
 */
public class NewsDetailsResponseHandler extends BaseResponseHandler implements ResponseHandler<NewsContent> {

    @Override
    public NewsContent handleResponse(final HttpResponse response) throws IOException {
        verifyResponse(response);
        Reader reader = initReaderFromResponse(response);
        try {
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(reader);
            return gson.fromJson(object, NewsContent.class);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }
}
