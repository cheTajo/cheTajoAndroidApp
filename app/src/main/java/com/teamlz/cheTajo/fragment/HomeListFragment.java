package com.teamlz.cheTajo.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mikepenz.iconics.view.IconicsImageView;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.MainActivity;
import com.teamlz.cheTajo.object.HairDresser;
import com.teamlz.cheTajo.object.User;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/*
 * Created by francesco on 02/05/16.
 */
public class HomeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<HairDresser> hairDresserList;
    private String myId;
    private User myUser;

    private Firebase hairDresserFirebase;
    private Firebase userFirebase;

    public HomeListFragment(){}

    public static HomeListFragment newInstance(){
        return new HomeListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hairDresserList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_homelist, container, false);

        myId = MainActivity.id;
        Firebase myFirebase = new Firebase(getString(R.string.firebase_url));

        hairDresserFirebase = myFirebase.child("hairDressers");
        hairDresserFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    HairDresser hd = snapshot.getValue(HairDresser.class);
                    if (hairDresserList.contains(hd)) hairDresserList.remove(hd);
                    if (hd.getFollowers() == null) hd.initFollowers();
                    if (hd.getLikes() == null) hd.initLikes();
                    hairDresserList.add(hd);
                }
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        userFirebase = myFirebase.child("users").child(myId);
        userFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                myUser = snapshot.getValue(User.class);
                if (myUser.getFollowed() == null) myUser.initFollowed();
                if (myUser.getLiked() == null) myUser.initLiked();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new SlideInLeftAnimationAdapter(new RecyclerView.Adapter() {
            private int grey = getResources().getColor(R.color.colorGrey);
            private int blue = getResources().getColor(R.color.colorPrimaryDark);
            private int red = getResources().getColor(R.color.colorRed);

            private Animation resizeSmall = AnimationUtils.loadAnimation(getContext(), R.anim.resize_small);
            private Animation resizeBig = AnimationUtils.loadAnimation(getContext(), R.anim.resize_big);

            private Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Regular.ttf");

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_homelist, parent, false)) {
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                final IconicsImageView followIcon;
                final IconicsImageView likeIcon;

                final AppCompatTextView numLikeText;
                final AppCompatTextView numFollowText;

                final HairDresser myHd = hairDresserList.get(position);
                final Firebase myHdFirebase = hairDresserFirebase.child(myHd.getId());

                // Initialize shop name
                AppCompatTextView nameText = (AppCompatTextView) holder.itemView
                        .findViewById(R.id.home_list_item_shop_name);
                nameText.setText(myHd.getShopName());
                nameText.setTypeface(roboto);

                // Initialize hairdresser followers number
                numFollowText = (AppCompatTextView) holder.itemView
                        .findViewById(R.id.home_list_item_num_follow);
                numFollowText.setText(String.valueOf(myHd.getNumFollowers()));

                // Initialize hairdresser like number
                numLikeText = (AppCompatTextView) holder.itemView
                        .findViewById(R.id.home_list_item_num_like);
                numLikeText.setText(String.valueOf(myHd.getNumLikes()));

                // Initialize hairdresser follow icon
                followIcon = (IconicsImageView) holder.itemView
                        .findViewById(R.id.icon_follow);
                followIcon.setColor(grey);
                if (myHd.getFollowers() != null && myHd.getFollowers().contains(myId)) {
                    followIcon.setColor(red);
                }

                // Initialize hairdresser like icon
                likeIcon = (IconicsImageView) holder.itemView
                        .findViewById(R.id.icon_like);
                likeIcon.setColor(grey);
                if (myHd.getLikes() != null && myHd.getLikes().contains(myId)) {
                    likeIcon.setColor(blue);
                }

                RelativeLayout followLayout = (RelativeLayout) holder.itemView
                        .findViewById(R.id.relative_follow);
                followLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        followIcon.startAnimation(resizeBig);

                        if (followIcon.getIcon().getColor() == grey) {
                            followIcon.setColor(red);
                            myUser.addFollowed(myHd.getId());
                            myHd.addFollower(myId);
                        }

                        else {
                            followIcon.setColor(grey);
                            myUser.removeFollowed(myHd.getId());
                            myHd.removeFollower(myId);
                        }

                        userFirebase.child("followed").setValue(myUser.getFollowed());
                        myHdFirebase.child("followers").setValue(myHd.getFollowers());
                        myHdFirebase.child("numFollowers").setValue(myHd.getNumFollowers());
                        numFollowText.setText(String.valueOf(myHd.getNumFollowers()));
                        followIcon.startAnimation(resizeSmall);
                        //onItemClickHolder(v.getId(), holder);
                    }
                });

                RelativeLayout likeLayout = (RelativeLayout) holder.itemView
                        .findViewById(R.id.relative_like);
                likeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeIcon.startAnimation(resizeBig);

                        if (likeIcon.getIcon().getColor() == grey) {
                            likeIcon.setColor(blue);
                            myUser.addLiked(myHd.getId());
                            myHd.addLike(myId);
                        }

                        else {
                            likeIcon.setColor(grey);
                            myUser.removeLiked(myHd.getId());
                            myHd.removeLike(myId);
                        }

                        userFirebase.child("liked").setValue(myUser.getLiked());
                        myHdFirebase.child("likes").setValue(myHd.getLikes());
                        myHdFirebase.child("numLikes").setValue(myHd.getNumLikes());
                        numLikeText.setText(String.valueOf(myHd.getNumLikes()));
                        likeIcon.startAnimation(resizeSmall);
                    }
                });

                RelativeLayout mapLayout = (RelativeLayout) holder.itemView
                        .findViewById(R.id.relative_map);
                mapLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }

            @Override
            public int getItemCount() {
                return hairDresserList.size();
            }
        }));

        HomeFragment.fab_add.attachToRecyclerView(mRecyclerView);
        return view;
    }
}