package com.example.satya.onlinehunt;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.stats.StatsEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.security.Security;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.CancelableCallback {
    TextView result;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    GoogleMap mGoogleMap;
    public static double latt, lngg;
    public boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getApplicationContext().getSharedPreferences("pref",0);
        editor = pref.edit();
        setContentView(R.layout.activity_main);
        if (googleServicesAvailable()) {
            Toast.makeText(this, "Loading....", Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("EnableLocation");
            builder.setIcon(android.R.drawable.ic_menu_mylocation);
            builder.setMessage("Google Map requires your current Location");
            builder.setPositiveButton("OK", null);
            builder.show();
            latt = 16.97;
            lngg = 80.756;
            initMap();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        flag=false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        flag=true;
    }
    @Override
    protected void onStop() {
        super.onStop();
        flag = false;
    }
    public void initMap() throws SecurityException {
        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);
        final LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
            Marker mMap = null;
            @Override
           public void onLocationChanged(Location getLocation) {
                MarkerOptions opt = new MarkerOptions();
                if (mMap == null) {
                    mMap = mGoogleMap.addMarker(opt.position(new LatLng(getLocation.getLatitude(), getLocation.getLongitude())));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(getLocation.getLatitude(), getLocation.getLongitude()), 15));
                } else {
                    mMap.setPosition(new LatLng(getLocation.getLatitude(), getLocation.getLongitude()));
                    LatLng latLng = new LatLng(getLocation.getLongitude(),getLocation.getLongitude());
                    if(flag){
                       // Toast.makeText(MainActivity.this, "Long" +getLocation.getLongitude()+"Latt" +getLocation.getLatitude()+flag, Toast.LENGTH_LONG).show();
                    }
                        double lat1 = getLocation.getLatitude();
                        double lng1 = getLocation.getLongitude();
                    if (flag==true && distance(lat1, lng1,17.36978, 78.52017)<0.0009143) {
                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                        myAlertBuilder.setTitle("Hostel at Dilshuk Nagar");
                        myAlertBuilder.setPositiveButton("Got it !", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                }
                        });
                        myAlertBuilder.show();
                    }
                        /*Toast t=Toast.makeText(MainActivity.this, "WELCOME"+flag, Toast.LENGTH_SHORT);
                        t.show();*/
                    else if (distance(lat1,lng1,17.369066, 78.520171)<0.0009143){
                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                        myAlertBuilder.setTitle("Main Road near Dilshuk Nagar");
                        myAlertBuilder.setPositiveButton("Got it !", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        myAlertBuilder.show();
                    }
                    else if (distance(lat1,lng1,17.425719, 78.453706)<0.0009143){
                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                        myAlertBuilder.setTitle("Adithya Apartments");
                        myAlertBuilder.setPositiveButton("Got it !", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        myAlertBuilder.show();
                    }
                    else if (distance(lat1,lng1,17.425497, 78.453254)<0.0009143){
                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                        myAlertBuilder.setTitle("Main road");
                        myAlertBuilder.setPositiveButton("Got it !", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        myAlertBuilder.show();
                    }
                }
             }
            private double distance(double lat1,double lng1,double lat2,double lng2){
                double earthRadius=3958.75;
                double dLat=Math.toRadians(lat2-lat1);
                double dLng=Math.toRadians(lng2-lng1);
                double sindLat=Math.sin(dLat / 2);
                double sindLng=Math.sin(dLng / 2);
                double a=Math.pow(sindLat,2)+Math.pow(sindLng,2)*Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2));
                double c=2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
                double dist=earthRadius*c;
                // TO show the disance between your location and clue location
                //Toast.makeText(MainActivity.this,"Distance is "+dist,Toast.LENGTH_LONG).show();
                return dist;
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        });
    }
    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }
    @Override
    public void onFinish() {
    }
    @Override
    public void onCancel() {
    }
    public void onLocationChanged(Location Location) {
        Marker mCurrLocationMarker = null;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        LatLng latLng = new LatLng(Location.getLatitude(), Location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.unnamed));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        Toast.makeText(this, "Location Changed", Toast.LENGTH_SHORT).show();
        double lat1 = Location.getLatitude();
        double lng1 = Location.getLongitude();
        //Toast.makeText(MainActivity.this, "Current location latitude and longitude"+ lat1+ lng1, Toast.LENGTH_SHORT).show();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
        LatLng current = new LatLng(lat1,lng1);
        GoogleMap mMap = null;
        mMap.addMarker(new MarkerOptions()
                .position(current)
                .title("Marker in current position"));
        //Toast.makeText(this,"Current location lat and long" +lat1 +lng1,Toast.LENGTH_SHORT).show();
    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return super.onCreateOptionsMenu(menu);
        }
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case R.id.quit_game:
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                    myAlertBuilder.setTitle("Are you sure you want to quit??");
                    myAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "loading...", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    myAlertBuilder.show();
                    break;
                case R.id.logout:
                        editor.putString("id",null);
                    Intent satya = new Intent(MainActivity.this,Login.class);
                    startActivity(satya);
            }
            return super.onOptionsItemSelected(item);
        }
    }