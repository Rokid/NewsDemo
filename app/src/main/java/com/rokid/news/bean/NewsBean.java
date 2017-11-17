package com.rokid.news.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fanfeng on 2017/6/6.
 */

public class NewsBean implements Parcelable{


    /**
     * id : 4115624218642708
     * type : 1
     * source : 1
     * tts : 来自华尔街见闻APP的消息，<silence=1></silence>【上浮百分之10，甚至百分之20，！多地银行房贷利率全面上调】<silence=1></silence> 近日北京，上海，广州多家银行再次收紧房贷政策，部分银行将首套房贷利率上浮百分之10，，甚至百分之20，。这一方面受楼市调控政策影响，另一方面，“钱紧”背景下，资金成本不断上升，使得商业银行不得不采取措施控制房贷业务的成本。
     * title : 上浮10%甚至20%！多地银行房贷利率全面上调
     * subTitle : 华尔街见闻APP
     * imageUrl : http://rokid.oss-cn-qingdao.aliyuncs.com/thirdparty%2Fnews%2Fres%2Fimg%2Fcommon_bg.png
     */

    private long id;
    private int type;
    private int source;
    private String tts;
    private String title;
    private String subTitle;
    private String imageUrl;

    protected NewsBean(Parcel in) {
        id = in.readLong();
        type = in.readInt();
        source = in.readInt();
        tts = in.readString();
        title = in.readString();
        subTitle = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<NewsBean> CREATOR = new Creator<NewsBean>() {
        @Override
        public NewsBean createFromParcel(Parcel in) {
            return new NewsBean(in);
        }

        @Override
        public NewsBean[] newArray(int size) {
            return new NewsBean[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(type);
        dest.writeInt(source);
        dest.writeString(tts);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(imageUrl);
    }
}
