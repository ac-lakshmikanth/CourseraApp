package dailyselfie.labs.coursera.dailyselfieapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;
import android.widget.Toast;


public class DailySelfieAlarmActivity extends ActionBarActivity {

    private final int MY_NOTIFICATION_ID = 11151990;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getApplicationContext(),"alarm",Toast.LENGTH_LONG).show();

       // RemoteViews contentView = new RemoteViews(getPackageName(),R.layout.activity_daily_selfie_notification);

        final Intent appStartIntent = new Intent(getApplicationContext(),DailySelfieMain.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),0,appStartIntent,0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext())
                //.setTicker("Ticket Text")
                .setContentTitle("Daily Selfie")
                .setContentText("Time for another selfie")
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setAutoCancel(true)
                //.setContent(contentView)
                .setContentIntent(contentIntent);

        notificationManager.notify(MY_NOTIFICATION_ID,notificationBuilder.build());
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daily_selfie_alarm, menu);
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
