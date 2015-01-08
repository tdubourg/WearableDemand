package com.androidweardocs.demand;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by michaelHahn on 1/6/15.
 * Wearable demand sample
 */
public class DemandActivity extends ActionBarActivity {

    public static final String ACTION_DEMAND = "com.androidweardocs.ACTION_DEMAND";
    public static final String EXTRA_MESSAGE = "com.androidweardocs.EXTRA_MESSAGE";
    public static final String EXTRA_VOICE_REPLY = "com.androidweardocs.EXTRA_VOICE_REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand);

        // Create an Intent for the demand
        Intent demandIntent = new Intent(this, DemandIntentReceiver.class)
                .putExtra(EXTRA_MESSAGE, "Reply icon selected.")
                .setAction(ACTION_DEMAND);

        // Create a pending intent using the local broadcast receiver
        PendingIntent demandPendingIntent =
                PendingIntent.getBroadcast(this, 0, demandIntent, 0);

        // Create RemoteInput object for a voice reply (demand)
        String replyLabel = getResources().getString(R.string.app_name);
        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .build();

        // Create a wearable action
        NotificationCompat.Action replyAction =
                new NotificationCompat.Action.Builder(R.drawable.ic_reply_icon,
                        getString(R.string.reply_label), demandPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        // Create a wearable extender for a notification
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .addAction(replyAction);

        // Create a notification and extend it for the wearable
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Hello Wearable!")
                        .setContentText("First wearable demand.")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .extend(wearableExtender)
                        .build();
        // Get the notification manager
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Dispatch the extended notification
        int notificationId = 1;
        notificationManager.notify(notificationId, notification);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demand, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
