package com.itmg.bucket;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
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
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ListIterator;

/**
 * Response handler.
 *
 * @author a.samoilich
 */
public class MainNewsResponseHandler implements ResponseHandler<List<NewsContent>> {
    /**
     * Custom data name.
     */
    private String customDataName;

    /**
     * Constructor.
     *
     * @param customDataName provided data name
     */
    public MainNewsResponseHandler(String customDataName) {
        this.customDataName = customDataName;
    }

    public String getCustomDataName() {
        return customDataName;
    }

    @Override
    public List<NewsContent> handleResponse(final HttpResponse response) throws IOException {

        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        if (statusLine.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES)
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        if (entity == null)
            throw new ClientProtocolException("Response contains no content");

        Gson gson = new GsonBuilder().create();
        Reader reader = initReaderFromResponse(response);

        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(reader);
        JsonArray newsArray = object.getAsJsonArray(getCustomDataName());

        Type newsType = new TypeToken<List<NewsContent>>() {
        }.getType();
        List<NewsContent> parsedList = gson.fromJson(newsArray, newsType);

        ListIterator<NewsContent> litr = parsedList.listIterator();
        while (litr.hasNext()) {
            final NewsContent element = litr.next();
            if (!element.isParsed())
                continue;
            element.setShort_url(element.getNews_id());

        }
        return parsedList;
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
