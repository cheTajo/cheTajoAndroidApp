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
@SuppressWarnings("deprecation")
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
            private int red = getResources().getColor(R.color.colorRed);

            private Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Regular.ttf");

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_homelist, parent, false)) {
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final HairDresser myHd = hairDresserList.get(position);
                final Firebase myHdFirebase = hairDresserFirebase.child(myHd.getId());

                // Initialize shop name
                AppCompatTextView nameText = (AppCompatTextView) holder.itemView
                        .findViewById(R.id.home_list_item_shop_name);
                nameText.setText(myHd.getShopName());
                nameText.setTypeface(roboto);

                // Initialize hairdresser followers number
                final AppCompatTextView numFollowText = (AppCompatTextView) holder.itemView
                        .findViewById(R.id.home_list_item_num_follow);
                numFollowText.setText(String.valueOf(myHd.getFollowers().size()));

                // Initialize hairdresser follow icon
                final IconicsImageView followIcon = (IconicsImageView) holder.itemView
                        .findViewById(R.id.icon_follow);
                followIcon.setColor(grey);
                if (myHd.getFollowers() != null && myHd.getFollowers().contains(myId)) {
                    followIcon.setColor(red);
                }

                final AppCompatTextView textFollow = (AppCompatTextView) holder.itemView
                        .findViewById(R.id.follow_text);
                textFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textFollow.setClickable(false);

                        if (followIcon.getIcon().getColor() == grey) {
                            followIcon.setColor(red);
                            if (!myUser.getFollowed().contains(myHd.getId()))
                                myUser.addFollowed(myHd.getId());
                            if (!myHd.getFollowers().contains(myId))
                                myHd.addFollower(myId);
                        }

                        else {
                            followIcon.setColor(grey);
                            myUser.removeFollowed(myHd.getId());
                            myHd.removeFollower(myId);
                        }

                        userFirebase.child("followed").setValue(myUser.getFollowed());
                        myHdFirebase.child("followers").setValue(myHd.getFollowers());
                        numFollowText.setText(String.valueOf(myHd.getFollowers().size()));
                        textFollow.setClickable(true);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return hairDresserList.size();
            }
        }));
        return view;
    }
}