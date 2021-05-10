package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class NearbyPostActivity extends AppCompatActivity {


    private static AsyncHttpClient asyncClient = new AsyncHttpClient();

    private TextView haveInfo;
    private TextView wantInfo;
    private TextView authorInfo;
    private TextView bodyInfo;
    private TextView postedInfo;

    private Button nearbyRedditButton;

    private Nearby nearby;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearbypost);

        haveInfo=findViewById(R.id.tv_nearbyInfoHave);
        wantInfo=findViewById(R.id.tv_nearbyInfoWant);
        authorInfo=findViewById(R.id.tv_nearbyInfoAuthor);
        bodyInfo=findViewById(R.id.tv_nearbyInfoBody);
        postedInfo=findViewById(R.id.tv_nearbyInfoPosted);
        nearbyRedditButton=findViewById(R.id.button_nearbyReddit);

        String url = getIntent().getStringExtra("url");
        asyncClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONArray(new String(responseBody)).getJSONObject(0).getJSONObject("data").getJSONArray("children").getJSONObject(0).getJSONObject("data");
                    nearby=new Nearby(json);
                    Log.d("help",nearby.getPosted());
                    haveInfo.setText("Have: " + nearby.getHave());
                    wantInfo.setText("Want: "+nearby.getWant());
                    authorInfo.setText("Posted by: "+nearby.getAuthor());
                    bodyInfo.setText(nearby.getBody());
                    postedInfo.setText("Posted at: "+nearby.getPosted());

                    nearbyRedditButton.setOnClickListener(v -> {
                        Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(nearby.getUrl()));
                        startActivity(implicit);

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
