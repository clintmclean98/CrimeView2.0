package t.flatearchsocie.crimeview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCircleClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.sql.SQLException;
import java.util.ArrayList;

import static android.graphics.Color.TRANSPARENT;

public class SeverityIdicator extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
DatabaseHandler databaseHandler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_severity_idicator);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





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


        databaseHandler = DatabaseHandler.getInstance();

        try {
            ArrayList<Location> locations = databaseHandler.getLocations();
            ArrayList<Crime> crimes = databaseHandler.getCrimeDetails();


            for (Crime cur: crimes ) {

                for (Location curLoc: locations) {

                    if (curLoc.getSuburb().equals(cur.getLocation())) {
                        curLoc.increaseCount();
                        curLoc.increaseSeverity(cur.getSeverity());
                        break;
                    }
                }
            }

            for (Location cur: locations) {



                int[] colors = {
                        Color.argb(0, 0, 255, 255),// transparent
                        Color.argb(255 / 3 * 2, 0, 255, 255),
                        Color.rgb(0, 191, 255),
                        Color.rgb(0, 0, 127),
                        Color.rgb(255, 0, 0)
                };

                float[] startPoints = {
                        0.0f, 0.10f, 0.20f, 0.60f, 1.0f
                };



// Create the tile provider.


// Add the tile overlay to the map.




                if (cur.AverageSeverity() <= 2) {
                    Circle circle = googleMap.addCircle(new CircleOptions()
                            .center(new LatLng(cur.getLatitude(), cur.getLongitude()))
                            .radius(500)
                            .strokeColor(Color.GREEN)
                            .fillColor(TRANSPARENT));
                    circle.setClickable(true);
                }

                else if (cur.AverageSeverity() >= 4) {
                    Circle circle = googleMap.addCircle(new CircleOptions()
                            .center(new LatLng(cur.getLatitude(), cur.getLongitude()))
                            .radius(500)
                            .strokeColor(Color.RED)
                            .fillColor(TRANSPARENT));
                circle.setClickable(true);

                }


                else {
                    Circle circle = googleMap.addCircle(new CircleOptions()
                            .center(new LatLng(cur.getLatitude(), cur.getLongitude()))
                            .radius(500)
                            .strokeColor(Color.YELLOW)
                            .fillColor(TRANSPARENT));
                    circle.setClickable(true);


                }
mMap.setOnCircleClickListener(new OnCircleClickListener() {
    @Override
    public void onCircleClick(Circle circle) {
        LatLng latLng = circle.getCenter();
        Intent intent = new Intent(getApplicationContext() , ViewCrimesByArea.class);
        startActivity(intent);


    }
});

               /* HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                        .data(Collections.singleton(new LatLng((double) cur.getLatitude(), (double) cur.getLongitude())))
                        .gradient(gradient)
                        .build();
                TileOverlay mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));*/

            }







        } catch (SQLException e) {
            e.printStackTrace();
        }
















        // Add a marker in Sydney and move the camera







        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //get latlong for corners for specified city

        LatLng one = new LatLng(-33.7999417, 25.2572868 );
        LatLng two = new LatLng(-33.9607324d, 25.6022644);


        builder.include(one);
        builder.include(two);

        LatLngBounds bounds = builder.build();

        //get width and height to current display screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 20% padding
        int padding = (int) (width * 0.2);

        //set latlong bounds
        mMap.setLatLngBoundsForCameraTarget(bounds);

        //move camera to fill the bound to screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

        //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
        mMap.setMinZoomPreference(mMap.getCameraPosition().zoom);
    }



}
