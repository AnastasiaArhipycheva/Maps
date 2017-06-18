package anastasia.maps;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    String[] type = {"Стандартная", "Гибридная", "Спутниковая", "Карта местности"};
    ArrayList<String> Loc = new ArrayList<>();
    ArrayAdapter<String> adapterLoc;
    Spinner spinnerL;
    MyAwesomeAdapter markerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDestination = (EditText) findViewById(R.id.etDestination);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Loc.add("Fq");
        adapterLoc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Loc);
        //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerT = (Spinner) findViewById(R.id.spinnerType);
        spinnerL = (Spinner) findViewById(R.id.spinnerLocations);
        spinnerT.setAdapter(adapter);
        //spinnerL.setAdapter(adapterLoc);
        // заголовок
        spinnerT.setPrompt("Тип карты");
        spinnerL.setPrompt("Мои маркеры");
        // выделяем элемент
        spinnerT.setSelection(0);
        spinnerL.setSelection(0);
        // устанавливаем обработчик нажатия

        spinnerT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                switch (position) {
                    case 0: mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        Toast.makeText(getBaseContext(), "Стандартная", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        Toast.makeText(getBaseContext(), "Гибридная", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        Toast.makeText(getBaseContext(), "Спутниковая", Toast.LENGTH_SHORT).show();
                        break;
                    case 3: mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        Toast.makeText(getBaseContext(), "Карта местности", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }

        });
        spinnerL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerL.setLongClickable(true);
        spinnerL.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Loc.remove(i);

                //   Markers.Get().setMarker(markerAdapter);
                return false;
            }
        });
    }

    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    AlertDialog dialog;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng NNGU = new LatLng(56.299166, 43.982493);

        Markers.Get().addMarker(mMap.addMarker(new MarkerOptions().position(NNGU).title("Marker in NNGU")));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NNGU, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("ННГУ 2 корпус")
                .position(NNGU)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        markerAdapter = new MyAwesomeAdapter(this, Markers.Get());

        spinnerL.setAdapter(markerAdapter);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Введите имя маркера");
                View view = getLayoutInflater().inflate(R.layout.marker_dialog, null, false);
                final EditText name = (EditText) view.findViewById(R.id.markerName);
                final TextView error = (TextView) view.findViewById(R.id.error);
                final Button cancel = (Button) view.findViewById(R.id.btnCancel);
                final Button accept = (Button) view.findViewById(R.id.btnAccept);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String in = name.getText().toString();
                        if (in.trim().equals("")){
                            error.setVisibility(View.VISIBLE);
                            error.setText(R.string.error_empty_name);
                        }else if (markerAdapter.contains(in))
                        {
                            error.setVisibility(View.VISIBLE);
                            error.setText(R.string.error_name_already_exist);
                        }else {
                            markerAdapter.add(mMap.addMarker(new MarkerOptions().position(latLng).title(in)));
                            dialog.dismiss();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                builder.setView(view);
                dialog = builder.create();
                dialog.show();
                //map.addMarker(new MarkerOptions().position(latLng).title(""));

            }
        });

        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}
