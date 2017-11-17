package com.rokid.news.http;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rokid.news.Logger;
import com.rokid.news.bean.NewsBean;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by fanfeng on 2017/5/11.
 */

public class HttpClientWrapper {

    private static OkHttpClient okHttpClient;
    private static final int CONNECTION_TIME_OUT = 10;
    private static final int READ_TIME_OUT = 10;
    private static final int WRITE_TIME_OUT = 10;

    public HttpClientWrapper() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);

    }

    public static HttpClientWrapper getInstance() {
        return SingleHolder.instance;
    }

    public Response sendRequest(String url) {

        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "text/plain")
                .addHeader("Accept-Charset", "utf-8")
                .addHeader("Cache-Control", "no-cache")
                .get()
                .build();
        Response response = null;
        Call call = null;
        try {
            call = okHttpClient.newCall(request);
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            okHttpClient.cancel(call);
        }
        if (response == null) {
            Logger.d("getNews response callback is null!");
        } else if (!response.isSuccessful()) {
            Logger.d("getNews response not success! response : " + response);
        } else {
            Logger.d("getNews response success! response : " + response);

        }
        return response;
    }

    private static class SingleHolder {
        private static final HttpClientWrapper instance = new HttpClientWrapper();
    }

}
