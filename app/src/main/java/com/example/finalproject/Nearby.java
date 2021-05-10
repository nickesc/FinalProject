package com.example.finalproject;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Nearby {

    private JSONObject post;

    private String url;
    private String title;
    private String body;
    private String author;
    private String have;
    private String want;
    private String[] posted = {"",""};

    public Nearby(JSONObject json) throws JSONException {

        post=json;

        url=post.getString("url");
        title=post.getString("title");
        body=post.getString("selftext");
        author=post.getString("author");

        String temp=title.substring(7).trim();
        temp=temp.substring(3).trim();

        String[] titleArray=temp.split("\\[W]");

        //titleArray=titleArray[1].split("W");

        have=titleArray[0].trim();
        want=titleArray[1].trim();
        //have=temp;

        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        Date date = new Date(json.getInt("created_utc")*1000L);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a zzz");

        posted[0]=sdf1.format(date);
        posted[1]=sdf2.format(date);

    }

    public String getUrl() {
        return url;
    }

    public JSONObject getPost() {
        return post;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public String getHave() {
        return have;
    }

    public String getTitle() {
        return title;
    }

    public String getWant() {
        return want;
    }

    public String getPosted() {
        return posted[0]+" at "+posted[1];
    }
    public String getPosted(int x) {

        return posted[0]+"\n"+posted[1];
    }
}
