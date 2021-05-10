package com.example.finalproject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.TimeZone;

public class Interest {

    private String name;
    private String title;
    private String postBody;
    private String postId;

    private String url;
    private String imageUrl;
    private String infoUrl;

    private String flair;
    private int score;
    private String author;
    private String type;

    private Date posted;

    public Interest(JSONObject json) throws JSONException {
        this.name=json.getString("_id");
        this.title=json.getString("title");
        this.postBody=json.getString("selftext");
        this.postId=json.getString("name");

        this.flair=json.getString("link_flair_css_class");
        this.score=json.getInt("score");
        this.author=json.getString("author");
        this.type=json.getString("type");

        this.url=json.getString("url");
        this.imageUrl=json.getString("imageURL");
        this.infoUrl=json.getString("infoURL");

        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        this.posted=new Date(json.getInt("created_utc")*1000L);
    }
    public String getName() {
        return name;
    }
    public String getTitle() {
        return title;
    }
    public String getPostBody() {
        return postBody;
    }
    public String getPostId() {
        return postId;
    }

    public String getUrl() {
        return url;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getInfoUrl() {
        return infoUrl;
    }

    public String getFlair()  {
        return flair;
    }
    public int getScore() {
        return score;
    }
    public String getAuthor() {
        return author;
    }
    public String getType() {
        return type;
    }

    public Date getPosted() {
        return posted;
    }
}

