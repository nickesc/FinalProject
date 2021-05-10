package com.example.finalproject;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static AsyncHttpClient asyncClient = new AsyncHttpClient();
    private ArrayList<Groupbuy> groupbuys = new ArrayList<Groupbuy>();
    private ArrayList<Interest> interests = new ArrayList<Interest>();

    private Button groupbuyButton, interestButton, nearbyButton;
    private FragmentContainerView mainFragmentView;

    private String gbUrl = "http://10.0.2.2:5001/gb";
    private String icUrl = "http://10.0.2.2:5001/ic";

    private static final int REQUEST_LOCATION=1;
    private LocationManager locationManager;
    private Location location;
    private String usState;
    private Geocoder geocoder;

    private FusedLocationProviderClient fusedLocationClient;

    private Map<String, String> states;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geocoder=new Geocoder(this);
        setStates();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        groupbuyButton = findViewById(R.id.button_groupbuy);
        interestButton = findViewById(R.id.button_interest);
        nearbyButton = findViewById(R.id.button_nearby);

        groupbuyButton.setOnClickListener(v-> loadGroupbuy(new FragmentGroupbuy(),gbUrl));
        interestButton.setOnClickListener(v-> loadGroupbuy(new FragmentInterest(),icUrl));
        nearbyButton.setOnClickListener(v-> loadNearby(new FragmentNearby()));
        loadGroupbuy(new FragmentGroupbuy(),gbUrl);





/*
        asyncClient.get(icUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray json = new JSONArray(new String(responseBody));

                    for (int i=0; i<json.length();i++){
                        Interest interest=new Interest(json.getJSONObject(i));
                        Log.d("help",interest.getName());
                        interests.add(interest);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("help","oops");
            }
        });

 */
    }

    public void loadGroupbuy(Fragment fragment, String url){

        asyncClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("help","working?");
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("response",responseBody);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragview_main, fragment);
                    fragmentTransaction.commit();
                    /*
                    JSONArray json = new JSONArray(new String(responseBody));

                    for (int i=0; i<json.length();i++){
                        Groupbuy groupbuy=new Groupbuy(json.getJSONObject(i));
                        Log.d("help",groupbuy.getName());
                        groupbuys.add(groupbuy);
                    }

                     */


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("help","oops");
            }
        });




    }
    public void loadNearby(Fragment fragment){

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled((LocationManager.GPS_PROVIDER))) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
        else{
            getLocation(fragment);
        }




    }

    public void getLocation(Fragment fragment){
        Log.d("help","good");
        if  (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else{
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Address newLoc= null;
                                try {
                                    newLoc = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1).get(0);
                                    Log.d("help",""+states.get(newLoc.getAdminArea()));
                                    usState=""+states.get(newLoc.getAdminArea());
                                    Log.d("help",usState);


                                    int i =0;
                                    Bundle bundle = new Bundle();
                                    bundle.putString("state",usState);
                                    fragment.setArguments(bundle);
                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragview_main, fragment);
                                    fragmentTransaction.commit();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            else{
                                Log.d("help","huh");
                            }
                        }
                    });
        }

    }
    public void setStates(){
        states = new HashMap<String, String>();
        states.put("Alabama","AL");
        states.put("Alaska","AK");
        states.put("Alberta","AB");
        states.put("American Samoa","AS");
        states.put("Arizona","AZ");
        states.put("Arkansas","AR");
        states.put("Armed Forces (AE)","AE");
        states.put("Armed Forces Americas","AA");
        states.put("Armed Forces Pacific","AP");
        states.put("British Columbia","BC");
        states.put("California","CA");
        states.put("Colorado","CO");
        states.put("Connecticut","CT");
        states.put("Delaware","DE");
        states.put("District Of Columbia","DC");
        states.put("Florida","FL");
        states.put("Georgia","GA");
        states.put("Guam","GU");
        states.put("Hawaii","HI");
        states.put("Idaho","ID");
        states.put("Illinois","IL");
        states.put("Indiana","IN");
        states.put("Iowa","IA");
        states.put("Kansas","KS");
        states.put("Kentucky","KY");
        states.put("Louisiana","LA");
        states.put("Maine","ME");
        states.put("Manitoba","MB");
        states.put("Maryland","MD");
        states.put("Massachusetts","MA");
        states.put("Michigan","MI");
        states.put("Minnesota","MN");
        states.put("Mississippi","MS");
        states.put("Missouri","MO");
        states.put("Montana","MT");
        states.put("Nebraska","NE");
        states.put("Nevada","NV");
        states.put("New Brunswick","NB");
        states.put("New Hampshire","NH");
        states.put("New Jersey","NJ");
        states.put("New Mexico","NM");
        states.put("New York","NY");
        states.put("Newfoundland","NF");
        states.put("North Carolina","NC");
        states.put("North Dakota","ND");
        states.put("Northwest Territories","NT");
        states.put("Nova Scotia","NS");
        states.put("Nunavut","NU");
        states.put("Ohio","OH");
        states.put("Oklahoma","OK");
        states.put("Ontario","ON");
        states.put("Oregon","OR");
        states.put("Pennsylvania","PA");
        states.put("Prince Edward Island","PE");
        states.put("Puerto Rico","PR");
        states.put("Quebec","PQ");
        states.put("Rhode Island","RI");
        states.put("Saskatchewan","SK");
        states.put("South Carolina","SC");
        states.put("South Dakota","SD");
        states.put("Tennessee","TN");
        states.put("Texas","TX");
        states.put("Utah","UT");
        states.put("Vermont","VT");
        states.put("Virgin Islands","VI");
        states.put("Virginia","VA");
        states.put("Washington","WA");
        states.put("West Virginia","WV");
        states.put("Wisconsin","WI");
        states.put("Wyoming","WY");
        states.put("Yukon Territory","YT");
    }
}