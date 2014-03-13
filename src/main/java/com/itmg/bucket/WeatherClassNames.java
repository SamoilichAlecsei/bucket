package com.itmg.bucket;

/**
 * Created by User on 13.03.14.
 */
public enum WeatherClassNames {

    WEATHER_IMG("weather_img"),
    DEGREE("degree"),
    LEFT("left"),
    UNKNOWN("unknown");

    private String value;

    WeatherClassNames(final String value) {
        this.value = value;
    }

    public static WeatherClassNames getByValue(final String value) {
        for (WeatherClassNames name : WeatherClassNames.values())
            if (name.value.equals(value))
                return name;
        return UNKNOWN;
    }
}
