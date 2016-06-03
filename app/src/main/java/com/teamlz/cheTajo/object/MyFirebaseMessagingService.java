package com.teamlz.cheTajo.object;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.SplashActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /*
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }
    // [END receive_message]

    /*
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(View view, int follower) {
        // Use NotificationCompat.Builder to set up our notification.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext());

        // This intent is fired when notification is clicked
        Intent intent = new Intent(view.getContext(), SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(view.getContext(), 0, intent, 0);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Large icon appears on the left of the notification
        BitmapDrawable d = (BitmapDrawable) view.getContext().getDrawable(R.drawable.che_tajo_icon_red);
        builder.setLargeIcon(d.getBitmap());


        //icon appears in device notification bar and right hand corner of notification
        builder.setSmallIcon(R.drawable.che_tajo_icon_red);


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
        Notification noty = builder.build();

        int smallIconId = view.getContext().getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
        if (smallIconId != 0) {
            noty.contentView.setViewVisibility(smallIconId, View.INVISIBLE);
        }

        // Will display the notification in the notification bar
        notificationManager.notify(0, noty);
    }
}