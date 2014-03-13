package com.itmg.bucket;

import com.itmg.bucket.service.NewsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

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
        mockMvc.perform(get("/getMainNews"))
                .andExpect(status().isOk());
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
}
