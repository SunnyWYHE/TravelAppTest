package com.hsbc.eep.tavel.test_location;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements LocationListener {
    static final int MIN_TIME = 5000;  //time interval for location refresh (in ms)
    static final float MIN_DIST = 5;  //distance for location refresh (in meter)
    LocationManager locMgr;
    TextView txv;
    TextView locProviderTxv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txv = (TextView) findViewById(R.id.txv);
        locProviderTxv = (TextView) findViewById(R.id.provider);
        locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //get the best location provided (find the provided already enabled
        String bestProvider = locMgr.getBestProvider(new Criteria(), true);
        locProviderTxv.setText(bestProvider);

        if (bestProvider != null) {
            txv.setText("Getting location information...");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locMgr.requestLocationUpdates(bestProvider, MIN_TIME, MIN_DIST, this); //register location event monitor

        } else {
            txv.setText("Please make sure the GPS is enabled");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locMgr.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        String str = String.format("\nLatitude : %.5f\nLongitude : %.5f\nHeight : %.2f",
                location.getLatitude(),
                location.getLongitude(),
                location.getAltitude());
        txv.setText(str);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void setup(View v) {
        Intent it = new Intent (Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(it);
    }
}
