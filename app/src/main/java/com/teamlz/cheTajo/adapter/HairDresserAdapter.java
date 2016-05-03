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

/**
 * Created by francesco on 02/05/16.
 */
public class HairDresserAdapter extends RecyclerView.Adapter<HairDresserAdapter.HairViewHolder>{

    private List dressers;

    public HairDresserAdapter(List<String> list){
        this.dressers = list;
    }

    public static class HairViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener, View.OnClickListener{

        protected TextView name;
        protected RelativeLayout relative_follow;
        protected RelativeLayout relative_like;
        protected RelativeLayout relative_map;
        protected IconicsImageView icon_follow;
        protected IconicsImageView icon_like;
        protected IconicsImageView icon_map;
        protected boolean like;
        protected boolean follow;

        public HairViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.dresser_name);
            relative_follow = (RelativeLayout) itemView.findViewById(R.id.relative_follow);
            relative_like = (RelativeLayout) itemView.findViewById(R.id.relative_like);
            relative_map = (RelativeLayout) itemView.findViewById(R.id.relative_map);
            relative_follow.setOnClickListener(this);
            relative_like.setOnClickListener(this);
            relative_map.setOnClickListener(this);
            icon_follow = (IconicsImageView) itemView.findViewById(R.id.icon_follow);
            icon_like = (IconicsImageView) itemView.findViewById(R.id.icon_like);
            icon_map = (IconicsImageView) itemView.findViewById(R.id.icon_map);
            like = false;
            follow = false;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onClick(View v) {

            final Animation resize_small = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.resize_small);
            final Animation resize_big = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.resize_big);

            switch (v.getId()){

                case R.id.relative_follow:

                    icon_follow.startAnimation(resize_big);
                    icon_follow.startAnimation(resize_small);

                    if (!follow) {
                        icon_follow.setColor(itemView.getResources().getColor(R.color.red));
                        icon_follow.startAnimation(resize_big);
                        icon_follow.startAnimation(resize_small);
                        follow = true;
                    }
                    else {
                        icon_follow.setColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
                        follow = false;
                    }
                    break;

                case R.id.relative_like:

                    icon_like.startAnimation(resize_big);
                    icon_like.startAnimation(resize_small);

                    if (!like) {
                        icon_like.setColor(itemView.getResources().getColor(R.color.blue));
                        like = true;
                    }
                    else {
                        icon_like.setColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
                        like = false;
                    }
                    break;

                case R.id.relative_map:
                    break;
            }
        }
    }

    @Override
    public HairViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homelist, parent, false);
        return new HairViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HairViewHolder holder, int position) {
        holder.name.setText(dressers.get(position).toString());
    }


    @Override
    public int getItemCount() {
        return dressers.size();
    }
}
