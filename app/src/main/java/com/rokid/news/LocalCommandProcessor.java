package com.rokid.news;

import android.util.Log;

/**
 * Created by fanfeng on 2017/6/6.
 */

public class LocalCommandProcessor extends BaseCommandProcessor {

    private final String TAG = this.getClass().getSimpleName();

    private String[] mContents;
    private int index;


    public void setContents(String... contents){
        if (contents == null){
            Log.i(TAG," contents null !!!");
            return;
        }
        this.mContents = contents;
    }
    @Override
    public void start() {

    }

    @Override
    void resetData() {
        index = 0;
    }

    @Override
    void getNews() {

    }

    @Override
    boolean isFirstNews() {
        return false;
    }

    @Override
    boolean isLastNews() {
        return index == mContents.length-1;
    }

    @Override
    void pause() {

    }

    @Override
    void resume() {

    }

    @Override
    public void next() {
        if (mContents == null){
            Log.i(TAG,"contents queue empty !");
            return;
        }
        if ( mContents.length < index +1){
            alertLastNews();
            return;
        }
        String currentContent = mContents[index ++];
        readContent(currentContent);
    }

    @Override
    public void previous() {
        if (mContents == null){
            Log.i(TAG,"contents queue empty !");
            return;
        }
        if ( index < 0){
            alertFirstNews();
            return;
        }
        String currentContent = mContents[index--];
        readContent(currentContent);
    }

}
