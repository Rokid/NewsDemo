package com.rokid.news;

import android.text.TextUtils;
import android.util.Log;

import rokid.os.RKTTS;
import rokid.os.RKTTSCallback;

/**
 * Created by fanfeng on 2017/6/6.
 */

public abstract class BaseCommandProcessor {

    private final String TAG = this.getClass().getSimpleName();

    //STOP
    private int ttsId = -1;

    public RKTTS mRktts = new RKTTS();

    public NewsShow newsShow;

    int index;


    public void setNewsShow(NewsShow newsShow){
        this.newsShow = newsShow;
    }

    protected void setTitle(String title){
        if (newsShow != null){
            newsShow.setTitle(title);
            Logger.d("readContent index : " + index + " title : " + title);
        }
    }

    protected void readContent(String currentContent) {
        if (TextUtils.isEmpty(currentContent)){
            Log.d(TAG,"currentContent invalidate!!");
            return;
        }
        if (newsShow != null){
            newsShow.setContent(currentContent);
        }
        speakContent(currentContent);
    }

    private void speakContent(String currentContent) {
        if (ttsId > 0){
            mRktts.stop(ttsId);
        }
        ttsId = mRktts.speak(currentContent,rkttsCallback);
        Logger.d(" readContent  speak ttsId : " + ttsId);
    }

    private void speakMessage(String currentContent) {
        if (ttsId > 0){
            mRktts.stop(ttsId);
        }
        Logger.d(" readContent ttsId : " + ttsId);
        ttsId = mRktts.speak(currentContent,null);
        Logger.d(" readContent  speak ttsId : " + ttsId);
    }

    private void speakFirst(String welcomeStr){
        setTitle(welcomeStr);
        mRktts.speak(welcomeStr,new RKTTSCallback(){
            @Override
            public void onStart(int id) {
                super.onStart(id);
            }

            @Override
            public void onCancel(int id) {
                super.onCancel(id);
            }

            @Override
            public void onComplete(int id) {
                super.onComplete(id);
                getNews();
            }

            @Override
            public void onError(int id, int err) {
                super.onError(id, err);
            }
        });
    }


    private RKTTSCallback rkttsCallback = new RKTTSCallback(){
        @Override
        public void onStart(int id) {
            super.onStart(id);
        }

        @Override
        public void onCancel(int id) {
            super.onCancel(id);
        }

        @Override
        public void onComplete(int id) {
            super.onComplete(id);
            if (isLastNews()){
                speakLastNews();
            }else {
                next();
            }
        }

        @Override
        public void onError(int id, int err) {
            super.onError(id, err);
        }
    };


    public interface NewsShow{
        void setTitle(String title);
        void setContent(String content);
        void finish();
    }

    protected void start(){
        String hello = HelloUtils.hello();
        if (TextUtils.isEmpty(hello)){
            Logger.d("invalidate time !!!");
            return;
        }
        speakFirst(hello.concat("，若琪为您播报本时段最新资讯"));
    }

    abstract void resetData();
    abstract void getNews();
    abstract boolean isFirstNews();
    abstract boolean isLastNews();
    abstract void pause();
    abstract void resume();
    abstract void next();
    abstract void previous();

    public void pauseTTS(){
        if (ttsId > 0){
            mRktts.stop(ttsId);
        }
    }

    protected void alertFirstNews(){
        speakMessage("已经是第一条了.");
    }

    protected void alertLastNews(){
        speakMessage("已经是最后一条了.");
    }


    protected void speakLastNews(){
        speakMessage("以上就是这次的若琪新闻，感谢您的收听。");
    }

    public void finish(){
        if (newsShow != null){
            newsShow.finish();
        }
    }

}
