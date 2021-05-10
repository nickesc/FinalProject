package com.example.finalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FragmentNearby extends Fragment {

    private View view;
    private RecyclerView nearbyRecyclerView;
    private TextView nearbyTitle;

    private ArrayList<Nearby> nearbys = new ArrayList<Nearby>();


    private static AsyncHttpClient asyncClient = new AsyncHttpClient();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_nearby, container, false);
        nearbyRecyclerView = view.findViewById(R.id.rv_nearby);
        nearbyTitle = view.findViewById(R.id.tv_nearbyTitle);

        String usState=getArguments().getString("state");

        if(usState.equals("null")){
            Log.d("help","state is null");
            nearbyTitle.setText("Unavailable in your area");
        }
        else {
            Log.d("help", "state: " + usState);
            asyncClient.get("https://www.reddit.com/r/mechmarket/search.json?limit=100&restrict_sr=1&q=%22%5BUS-" + usState.toUpperCase() + "%5D%22&sort=new", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONArray json = new JSONObject(new String(responseBody)).getJSONObject("data").getJSONArray("children");

                        for(int i=0; i<json.length();i++){
                            Nearby nearby= new Nearby(json.getJSONObject(i).getJSONObject("data"));

                            nearbys.add(nearby);

                            NearbyAdapter nearbyAdapter = new NearbyAdapter(nearbys);
                            nearbyRecyclerView.setAdapter(nearbyAdapter);
                            nearbyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                            Log.d("help",""+nearby.getHave()+ " | "+nearby.getWant());

                        }

                        //Log.d("help",json.toString(1));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }

        return view;
    }

}
