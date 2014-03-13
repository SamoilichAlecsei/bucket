package com.itmg.bucket.handler;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.itmg.bucket.CountryAO;
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
 * Created by Alecsei on 13.03.14.
 */
public class CountriesResponseHandler extends BaseResponseHandler implements ResponseHandler<List<CountryAO>> {

    public CountriesResponseHandler(String jsonArguments) {
        super(jsonArguments);
    }

    public CountriesResponseHandler() {
        super();
    }

    @Override
    public List<CountryAO> handleResponse(final HttpResponse response) throws IOException {

        verifyResponse(response);
        Gson gson = new GsonBuilder().create();
        Reader reader = initReaderFromResponse(response);

        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(reader);
        JsonArray countries = object.getAsJsonArray(getCustomDataName());

        Type contriesAoType = new TypeToken<List<CountryAO>>() {}.getType();
        List<CountryAO> parsedList = gson.fromJson(countries, contriesAoType);

        reader.close();
        return parsedList;
    }
}
