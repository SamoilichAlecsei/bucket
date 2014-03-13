package com.itmg.bucket.handler;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.itmg.bucket.response.NewsContent;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by User on 13.03.14.
 */
public class NewsResponseHandler extends BaseResponseHandler implements ResponseHandler<List<NewsContent>> {

    public NewsResponseHandler(String jsonArguments) {
        super(jsonArguments);
    }

    @Override
    public  List<NewsContent> handleResponse(final HttpResponse response) throws IOException {
        verifyResponse(response);

        Gson gson = new GsonBuilder().create();
        Reader reader = initReaderFromResponse(response);

        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(reader);
        JsonArray countries = object.getAsJsonArray(getCustomDataName());

        Type contriesAoType = new TypeToken<List<NewsContent>>(){}.getType();
        List<NewsContent> parsedList = gson.fromJson(countries, contriesAoType);

        ListIterator<NewsContent> litr = parsedList.listIterator();
        while (litr.hasNext()) {
            NewsContent element = litr.next();
            if (element.isParsed()) {
                element.setShort_url(element.getNews_id());
                litr.set(element);
            }
        }
        return parsedList;
    }
}
