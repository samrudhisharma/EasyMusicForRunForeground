package easymusicforrun.easymusicforrun;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.net.Uri;

import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.net.ConnectivityManager;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.ConnectionResult;
import android.support.annotation.NonNull;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.awareness.snapshot.DetectedActivityResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import android.media.MediaPlayer;

import easymusicforrun.easymusicforrun.IContextCallback;
import easymusicforrun.easymusicforrun.IContextInterface;



public class MusicPlaying extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SensorManager sensorManager;
    private NetworkChangedReceiver mConnReceiver;
    private final String CONNECTIVITY = "android.net.conn.CONNECTIVITY_CHANGE";
    private GoogleApiClient mGoogleApiClient;
    private final static int REQUEST_PERMISSION_RESULT_CODE = 42;
    private MediaPlayer mp;

    protected IContextInterface contextService;
    protected ServiceConnection contextServiceConnection;
    private IContextCallback.Stub mContextCallback;
    private boolean mContextIsBound;

    // conditional variables
    boolean speed_condition = false;
    boolean connectivity_condition =  false;
    boolean headset_condition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplaying);

        registerReceiver();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Awareness.API)
                .enableAutoManage(this, this)
                .build();
        mGoogleApiClient.connect();

        detectActivity();
    }

    @Override
    public void onStart(){
        super.onStart();

        contextServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                contextService = IContextInterface.Stub.asInterface(service);
                System.out.println("Service Connected");
                Toast.makeText(getApplicationContext(),
                        "Context Service Connected", Toast.LENGTH_SHORT)
                        .show();
                Log.d("IApp", "Binding is done - Context Service connected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                // TODO Auto-generated method stub
                contextService = null;
                System.out.println("Service DIsConnected");
                Toast.makeText(getApplicationContext(), "Service Disconnected",
                        Toast.LENGTH_SHORT).show();
                Log.d("IRemote", "Binding - Location Service disconnected");
            }
        };
        if (contextService == null) {
            Intent contextIntent = new Intent("easymusicforrun.easymusicforrun.service.ContextMiddlewareService.RUN");
            contextIntent.setPackage("easymusicforrun.easymusicforrun.service");
//            contextIntent.setAction("easymusicforrun.easymusicforrun.service.ContextMiddlewareService.RUN");
            ComponentName componentName = startService(contextIntent);
            boolean flag = bindService(contextIntent, contextServiceConnection, Context.BIND_AUTO_CREATE);
            System.out.println(componentName + "@SNS Muni" + flag);
            mContextIsBound = true;
            if(contextService==null)
                Log.d("locService","NULL");
        }

    }

    protected void onDestroy() {
        super.onDestroy();
//        if(addServiceConnection != null)
//            unbindService(addServiceConnection);
        if(contextServiceConnection != null)
            unbindService(contextServiceConnection);
    };


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }



    public void checkHeadSetStatus() {


            try {
                if(mContextIsBound) {
                    // Sync call to service funtion. Response handled immediately.
                    if(contextService.isJackPluggedIn()) {

                        System.out.println("Headset is plugged");
                        headset_condition = true;

                        //Running, wifi connected, headphones connected
                        if (speed_condition && headset_condition && connectivity_condition) {
                            //TODO: Add Channel Code
                            watchYoutubeVideo(Constants.youtubeVideoIdForRunning);
                        } else if(speed_condition && headset_condition) {
                            playLocalStoredClip(Constants.localPlaylistClipName);
                        }

                    }

                    else {

                        System.out.println("Headset is unplugged");
                        headset_condition = false;
                        //Running, wifi connected, headphones connected
                        if (speed_condition && connectivity_condition) {
                            Toast.makeText(MusicPlaying.this,
                                    "Headset is Unplugged", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            } catch (RemoteException e){
                e.printStackTrace();
            }
        }


    private void registerReceiver() {
        mConnReceiver = new NetworkChangedReceiver();
        registerReceiver(mConnReceiver, new IntentFilter(CONNECTIVITY));
    }

    private void internetChanged(boolean networkAvailable) {

        System.out.println("Internet Changed");
        if (networkAvailable) {
            System.out.println("Internet Connected");
            connectivity_condition = true;
            if(headset_condition && speed_condition) {
                watchYoutubeVideo(Constants.youtubeVideoIdForRunning);
            }

        } else {
            System.out.println("Internet Not Connected");
            Toast.makeText(MusicPlaying.this,
                    "Internet Disconnected. Please Connect the Wifi and LTE.", Toast.LENGTH_LONG).show();
            connectivity_condition = false;
            playLocalStoredClip(Constants.localPlaylistClipName);
        }
    }


    private class NetworkChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context ctx, Intent intent) {
            internetChanged(isNetworkAvailable(ctx));
        }

        // Checks if Internet is Online via Wifi or Mobile
        public boolean isNetworkAvailable(Context context) {
            boolean isMobile = false, isWifi = false;

            NetworkInfo[] infoAvailableNetworks = getConnectivityManagerInstance(context).getAllNetworkInfo();

            if (infoAvailableNetworks != null) {
                for (NetworkInfo network : infoAvailableNetworks) {

                    if (network.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (network.isConnected() && network.isAvailable())
                            isWifi = true;
                    }
                    if (network.getType() == ConnectivityManager.TYPE_MOBILE) {
                        if (network.isConnected() && network.isAvailable())
                            isMobile = true;
                    }
                }
            }

            return isMobile || isWifi;
        }

        private ConnectivityManager getConnectivityManagerInstance(Context context) {
            return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }

    }


    //Awareness API related code
    private boolean checkLocationPermission() {
        if (!hasLocationPermission()) {
            System.out.println("Does not have location permission granted");
            requestLocationPermission();
            return false;
        }

        return true;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                MusicPlaying.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSION_RESULT_CODE);
    }


    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_RESULT_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    System.out.println("Location permission denied.");
                }
            }
        }
    }

    private void detectActivity() {
        Awareness.SnapshotApi.getDetectedActivity(mGoogleApiClient)
                .setResultCallback(new ResultCallback<DetectedActivityResult>() {
                    @Override
                    public void onResult(@NonNull DetectedActivityResult detectedActivityResult) {
                        ActivityRecognitionResult result = detectedActivityResult.getActivityRecognitionResult();
                        System.out.println("time: " + result.getTime());
                        System.out.println("elapsed time: " + result.getElapsedRealtimeMillis());
                        System.out.println("Most likely activity: " + result.getMostProbableActivity().toString());

                        for (DetectedActivity activity : result.getProbableActivities()) {
                            System.out.println("Activity: " + activity.getType() + " Likelihood: " + activity.getConfidence());
                            if(activity.getType() == 8 && activity.getConfidence() >= 60) {
                                checkHeadSetStatus();
                                speed_condition = true;
                                if(headset_condition && connectivity_condition) {
                                    watchYoutubeVideo(Constants.youtubeVideoIdForRunning);
                                }

                            } else if(activity.getType() == 7 && activity.getConfidence() >= 60) {
                                Toast.makeText(MusicPlaying.this,
                                        "Your are walking, music will slow down", Toast.LENGTH_LONG).show();
                                checkHeadSetStatus();
                                if(headset_condition && connectivity_condition) {
                                    watchYoutubeVideo(Constants.youtubeVideoIdForWalking);
                                }
                            }
                            else if(activity.getType() == 3 && activity.getConfidence() >= 60) {

                                //For Debugging
                                watchYoutubeVideo(Constants.youtubeVideoIdForRunning);
                                System.out.println(Constants.youtubeVideoIdForRunning+"SSKSKSKSKS");

                                Toast.makeText(MusicPlaying.this,
                                        "Your are stationary please begin walking", Toast.LENGTH_LONG).show();

                            } else {
                                checkHeadSetStatus();
                                if(headset_condition && connectivity_condition) {
                                    watchYoutubeVideo(Constants.youtubeVideoIdForRunning);
                                }
                            }
                        }
                    }
                });
    }

    private void playLocalStoredClip(String clipName) { //Replace with playlist name

        if (mp != null) {
            mp.release();
        }


        if(clipName == "clip1") {
            // Create a new MediaPlayer to play this sound
            mp = MediaPlayer.create(this, R.raw.clip1);
            mp.start();
        } else {
            mp = MediaPlayer.create(this, R.raw.clip2);
            mp.start();
        }
    }

}
