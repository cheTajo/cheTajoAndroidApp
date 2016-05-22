package com.teamlz.cheTajo.object;

import android.content.Context;

import com.firebase.client.Firebase;
import com.teamlz.cheTajo.R;

/*
 * Created by francesco on 05/05/16.
 */
public class Utils {

    public static void addHairDresser(Context context){
        Firebase myFirebase = new Firebase(context.getResources().getString(R.string.firebase_url));

        HairDresser myHd = new HairDresser("52f0091c-74bb-4552-a4aa-46a582728c20", "Un Angelo per capello");
        /*myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");*/
        myHd.setLatitude(40.8253799);
        myHd.setLongitude(16.5272887);
        myFirebase.child("hairDressers").child("52f0091c-74bb-4552-a4aa-46a582728c20").setValue(myHd);

        myHd = new HairDresser("79b451ed-fb1c-4282-8178-1d7582274a8f", "Scialla Shampoo");
        /*myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");*/
        myHd.setLatitude(70);
        myHd.setLongitude(8);
        myFirebase.child("hairDressers").child("79b451ed-fb1c-4282-8178-1d7582274a8f").setValue(myHd);

        myHd = new HairDresser("7b2a5b26-690b-46c6-88d6-470bb74f342e", "Piccina Fashonist");
        /*myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");*/
        myHd.setLatitude(37.8253799);
        myHd.setLongitude(16.5272887);
        myFirebase.child("hairDressers").child("7b2a5b26-690b-46c6-88d6-470bb74f342e").setValue(myHd);

        myHd = new HairDresser("Prova01", "Figaro Qua");
        /*myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");*/
        myHd.setLatitude(12);
        myHd.setLongitude(15);
        myFirebase.child("hairDressers").child("Prova01").setValue(myHd);

        myHd = new HairDresser("Prova02", "Figaro Là");
        /*myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");*/
        myHd.setLatitude(1);
        myHd.setLongitude(20);
        myFirebase.child("hairDressers").child("Prova02").setValue(myHd);

        myHd = new HairDresser("Prova03", "Figaro Su");
        /*myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");*/
        myHd.setLatitude(1);
        myHd.setLongitude(2);
        myFirebase.child("hairDressers").child("Prova03").setValue(myHd);

        myHd = new HairDresser("Prova04", "Figaro Giù");
        /*myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");*/
        myHd.setLatitude(72);
        myHd.setLongitude(40);
        myFirebase.child("hairDressers").child("Prova04").setValue(myHd);

        myHd = new HairDresser("Prova05", "Sfigato Di Merda");
        myHd.setLatitude(12);
        myHd.setLongitude(23);
        myFirebase.child("hairDressers").child("Prova05").setValue(myHd);
    }
}
