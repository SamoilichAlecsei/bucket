package com.itmg.bucket;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Controller which return main news.
 *
 * @author a.samoilich
 */
@Controller
@RequestMapping("/getMainNews")
public class MainNewsController {
    /**
     * Url by which getting news.
     */
    //TODO maybe move to config file
    private static final String URL = "http://newshub.org/api/getMainPageNews?accessToken=ec5e7622a39ba5a09e87fabcce102851&countryCode=ua&pageID=1&fullContent=NO&offsetInMinutes=120";
    /**
     * Content type.
     */
    private static final String CONTENT_TYPE = "text/html";
    /**
     * Encoding type.
     */
    private static final String ENCODING = "UTF-8";

    @RequestMapping(method = RequestMethod.GET)
    public void getMainNews(HttpServletResponse response) {

        CloseableHttpClient httpClient = createClient();
        HttpGet get = new HttpGet(URL);
        get.addHeader("x-forwarded-for", "127.0.0.1");//TODO remove hard code

        try {
            List<NewsContent> contentList = httpClient.execute(get, new MainNewsResponseHandler("main_news"));
            httpClient.close();

            PrintWriter out = getPrintWriter(response);
            for (NewsContent ao : contentList)
                writeNewsObject(ao, out);

            out.close();
        } catch (ClientProtocolException e) {
            System.out.println("Failed to initialize HttpRequest." + e.getMessage());//TODO change to logger
        } catch (IOException e) {
            System.out.println("Failed to read HttpResponse." + e.getMessage());//TODO change to logger
        }
    }

    /**
     * Build {@link HttpClient}.
     *
     * @return {@link CloseableHttpClient}
     */
    private CloseableHttpClient createClient() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        return builder.build();
    }

    /**
     * Get {@link PrintWriter}.
     *
     * @param response {@link HttpServletResponse}
     * @return {@link PrintWriter}
     * @throws IOException on errors
     */
    private PrintWriter getPrintWriter(HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        return response.getWriter();
    }

    /**
     * Write response to {@link PrintWriter}.
     *
     * @param item for writing
     * @param out  provided {@link PrintWriter}
     */
    private void writeNewsObject(final NewsContent item, final PrintWriter out) {
        out.write("<div>");
        out.write("<a href=" + item.getNews_id() + " class=\"block left\">" +
                "<img class=\"left\" width=\"140\" src=" + item.getImg_src() + " alt=" + item.getImg_alt() + "/>" +
                "</a>");
        out.write("<span class=\"date block\">" + item.getDate_updated() + " </span>");
        out.write("<a href=" + item.getNews_id() + " class=\"news-title block\">" + item.getNews_title() + "</a>");
        out.write("<p>");
        out.write("<span>" + item.getNews_content() + "</span>");
        out.write("</p>");
        out.write("</div>");
    }
}