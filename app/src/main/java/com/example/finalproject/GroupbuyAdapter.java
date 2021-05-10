package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GroupbuyAdapter extends RecyclerView.Adapter<GroupbuyAdapter.ViewHolder> {

    private ArrayList<Groupbuy> groupbuys;

    public GroupbuyAdapter(ArrayList<Groupbuy> list){

        this.groupbuys =list;
        for(int i=0;i<groupbuys.size();i++){
            Log.d("help",groupbuys.get(i).getName());
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View groupbuyView = inflater.inflate(R.layout.item_groupbuy, parent, false);
        ViewHolder viewHolder = new ViewHolder(groupbuyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupbuyAdapter.ViewHolder holder, int position) {

        Groupbuy groupbuy= groupbuys.get(position);

        holder.cv_groupbuy.setOnClickListener(v -> nextActivity(groupbuy,v));

        holder.tv_name.setText(groupbuy.getName());
        Picasso.get().load(groupbuy.getImageUrl()).into(holder.iv_image);
        //holder.locDimensionTV.setText("Dimension: "+groupbuy.getDimension());
        //holder.locTypeTV.setText("Type: "+groupbuy.getType());

    }

    @Override
    public int getItemCount() {
        return groupbuys.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout cv_groupbuy;
        TextView tv_name;
        ImageView iv_image;
        //TextView locTypeTV;

        public ViewHolder(View itemView){
            super(itemView);
            //Log.d("help", "viewholder");
            cv_groupbuy=itemView.findViewById(R.id.cv_groupbuy);
            tv_name=itemView.findViewById(R.id.textView_groupbuyName);
            iv_image=itemView.findViewById(R.id.imageView_groupbuy);
            //locTypeTV=itemView.findViewById(R.id.tv_locType);
        }
    }

    private void nextActivity(Groupbuy groupbuy, View v){
        Context tcontext = v.getContext();
        Intent intent = new Intent(tcontext,DetailsActivity.class);
        intent.putExtra("id","gb/"+groupbuy.getPostId());
        intent.putExtra("type","gb");

        tcontext.startActivity(intent);

    }


}
