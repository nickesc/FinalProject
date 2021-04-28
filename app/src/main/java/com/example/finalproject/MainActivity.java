package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static AsyncHttpClient asyncClient = new AsyncHttpClient();
    private ArrayList<Groupbuy> groupbuys = new ArrayList<Groupbuy>();
    private ArrayList<Interest> interests = new ArrayList<Interest>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://www.reddit.com/r/mechmarket/search/.json?q=flair%3A%22group%20buy%22&restrict_sr=1&sort=new&limit=100";

    }
}