package com.itmg.bucket;

import java.io.Serializable;
import java.util.List;

public class CountryAO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2260496639111792631L;

    private String code;
    private String name;
    private String url;

    private String upperCaseCode;

    private List<CountryAO> countries;

    public String getCode() {
        return code;
    }

    public String getUpperCaseCode() {
        return getCode().toUpperCase();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<CountryAO> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryAO> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "CountryAO [code=" + code + ", name=" + name + ", url=" + url
                + "]";
    }


}
