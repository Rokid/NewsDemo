package com.rokid.news.http;

import android.text.TextUtils;


import com.rokid.news.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanfeng on 2017/5/15.
 */

public class BaseParameter {

    public static final String PARAM_KEY_KEYWORDS = "keyWords";
    public static final String PARAM_KEY_PERSON = "person";
    public static final String PARAM_KEY_TYPE = "type";
    public static final String PARAM_KEY_MASTERID = "masterId";

    Map<String, String> params = new HashMap<>();


    public void putUnEmptyParam(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            Logger.d("param invalidate ! key " + key + " value : " + value);
            return;
        }
        params.put(key, value);
    }

    public Map<String, String> generateParams(String person, String type,String keyWords) {
        putUnEmptyParam(PARAM_KEY_MASTERID, "1234");
        putUnEmptyParam(PARAM_KEY_KEYWORDS, keyWords);
        putUnEmptyParam(PARAM_KEY_PERSON, person);
        putUnEmptyParam(PARAM_KEY_TYPE, type);
        return params;
    }

    public String getParmasStr(){
        if (params.isEmpty()){
            Logger.d("params null !!!");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }


}
