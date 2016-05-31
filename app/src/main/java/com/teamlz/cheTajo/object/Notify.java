package com.teamlz.cheTajo.object;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.content.Context;

import com.teamlz.cheTajo.activity.SplashActivity;

public class Notify{
    private int follower;

    public Notify(int follower){
        this.follower=follower;
    }
    public void sendNotification(View view) {

        // Use NotificationCompat.Builder to set up our notification.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext());

        //icon appears in device notification bar and right hand corner of notification
        builder.setSmallIcon(android.R.drawable.btn_star_big_on);

        // This intent is fired when notification is clicked
        Intent intent = new Intent(view.getContext(), SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(view.getContext(), 0, intent, 0);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Large icon appears on the left of the notification
        builder.setLargeIcon(BitmapFactory.decodeFile("@drawable/che_tajo_icon_red.png"));

        // Content title, which appears in large type at the top of the notification
        builder.setContentTitle("CheTajo: nuovo follower");

        // Set sound
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(sound);

        // Content text, which appears in smaller text below the title
        if( follower > 1) builder.setContentText(follower + " nuovi utenti hanno iniziato a seguirti");
        else builder.setContentText(" un nuovo utente ha iniziato a seguirti");

        // The subtext, which appears under the text on newer devices.
        // This will show-up in the devices with Android 4.2 and above only
        builder.setSubText("Tap to view home");

        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(0, builder.build());


    }
}