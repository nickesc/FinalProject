package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {

    private static AsyncHttpClient asyncClient = new AsyncHttpClient();

    private String urlBase = "http://10.0.2.2:5001/";

    private Groupbuy groupbuy;
    private Interest interest;

    private TextView name;
    private TextView type;
    private TextView title;
    private TextView author;
    private TextView ups;
    private TextView posted;

    private ImageView image;

    private Button infoButton;
    private Button redditButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String postIdUrl=getIntent().getStringExtra("id");
        String intentType=getIntent().getStringExtra("type");
        if(intentType.equals("gb")){
            //Log.d("help",postIdUrl);
            asyncClient.get(urlBase + postIdUrl, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        groupbuy = new Groupbuy(new JSONArray(new String(responseBody)).getJSONObject(0));
                        //Log.d("help", groupbuy.getName());

                        name=findViewById(R.id.tv_infoName);
                        name.setText(groupbuy.getName());

                        type=findViewById(R.id.tv_infoType);
                        type.setText(groupbuy.getType());

                        title=findViewById(R.id.tv_infoPostTitle);
                        title.setText(groupbuy.getTitle());

                        author=findViewById(R.id.tv_infoAuthor);
                        author.setText(groupbuy.getAuthor());

                        ups=findViewById(R.id.tv_infoScore);
                        ups.setText(""+ groupbuy.getScore());

                        posted=findViewById(R.id.tv_infoPosted);
                        posted.setText(groupbuy.getPosted().toString());

                        image=findViewById(R.id.iv_infoImage);
                        Picasso.get().load(groupbuy.getImageUrl()).into(image);

                        infoButton=findViewById(R.id.button_infoButton);
                        redditButton=findViewById(R.id.button_redditButton);

                        redditButton.setOnClickListener(v -> {
                            Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(groupbuy.getUrl()));
                            startActivity(implicit);

                        });

                        if(groupbuy.getInfoUrl().equals("none")){
                            infoButton.setVisibility(View.GONE);
                        }
                        else{
                            if(groupbuy.getInfoUrl().contains("geekhack")){
                                infoButton.setText("Open Geekhack Post");
                            }
                            infoButton.setOnClickListener(v -> {
                                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(groupbuy.getInfoUrl()));
                                startActivity(implicit);
                            });
                        }

                        //image.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("api error", new String((responseBody)));
                }
            });
        }
        else{
            //Log.d("help",postIdUrl);
            asyncClient.get(urlBase + postIdUrl, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        interest = new Interest(new JSONArray(new String(responseBody)).getJSONObject(0));
                        //Log.d("help",interest.getName());

                        name=findViewById(R.id.tv_infoName);
                        name.setText(interest.getName());

                        type=findViewById(R.id.tv_infoType);
                        type.setText(interest.getType());

                        title=findViewById(R.id.tv_infoPostTitle);
                        title.setText(interest.getTitle());

                        author=findViewById(R.id.tv_infoAuthor);
                        author.setText(interest.getAuthor());

                        ups=findViewById(R.id.tv_infoScore);
                        ups.setText(""+interest.getScore());

                        posted=findViewById(R.id.tv_infoPosted);
                        posted.setText(interest.getPosted().toString());

                        image=findViewById(R.id.iv_infoImage);
                        Picasso.get().load(interest.getImageUrl()).into(image);

                        infoButton=findViewById(R.id.button_infoButton);
                        redditButton=findViewById(R.id.button_redditButton);

                        redditButton.setOnClickListener(v -> {
                            Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(interest.getUrl()));
                            startActivity(implicit);

                        });

                        if(interest.getInfoUrl().equals("none")){
                            infoButton.setVisibility(View.GONE);
                        }
                        else{
                            if(interest.getInfoUrl().contains("form")){
                                infoButton.setText("Open Interest Check Form");
                            }
                            else if(groupbuy.getInfoUrl().contains("geekhack")){
                                infoButton.setText("Open Geekhack Post");
                            }
                            infoButton.setOnClickListener(v -> {
                                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(interest.getInfoUrl()));
                                startActivity(implicit);
                            });
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("api error", new String((responseBody)));
                }
            });

        }

    }
}
