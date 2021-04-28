package com.example.finalproject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.TimeZone;

public class Interest {

    private JSONObject ids;
    private JSONObject flair;
    private String url;
    private String title;
    private String author;
    private String subreddit;
    private String postBody;
    private int score;
    private int awardsCount;
    private Date posted;




    public Interest(JSONObject json) throws JSONException {
        this.ids.put("postName", json.getString("name"));
        this.ids.put("postID", json.getString("id"));
        this.ids.put("authorID", json.getString("author_fullname"));
        this.ids.put("subredditID", json.getString("subreddit_id"));

        this.flair.put("flairClass", "link_flair_css_class");
        this.flair.put("flairName", "link_flair_text");

        this.url=json.getString("url");
        this.title=json.getString("title");
        this.author=json.getString("author");
        this.subreddit=json.getString("subreddit");
        this.postBody=json.getString("selftext");
        this.score=json.getInt("score");
        this.awardsCount=json.getInt("total_awards_recieved");

        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        this.posted=new Date(json.getInt("created_utc")*1000L);
    }
    public String getIDs(String string) throws JSONException {
        return ids.getString(string);
    }
    public String getFlair(String string) throws JSONException {
        return flair.getString(string);
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }

    public String getSubreddit() {
        return subreddit;
    }
    public String getPostBody() {
        return postBody;
    }

    public int getScore() {
        return score;
    }

    public int getAwardsCount() {
        return awardsCount;
    }

    public Date getPosted() {
        return posted;
    }
}

