package com.itmg.bucket;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.itmg.bucket.response.*;
import com.itmg.bucket.service.NewsService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class AppTests {

    private MockMvc mockMvc;

    @Autowired
    private NewsService service;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void simple() throws Exception {
        ResultActions actions = mockMvc.perform(get("/getMainNews"))
                .andExpect(status().isOk());
        MvcResult result = actions.andReturn();
        String content = result.getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        Assert.assertEquals("Should be equals", 74, doc.getAllElements().size());
    }

    @Test
    public void testLoadDetails() {
        final String expectedUrl = "http://newshub.org/api/getDetailedNewsContent?accessToken=ec5e7622a39ba5a09e87fabcce102851&newsID=152687&withHtmlTags=true&offsetInMinutes=120&countryCode=ua";
        final String actualUrl = UrlUtils.getDetailsNewsUrl("152687", "120", "ua");
        Assert.assertEquals("Should be equals", expectedUrl, actualUrl);

        NewsContent expectedContent = buildDetailsNews();
        NewsContent actualContent = null;
        try {
            actualContent = service.loadDetailsContent("152687", "ua", "127.0.0.1", "120");
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
        Assert.assertNotNull("Cannot be NULL", actualContent);
        Assert.assertEquals("Should be equals", expectedContent.getNews_id(), actualContent.getNews_id());
        Assert.assertEquals("Should be equals", expectedContent.getNews_url(), actualContent.getNews_url());
        Assert.assertEquals("Should be equals", expectedContent.getImg_src(), actualContent.getImg_src());
        Assert.assertEquals("Should be equals", expectedContent.getImg_alt(), actualContent.getImg_alt());
        Assert.assertEquals("Should be equals", expectedContent.getDate_updated(), actualContent.getDate_updated());
        Assert.assertEquals("Should be equals", expectedContent.getNews_title(), actualContent.getNews_title());
        Assert.assertEquals("Should be equals", expectedContent.isParsed(), actualContent.isParsed());
        Assert.assertNull("Should be NULL", actualContent.getShort_url());

        //error section
        try {
            service.loadDetailsContent("-1", "ua", "127.0.0.1", "120");
            Assert.fail("Should fail");
        } catch (Exception e) {
            Assert.assertTrue("Should be TRUE", e instanceof IOException);
        }

    }

    private NewsContent buildDetailsNews() {
        NewsContent result = new NewsContent();
        result.setNews_id("152687");
        result.setNews_url("http://www.radikal.com.tr/turkiye/bdpnin_nusaybin_mitinginde_olay_cikti-1167482");
        result.setImg_src("http://i.radikal.com.tr/480x325/2013/12/22/fft64_mf1874225.Jpeg");
        result.setImg_alt("BDP'nin Nusaybin mitinginde olay çıktı");
        result.setDate_updated("22 грудня 2013, 16:30");
        result.setNews_title("BDP'nin Nusaybin mitinginde olay çıktı");
        result.setParsed(true);
        result.setShort_url(null);
        return result;
    }

    @Test
    public void testCountries() {
        final String expectedUrl = "http://newshub.org/api/getCountriesList?accessToken=ec5e7622a39ba5a09e87fabcce102851";
        final String actualUrl = UrlUtils.getCountriesListUrl();
        Assert.assertEquals("Should be equals", expectedUrl, actualUrl);
        try {
            List<CountryAO> countryAOs = service.loadAllCountries();
            Assert.assertNotNull("Cannot be NULL", countryAOs);
            Assert.assertEquals("Should be equals", 60, countryAOs.size());
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }

    }

    @Test
    public void testMenuItems() {
        final String expectedUrl = "http://newshub.org/api/getMenuItems?accessToken=ec5e7622a39ba5a09e87fabcce102851&countryCode=ua";
        final String actualUrl = UrlUtils.getMenuItemsUrl("ua");
        Assert.assertEquals("Should be equals", expectedUrl, actualUrl);
        try {
            List<MenuItemAO> menuItemAOs = service.listMenuItems("ua");
            Assert.assertNotNull("Cannot be NULL", menuItemAOs);
            Assert.assertEquals("Should be equals", 5, menuItemAOs.size());
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
    }

    @Test
    public void testSliderNews() {
        final String expectedUrl = "http://newshub.org/api/getSliderNews?accessToken=ec5e7622a39ba5a09e87fabcce102851&countryCode=ua";
        final String actualUrl = UrlUtils.getSliderNewsUrl("ua");
        Assert.assertEquals("Should be equals", expectedUrl, actualUrl);
        try {
            List<NewsContent> newsContents = service.listSliderNews("ua");
            Assert.assertNotNull("Cannot be NULL", newsContents);
            Assert.assertEquals("Should be equals", 5, newsContents.size());
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
    }

    @Test
    public void testLoadCategoriesByCountry() {
        final String expectedUrl = "http://newshub.org/api/getReferencedCategoriesList?accessToken=ec5e7622a39ba5a09e87fabcce102851&countryCode=ua";
        final String actualUrl = UrlUtils.getCategoriesByCountryUrl("ua");
        Assert.assertEquals("Should be equals", expectedUrl, actualUrl);
        try {
            List<CategoryAO> categoryAOs = service.loadCategoriesByCountry("ua");
            Assert.assertNotNull("Cannot be NULL", categoryAOs);
            Assert.assertEquals("Should be equals", 3, categoryAOs.size());
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
    }

    @Test
    public void testLoadNewsByMenuSectionAndCountry() {
        final String expectedUrl = "http://newshub.org/api/listNewsByMenuItem?accessToken=ec5e7622a39ba5a09e87fabcce102851&countryCode=ua&fullContent=content&menuItem=menuName&pageID=123&offsetInMinutes=120";
        final String actualUrl = UrlUtils.getMenuNewsUrl("menuName", "ua", "content", "123", "120");
        Assert.assertEquals("Should be equals", expectedUrl, actualUrl);
        try {
            List<NewsContent> newsContents = service.loadNewsByMenuSectionAndCountry("news", "ua", "1", "NO", "120");
            Assert.assertNotNull("Cannot be NULL", newsContents);
            Assert.assertEquals("Should be equals", 10, newsContents.size());
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
    }

    @Test
    public void testWeatherData() {
        final String expectedUrl = "http://ua.newshub.org/weather/data";
        final String actualUrl = UrlUtils.getWeatherUrl();
        Assert.assertEquals("Should be equals", expectedUrl, actualUrl);
        try {
            WeatherData weatherData = service.loadWeatherData();//TODO!!!!!!!!!!! need check url. because empty result every time
            Assert.assertNotNull("Cannot be NULL", weatherData);
//            Assert.assertEquals("Should be equals", 10, weatherData.size());
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
    }

    @Test
    public void testFetchUsersLocale() {
        try {
            String code = service.fetchUsersLocale("127.0.0.1");
            Assert.assertEquals("Should be equals", "ua", code);
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
    }

    @Test
    public void testSearchNewsBy() {
        final String expectedUrl = "http://newshub.org/api/searchNewsBy?accessToken=ec5e7622a39ba5a09e87fabcce102851&searchParam=searchParam&countryCode=ua&categoryCode=news&pageId=1&offsetInMinutes=120";
        final String actualUrl = UrlUtils.getSearchNewsLink("searchParam", "ua", "news", "1", "120");
        Assert.assertEquals("Should be equals", expectedUrl, actualUrl);
        try {
            SearchResultAO searchResultAO = service.searchNewsBy("", "ua", "news", "152687", "120");//TODO need valid request parameters
            Assert.assertNotNull("Cannot be NULL", searchResultAO);
//            Assert.assertEquals("Should be equals", 10, searchResultAO.size());
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
    }

    @Test
    public void testTopNews() {
        final String expectedUrl = "http://newshub.org/api/getTopNews?accessToken=ec5e7622a39ba5a09e87fabcce102851&countryCode=ua&offsetInMinutes=120";
        final String actualUrl = UrlUtils.getTopNewsLink("ua", "120");
        Assert.assertEquals("Should be equals", expectedUrl, actualUrl);
        try {
            NewsContent newsContent = service.getTopNews("ua", "120");
            Assert.assertNotNull("Cannot be NULL", newsContent);
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
    }

    @Test
    public void testLoadCountryBundle() {
        final String expectedUrl = "http://newshub.org/api/getLocalizationBundleByCountry?accessToken=ec5e7622a39ba5a09e87fabcce102851&countryCode=ua";
        final String actualUrl = UrlUtils.getLocalizationBundleLink("ua");
        Assert.assertEquals("Should be equals", expectedUrl, actualUrl);
        try {
            SearchResultAO searchResultAO = service.searchNewsBy("", "ua", "news", "152687", "120");//TODO need valid request parameters
            Assert.assertNotNull("Cannot be NULL", searchResultAO);
//            Assert.assertEquals("Should be equals", 10, searchResultAO.size());
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
    }

    @Test
    public void testFulContentForNews() {
        try {
            List<NewsContent> contentForNews = service.getFulContentForNews(Arrays.asList("1", "2", "3"), "120", "ua");
            Assert.assertNotNull("Cannot be NULL", contentForNews);
            Assert.assertEquals("Should be equals", 3, contentForNews.size());
        } catch (Exception e) {
            Assert.fail("Can't happen! e = " + e.getMessage());
        }
    }
}
