package com.itmg.bucket.handler;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.itmg.bucket.response.MenuItemAO;
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

public class MenuItemsResponseHandler extends BaseResponseHandler implements ResponseHandler<List<MenuItemAO>> {

    public MenuItemsResponseHandler(String jsonArgument) {
        super(jsonArgument);
    }

    @Override
    public List<MenuItemAO> handleResponse(final HttpResponse response) throws IOException {
        verifyResponse(response);
        Gson gson = new GsonBuilder().create();
        Reader reader = initReaderFromResponse(response);

        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(reader);
        JsonArray countries = object.getAsJsonArray(getCustomDataName());

        Type contriesAoType = new TypeToken<List<MenuItemAO>>() {
        }.getType();
        List<MenuItemAO> parsedList = gson.fromJson(countries, contriesAoType);

        reader.close();
        return parsedList;
    }
}
