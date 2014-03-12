package com.itmg.bucket.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itmg.bucket.NewsContent;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * Created by User on 12.03.14.
 */
public class NewsDetailsResponseHandler implements ResponseHandler<NewsContent> {

    @Override
    public NewsContent handleResponse(final HttpResponse response) throws IOException {

        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        if (statusLine.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES)
            throw new HttpResponseException(statusLine.getStatusCode(),statusLine.getReasonPhrase());
        if (entity == null)
            throw new ClientProtocolException("Response contains no content");

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

    /**
     * Init {@link Reader} object.
     *
     * @param response {@link HttpResponse}
     * @return {@link Reader}
     * @throws IllegalStateException on errors
     * @throws IOException           on errors
     */
    private Reader initReaderFromResponse(HttpResponse response) throws IllegalStateException, IOException {
        HttpEntity entity = response.getEntity();
        ContentType contentType = ContentType.getOrDefault(entity);
        Charset charset = contentType.getCharset();
        return new InputStreamReader(entity.getContent(), charset);
    }
}
