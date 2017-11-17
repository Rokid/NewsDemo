package com.rokid.news.http;


/**
 * Created by fanfeng on 2017/5/11.
 */

public class BaseUrlConfig {

    public static final String TAG = "BaseUrlConfig";

    public static final String BASE_URL = "https://cloudapp-test.rokid-inc.com";

    public static final String PATH = "/weibonews/http/news/getNews";

    public static String getUrl() {

        String baseUrl = BASE_URL.concat(PATH);

        return baseUrl;
    }

}
