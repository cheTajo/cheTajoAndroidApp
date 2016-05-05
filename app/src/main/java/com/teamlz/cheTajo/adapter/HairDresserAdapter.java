package com.teamlz.cheTajo.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.mikepenz.iconics.view.IconicsImageView;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.object.HairDresser;
import com.teamlz.cheTajo.object.Utils;

import java.util.List;

/*
 * Created by francesco on 02/05/16.
 */

public class HairDresserAdapter extends RecyclerView.Adapter<HairDresserAdapter.HairViewHolder>{

    private List<HairDresser> dressers;

    public HairDresserAdapter(List<HairDresser> list){
        this.dressers = list;
    }

    public static class HairViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        protected TextView name;
        protected RelativeLayout relative_follow;
        protected RelativeLayout relative_like;
        protected RelativeLayout relative_map;
        protected IconicsImageView icon_follow;
        protected IconicsImageView icon_like;
        protected IconicsImageView icon_map;
        protected boolean like;
        protected boolean follow;
        protected TextView text_thumb;
        protected TextView text_follow;
        protected int num_thumbs;
        protected int num_follows;

        public HairViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.dresser_name);
            relative_follow = (RelativeLayout) itemView.findViewById(R.id.relative_follow);
            relative_like = (RelativeLayout) itemView.findViewById(R.id.relative_like);
            relative_map = (RelativeLayout) itemView.findViewById(R.id.relative_map);
            icon_follow = (IconicsImageView) itemView.findViewById(R.id.icon_follow);
            icon_like = (IconicsImageView) itemView.findViewById(R.id.icon_like);
            icon_map = (IconicsImageView) itemView.findViewById(R.id.icon_map);
            text_follow = (TextView) itemView.findViewById(R.id.num_follow);
            text_thumb = (TextView) itemView.findViewById(R.id.num_thumb);

        }

        @Override
        public void onClick(View v) {
            Log.d("Item", " Click");
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d("Long", " Click");
            Snackbar snackbar = null;

            snackbar = Snackbar.make(v, "Sei un Coglione", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Si è vero", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(v.getResources().getColor(R.color.colorRed));
            snackbar.show();
            return true;
        }
    }

    @Override
    public HairViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homelist, parent, false);
        return new HairViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HairViewHolder holder, final int position) {
        int greyColor = holder.itemView.getResources().getColor(R.color.colorGrey);
        int blueColor = holder.itemView.getResources().getColor(R.color.blue);
        int redColor = holder.itemView.getResources().getColor(R.color.colorRed);

        holder.name.setText(dressers.get(position).getFirstName() + " " + dressers.get(position).getLastName());

        //retrieve num follows and likes
        holder.num_follows = dressers.get(position).getNumFollowers();
        holder.num_thumbs = dressers.get(position).getNumLikes();
        holder.text_follow.setText(String.valueOf(holder.num_follows));
        holder.text_thumb.setText(String.valueOf(holder.num_thumbs));

        //retrieve my follows

        if (Utils.USERTHIS.getEmail() != null &&
                dressers.get(position).getFollowers().contains(Utils.USERTHIS.getEmail())) {
            holder.follow = true;
            holder.icon_follow.setColor(redColor);
        }

        else {
            holder.follow = false;
            holder.icon_follow.setColor(greyColor);
        }

        //retrieve my likes
        if (Utils.USERTHIS.getEmail() != null &&
                dressers.get(position).getLikes().contains(Utils.USERTHIS.getEmail())) {
            holder.like = true;
            holder.icon_like.setColor(blueColor);
        }

        else {
            holder.like = false;
            holder.icon_like.setColor(greyColor);
        }


        holder.relative_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickHolder(v.getId(), holder, position);
            }
        });

        holder.relative_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickHolder(v.getId(), holder, position);
            }
        });

        holder.relative_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickHolder(v.getId(), holder, position);
            }
        });
    }

    public void onItemClickHolder(int id, HairViewHolder holder, int position){

        switch (id){

            case R.id.relative_follow:
                clickFollow(holder,holder.follow, position);
                break;

            case R.id.relative_like:

                clickThumb(holder, holder.like, position);
                break;

            case R.id.relative_map:
                break;
        }
    }

    public void clickFollow(final HairViewHolder holder, final boolean pressed, int position){
        final Animation resize_small = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.resize_small);
        final Animation resize_big = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.resize_big);
        final int redColor = holder.itemView.getResources().getColor(R.color.colorRed);
        final int greyColor = holder.itemView.getResources().getColor(R.color.colorGrey);
        Firebase mFirebase = new Firebase(holder.itemView.getResources().getString(R.string.firebase_url));
        mFirebase = mFirebase.child(Utils.DRESSERS)
                    .child(dressers.get(position).getFirstName() + " " + dressers.get(position).getLastName());

        holder.icon_follow.startAnimation(resize_big);

        //mFirebase.child(Utils.FOLLOWERS).push().setValue(Utils.USERTHIS.getEmail());

        //mFirebase.child(Utils.NUM_FOLLOWERS).runTransaction(new Transaction.Handler()

            if (!pressed){
                holder.icon_follow.setColor(redColor);
                holder.num_follows += 1;
                holder.text_follow.setText(String.valueOf(holder.num_follows));
                holder.follow = true;
            }

            else {
                holder.icon_follow.setColor(greyColor);
                holder.num_follows -= 1;
                holder.text_follow.setText(String.valueOf(holder.num_follows));
                holder.follow = false;
            }

        holder.icon_follow.startAnimation(resize_small);
    }

    public void clickThumb(HairViewHolder holder, boolean pressed, int position){
        final Animation resize_small = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.resize_small);
        final Animation resize_big = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.resize_big);
        int blueColor = holder.itemView.getResources().getColor(R.color.blue);
        int greyColor = holder.itemView.getResources().getColor(R.color.colorGrey);

        holder.icon_like.startAnimation(resize_big);

        if (!pressed) {
            holder.icon_like.setColor(blueColor);
            holder.num_thumbs += 1;
            holder.text_thumb.setText(String.valueOf(holder.num_thumbs));
            holder.like = true;
        }
        else {
            holder.icon_like.setColor(greyColor);
            holder.num_thumbs -= 1;
            holder.text_thumb.setText(String.valueOf(holder.num_thumbs));
            holder.like = false;
        }

        holder.icon_like.startAnimation(resize_small);
    }


    @Override
    public int getItemCount() {
        return dressers.size();
    }
}
