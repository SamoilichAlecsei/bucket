package com.itmg.bucket.handler;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.itmg.bucket.response.CategoryAO;
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

/**
 * Created by User on 13.03.14.
 */
public class CategoriesResponseHandler extends BaseResponseHandler implements ResponseHandler<List<CategoryAO>> {

    public CategoriesResponseHandler(String jsonArguments) {
        super(jsonArguments);
    }

    public CategoriesResponseHandler() {
        super();
    }

    @Override
    public List<CategoryAO> handleResponse(final HttpResponse response) throws IOException {

        verifyResponse(response);

        Gson gson = new GsonBuilder().create();
        Reader reader = initReaderFromResponse(response);

        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(reader);
        JsonArray categories = object.getAsJsonArray(getCustomDataName());

        Type contriesAoType = new TypeToken<List<CategoryAO>>() {}.getType();
        List<CategoryAO> parsedList = gson.fromJson(categories, contriesAoType);

        reader.close();
        return parsedList;
    }

}
