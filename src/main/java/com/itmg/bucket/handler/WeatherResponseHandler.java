package com.itmg.bucket.handler;

import com.itmg.bucket.WeatherClassNames;
import com.itmg.bucket.response.WeatherData;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by User on 13.03.14.
 */
public class WeatherResponseHandler extends BaseResponseHandler implements ResponseHandler<WeatherData> {
    @Override
    public WeatherData handleResponse(HttpResponse response) throws IOException {
        verifyResponse(response);
        try {
            String html = EntityUtils.toString(response.getEntity());
            WeatherData weatherData = new WeatherData();
            Document doc = Jsoup.parse(html);

            for (Element el : doc.getAllElements()) {
                System.out.println(el.className() + "  " + el.attr("src") + "  " + el.text());
                WeatherClassNames classNames = WeatherClassNames.getByValue(el.className());
                switch (classNames) {
                    case WEATHER_IMG:
                        weatherData.setImgUrl(String.format("%s%s", "http://ua.newshub.org", el.attr("src")));
                    case DEGREE:
                        weatherData.setDegree(el.text());
                    case LEFT:
                        weatherData.setLocation(el.text());
                    default:
                        //some worning logs...
                }
            }
            return weatherData;
        } catch (Exception ex) {
            throw new IOException("Failed to parse data for weather.", ex);
        }
    }
}
