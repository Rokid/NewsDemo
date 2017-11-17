package com.rokid.news;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.rokid.news.bean.NLPBean;

/**
 * Created by fanfeng on 2017/5/8.
 */

public class CommandController {
    public static final String TAG = "CommandController";

    private static final String KEY_NLP = "nlp";

    private static final String COMMAND_PLAY = "news_play";
    private static final String COMMAND_WELCOME = "ROKID.INTENT.WELCOME";
    private static final String COMMAND_NEXT = "news_next";
    private static final String COMMAND_PREVIOUS = "news_previous";
    private static final String COMMAND_FINISH = "news_finish";
    private static final String COMMAND_PAUSE = "news_pause";
    private static final String COMMAND_RESUME = "news_resume";

    private boolean isStarted;

    private BaseCommandProcessor netCommandProcessor;

    CommandController(BaseCommandProcessor netCommandProcessor) {
        this.netCommandProcessor = netCommandProcessor;
    }

    public void parseIntent(Intent intent) {
        Logger.d(" parseIntent ");
        if (intent == null) {
            Logger.d("intent null !");
            return;
        }
        String nlp = intent.getStringExtra(KEY_NLP);

        if (TextUtils.isEmpty(nlp)) {
            Logger.d("nlp invalidate !");
            return;
        }

        Logger.d("parseIntent Nlp ---> ", nlp);
        NLPBean nlpBean = new Gson().fromJson(nlp, NLPBean.class);

        String intentEvent = nlpBean.getIntent();

        Log.d(TAG, "result intentEvent : " + intentEvent + "  slots " + nlpBean.getSlots());
        switch (intentEvent) {
            case COMMAND_PLAY:
            case COMMAND_WELCOME:
                if (!isStarted) {
                    netCommandProcessor.start();
                    isStarted = true;
                } else {
                    netCommandProcessor.resume();
                }
                break;
            case COMMAND_NEXT:
                if (isStarted) {
                    netCommandProcessor.next();
                }
                break;
            case COMMAND_PREVIOUS:
                if (isStarted) {
                    netCommandProcessor.previous();
                }
                break;
            case COMMAND_FINISH:
                netCommandProcessor.finish();
                break;
            case COMMAND_PAUSE:
                netCommandProcessor.pause();
                break;
            case COMMAND_RESUME:
                netCommandProcessor.resume();
                break;
            default:
                Log.d(TAG, "unKnow command " + intentEvent);
        }
    }

}
