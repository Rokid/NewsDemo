package com.rokid.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.rokid.news.bean.NewsBean;
import com.rokid.news.http.BaseParameter;
import com.rokid.news.http.BaseUrlConfig;
import com.rokid.news.http.HttpClientWrapper;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanfeng on 2017/6/5.
 */

public class NetCommandProcessor extends BaseCommandProcessor {


    private final String CONTENT_KEY = "content_key";

    private List<NewsBean> newsBeanList;

    private Handler contentHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            List<NewsBean> newsList = bundle.getParcelableArrayList(CONTENT_KEY);
            newsBeanList = newsList;
            resume();
        }
    };

    @Override
    void resetData() {
        index = 0;
    }

    @Override
    void getNews() {
        resetData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseParameter parameter = new BaseParameter();
                parameter.generateParams("", "", "");
                Response response = HttpClientWrapper.getInstance().sendRequest(BaseUrlConfig.getUrl().concat(parameter.getParmasStr()));
                Logger.d("response " + response);
                if (response != null) {
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = null;
                    try {
                        jsonArray = parser.parse(response.body().string()).getAsJsonArray();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new Gson();
                    List<NewsBean> newsList = new ArrayList<>();
                    for (JsonElement jsonElement : jsonArray) {
                        NewsBean newsBean = gson.fromJson(jsonElement, NewsBean.class);
                        newsList.add(newsBean);
                    }
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(CONTENT_KEY, (ArrayList<? extends Parcelable>) newsList);
                    message.setData(bundle);
                    contentHandler.sendMessage(message);
                }
            }
        }).start();
    }

    @Override
    boolean isFirstNews() {
        return index == 0;
    }

    @Override
    public void next() {
        if (newsBeanList == null || newsBeanList.isEmpty()) {
            Logger.d("newsBeanList is null !!!");
            return;
        }
        if (newsBeanList.size() - 1 <= index) {
            alertLastNews();
            return;
        }
        index++;
        NewsBean newsBean = newsBeanList.get(index);
        if (newsBean == null) {
            Logger.d(" newsBean null !!!");
            return;
        }
        String currentContent = newsBean.getTts();

        setTitle(newsBean.getTitle());
        readContent(currentContent);
    }

    @Override
    public void previous() {
        if (newsBeanList == null || newsBeanList.isEmpty()) {
            Logger.d("newsBeanList is null !!!");
            return;
        }
        if (index <= 0) {
            alertFirstNews();
            return;
        }
        index--;
        NewsBean newsBean = newsBeanList.get(index);
        if (newsBean == null) {
            Logger.d(" newsBean null !!!");
            return;
        }
        Logger.d("newsBean : " + newsBean.toString());
        String currentContent = newsBean.getTts();

        setTitle(newsBean.getTitle());
        readContent(currentContent);

    }

    @Override
    boolean isLastNews() {
        return index == newsBeanList.size() - 1;
    }

    @Override
    void pause() {
        pauseTTS();
    }


    @Override
    void resume() {
        if (newsBeanList == null || newsBeanList.isEmpty()) {
            Logger.d(" newsBeanList invalidate!!");
            return;
        }
        if (index > -1 && index < newsBeanList.size()) {
            NewsBean newsBean = newsBeanList.get(index);
            if (newsBean == null) {
                Logger.d(" newsBean null !!!");
                return;
            }
            String currentContent = newsBean.getTts();

            setTitle(newsBean.getTitle());
            readContent(currentContent);
        }
    }

}
