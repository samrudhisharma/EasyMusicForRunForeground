package easymusicforrun.easymusicforrun;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.net.Uri;

import android.app.PendingIntent;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.app.Notification;
import android.location.Location;
import android.widget.Toast;
import android.content.BroadcastReceiver;

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("*** Action: " + intent.getAction());
        if(intent.getAction().equalsIgnoreCase("android.net.conn.CONNECTIVITY_CHANGE")) {
            Toast.makeText(context, "Connection changed", Toast.LENGTH_SHORT).show();
        }
    }
}
