package com.itmg.bucket.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itmg.bucket.response.SearchResultAO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.io.Reader;

public class SearchResponseHandler extends BaseResponseHandler implements ResponseHandler<SearchResultAO> {
    public SearchResponseHandler(String jsonArguments) {
        super(jsonArguments);
    }

    public SearchResponseHandler() {
        super();
    }

    @Override
    public SearchResultAO handleResponse(final HttpResponse response) throws IOException {
        verifyResponse(response);
        Reader reader = initReaderFromResponse(response);

        try {
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(reader);

            SearchResultAO responseData = new Gson().fromJson(object, SearchResultAO.class);

            reader.close();
            return responseData;
        } catch (Exception ex) {
            throw new IOException("Could not parse Search response.", ex);
        }
    }
}
