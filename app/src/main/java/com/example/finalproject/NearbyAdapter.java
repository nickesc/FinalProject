package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {


    private List<Nearby> nearbys;

    public NearbyAdapter(List<Nearby> list){
        this.nearbys =list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View nearbyView = inflater.inflate(R.layout.item_nearby, parent, false);
        ViewHolder viewHolder = new ViewHolder(nearbyView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyAdapter.ViewHolder holder, int position) {

        Nearby nearby = nearbys.get(position);

        holder.tvWant.setText("Want: "+nearby.getWant());
        holder.tvHave.setText("Have: "+nearby.getHave());
        holder.tvPosted.setText(nearby.getPosted(1));

        holder.cvNearbyItem.setOnClickListener(v -> {
            //Log.d("help",nearby.getTitle());
            nextActivity(nearby,v);
        });

    }

    @Override
    public int getItemCount() {
        return nearbys.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvHave;
        TextView tvWant;
        TextView tvPosted;
        ConstraintLayout cvNearbyItem;

        public ViewHolder (View itemView){
            super(itemView);
            tvHave=itemView.findViewById(R.id.tv_nearbyHave);
            tvWant=itemView.findViewById(R.id.tv_nearbyWant);
            tvPosted=itemView.findViewById(R.id.tv_nearbyPosted);
            cvNearbyItem=itemView.findViewById(R.id.cv_nearby);
        }

    }
    private void nextActivity(Nearby nearby, View v){
        Context tcontext = v.getContext();
        Intent intent = new Intent(tcontext,NearbyPostActivity.class);
        intent.putExtra("url",nearby.getUrl()+".json");
        tcontext.startActivity(intent);

    }

}
