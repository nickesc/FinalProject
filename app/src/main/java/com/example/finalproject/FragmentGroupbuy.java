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

public class FragmentGroupbuy extends Fragment {

    private static AsyncHttpClient asyncClient = new AsyncHttpClient();
    private ArrayList<Groupbuy> groupbuys = new ArrayList<Groupbuy>();

    private View view;
    private JSONArray results;
    private RecyclerView groupbuyRecyclerView;

    private String gbUrl = "http://10.0.2.2:5001/gb";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        view=inflater.inflate(R.layout.fragment_groupbuy, container, false);
        groupbuyRecyclerView=view.findViewById(R.id.rv_groupbuy);

        try {
            results = new JSONArray(new String(getArguments().getByteArray("response")));
            Log.d("help",results.toString());
            for (int i=0;i<results.length();i++){
                Groupbuy groupbuy=new Groupbuy(results.getJSONObject(i));
                Log.d("help",groupbuy.getName());
                groupbuys.add(groupbuy);
            }
            GroupbuyAdapter groupbuyAdapter = new GroupbuyAdapter(groupbuys);
            groupbuyRecyclerView.setAdapter(groupbuyAdapter);
            groupbuyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            return view;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
