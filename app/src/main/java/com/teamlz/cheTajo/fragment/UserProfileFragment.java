package com.teamlz.cheTajo.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.object.HairDresser;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

@SuppressWarnings("deprecation")
public class UserProfileFragment extends Fragment{
    private String uid;

    private View view;
    private AppCompatEditText shopNameText;
    private AppCompatEditText addressText;

    private DatabaseReference hdFirebase;
    private DatabaseReference userFirebase;

    public UserProfileFragment() {
        // Required empty public constructor
    }
    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        shopNameText = (AppCompatEditText) view.findViewById(R.id.hd_sign_up_shop_name);
        addressText = (AppCompatEditText) view.findViewById(R.id.hd_sign_up_address);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert (user != null);
        uid = user.getUid();

        FirebaseDatabase myFirebase = FirebaseDatabase.getInstance();
        hdFirebase = myFirebase.getReference("hairDressers").child(uid);
        userFirebase = myFirebase.getReference("users").child(uid);

        AppCompatImageView profileImageView = (AppCompatImageView) view.findViewById(R.id.user_profile_image);
        String id = user.getProviderData().get(1).getUid();
        String url = "https://graph.facebook.com/" + id + "/picture?type=large";
        Drawable image = null;
        try {
            InputStream is = (InputStream) new URL(url)
                    .getContent();
            image = Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        profileImageView.setImageDrawable(image);

        AppCompatButton hdSignUpButton = (AppCompatButton) view.findViewById(R.id.hd_sign_up_button);
        hdSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(view);

                String shopName = shopNameText.getText().toString();
                String address = addressText.getText().toString();

                if(shopName.equals("")) {
                    Toast.makeText(getActivity(), "Compila i campi obbligatori", Toast.LENGTH_LONG).show();
                    return;
                }

                HairDresser hd = new HairDresser(uid, shopName);
                if(!address.equals("")) {
                    LatLng latLng = getLatLng(getContext(), address);
                    if (latLng != null) {
                        hd.setLatitude(latLng.latitude);
                        hd.setLongitude(latLng.longitude);
                    }
                }
                hdFirebase.setValue(hd);
                userFirebase.child("hairDresser").setValue(true);
            }
        });

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.user_profile_toolbar);
        toolbar.setTitle(user.getDisplayName());

        AppCompatTextView emailTextView = (AppCompatTextView) view.findViewById(R.id.user_profile_card_email);
        emailTextView.setText(user.getEmail());

        FloatingActionButton fab_settings = (FloatingActionButton) view.findViewById(R.id.fab_settings);
        fab_settings.setImageDrawable(new IconicsDrawable(this.getActivity(), "gmd-create")
                .sizeDp(18)
                .color(getResources().getColor(R.color.white)));
        return view;
    }

    public void hideKeyboard(View view) {
        if(getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public LatLng getLatLng(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return p1;
    }
}