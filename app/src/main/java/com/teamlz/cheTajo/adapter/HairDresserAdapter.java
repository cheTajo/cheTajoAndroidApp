package com.teamlz.cheTajo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikepenz.iconics.view.IconicsImageView;
import com.teamlz.cheTajo.R;

import java.util.List;

/*
 * Created by francesco on 02/05/16.
 */

public class HairDresserAdapter extends RecyclerView.Adapter<HairDresserAdapter.HairViewHolder>{

    private List dressers;

    public HairDresserAdapter(List<String> list){
        this.dressers = list;
    }

    public static class HairViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener{

        protected TextView name;
        protected RelativeLayout relative_follow;
        protected RelativeLayout relative_like;
        protected RelativeLayout relative_map;
        protected IconicsImageView icon_follow;
        protected IconicsImageView icon_like;
        protected IconicsImageView icon_map;
        protected boolean like;
        protected boolean follow;
        protected TextView num_thumb;
        protected TextView num_follow;

        public HairViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.dresser_name);
            relative_follow = (RelativeLayout) itemView.findViewById(R.id.relative_follow);
            relative_like = (RelativeLayout) itemView.findViewById(R.id.relative_like);
            relative_map = (RelativeLayout) itemView.findViewById(R.id.relative_map);
            icon_follow = (IconicsImageView) itemView.findViewById(R.id.icon_follow);
            icon_like = (IconicsImageView) itemView.findViewById(R.id.icon_like);
            icon_map = (IconicsImageView) itemView.findViewById(R.id.icon_map);
            num_follow = (TextView) itemView.findViewById(R.id.num_follow);
            num_thumb = (TextView) itemView.findViewById(R.id.num_thumb);
            like = false;
            follow = false;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }

    @Override
    public HairViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homelist, parent, false);
        return new HairViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HairViewHolder holder, int position) {
        holder.name.setText(dressers.get(position).toString());

        holder.relative_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickHolder(v.getId(), holder);
            }
        });

        holder.relative_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickHolder(v.getId(), holder);
            }
        });

        holder.relative_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickHolder(v.getId(), holder);
            }
        });
    }

    public void onItemClickHolder(int id, HairViewHolder holder){

        final Animation resize_small = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.resize_small);
        final Animation resize_big = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.resize_big);
        int blueColor = holder.itemView.getResources().getColor(R.color.blue);
        int redColor = holder.itemView.getResources().getColor(R.color.colorRed);
        int greyColor = holder.itemView.getResources().getColor(R.color.colorGrey);
        int num_thumbs = Integer.valueOf(holder.num_thumb.getText().toString());
        int num_follows = Integer.valueOf(holder.num_follow.getText().toString());

        switch (id){

            case R.id.relative_follow:

                holder.icon_follow.startAnimation(resize_big);
                holder.icon_follow.startAnimation(resize_small);

                if (!holder.follow) {
                    holder.icon_follow.setColor(redColor);
                    holder.num_follow.setText(String.valueOf(num_follows+1));
                    holder.follow = true;
                }
                else {
                    holder.icon_follow.setColor(greyColor);
                    holder.num_follow.setText(String.valueOf(num_follows-1));
                    holder.follow = false;
                }
                break;

            case R.id.relative_like:

                holder.icon_like.startAnimation(resize_big);
                holder.icon_like.startAnimation(resize_small);

                if (!holder.like) {
                    holder.icon_like.setColor(blueColor);
                    holder.num_thumb.setText(String.valueOf(num_thumbs+1));
                    holder.like = true;
                }
                else {
                    holder.icon_like.setColor(greyColor);
                    holder.num_thumb.setText(String.valueOf(num_thumbs-1));
                    holder.like = false;
                }
                break;

            case R.id.relative_map:
                break;
        }
    }


    @Override
    public int getItemCount() {
        return dressers.size();
    }
}
