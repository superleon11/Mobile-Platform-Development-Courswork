package s1316134.mpd.bgsdatastarter;


//
// Name                 Jordan O'Donnell
// Student ID           s1316134
// Programme of Study   Computing
//

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView textView = findViewById(R.id.txtview);


        Spannable wordtoSpan = new SpannableString("Magnitude Key: Green = Below 1. Yellow = Between 1 and 2. Red = Over 2. s1316134");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(android.graphics.Color.GREEN), 14, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.YELLOW), 32, 58, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 58, 71, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(wordtoSpan);
        textView.setBackgroundColor(Color.GRAY);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 4.6f;
        Bundle extras = getIntent().getExtras();
        for(int i =0; i<extras.size()/7; i++){

            String Location = extras.getString(i + "Location");
            Double magnitude = extras.getDouble(i+ "Magnitude");
            String date = extras.getString(i + "date");
            String depth = extras.getString(i + "depth");
            Double lat = extras.getDouble(i+ "lat");
            Double glong = extras.getDouble(i + "geolong");
            String colour = extras.getString(i + "colour");
            String title = (Location + "Magnitude " +magnitude.toString());
            String snippet = ("Date: " + date + ". Depth: " + depth);
            LatLng uk = new LatLng(lat, glong);

            if(colour.equals("green")) {
                mMap.addMarker(new MarkerOptions().position(uk).title(title).snippet(snippet).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            else if (colour.equals("yellow")) {
                mMap.addMarker(new MarkerOptions().position(uk).title(title).snippet(snippet).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }
            else if(colour.equals("red")) {
                mMap.addMarker(new MarkerOptions().position(uk).title(title).snippet(snippet).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }else{
                mMap.addMarker(new MarkerOptions().position(uk).title(title).snippet(snippet).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }

        }

        LatLng uk = new LatLng(54.8925, -1.504);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uk, zoomLevel));

    }
}
