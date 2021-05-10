package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FragmentInterest extends Fragment {

    private static AsyncHttpClient asyncClient = new AsyncHttpClient();
    private ArrayList<Interest> interests = new ArrayList<Interest>();

    private View view;
    private JSONArray results;
    private RecyclerView interstRecyclerView;

    private String icUrl = "http://10.0.2.2:5001/ic";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        view=inflater.inflate(R.layout.fragment_interest, container, false);
        interstRecyclerView=view.findViewById(R.id.rv_interest);

        try {
            results = new JSONArray(new String(getArguments().getByteArray("response")));
            Log.d("help",results.toString());
            for (int i=0;i<results.length();i++){
                Interest interest=new Interest(results.getJSONObject(i));
                Log.d("help",interest.getName());
                interests.add(interest);
            }
            InterestAdapter interestAdapter = new InterestAdapter(interests);
            interstRecyclerView.setAdapter(interestAdapter);
            interstRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            return view;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
