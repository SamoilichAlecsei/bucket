package com.itmg.bucket.handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.ContentType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * Created by Alecsei on 13.03.14.
 */
public class BaseResponseHandler {

    private String customDataName;

    public BaseResponseHandler(String customString) {
        this.customDataName = customString;
    }

    public BaseResponseHandler() {

    }

    public String getCustomDataName() {
        return customDataName;
    }
    /**
     * Init {@link Reader} object.
     *
     * @param response {@link HttpResponse}
     * @return {@link Reader}
     * @throws IllegalStateException on errors
     * @throws IOException           on errors
     */
    public Reader initReaderFromResponse(HttpResponse response) throws IllegalStateException, IOException {
        HttpEntity entity = response.getEntity();

        ContentType contentType = ContentType.getOrDefault(entity);
        Charset charset = contentType.getCharset();

        return new InputStreamReader(entity.getContent(), charset);
    }

    protected void verifyResponse(final HttpResponse response) throws IOException {

        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES)
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());

        HttpEntity entity = response.getEntity();
        if (entity == null)
            throw new ClientProtocolException("Response contains no content");
    }
}
