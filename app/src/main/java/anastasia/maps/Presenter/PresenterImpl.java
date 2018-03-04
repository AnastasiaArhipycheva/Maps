package anastasia.maps.Presenter;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import anastasia.maps.MapsActivity;
import anastasia.maps.Models.Markers;
import anastasia.maps.Models.MarkersImpl;
import anastasia.maps.Models.MyAwesomeAdapter;

/**
 * Created by Администратор on 28.02.2018.
 */

public class PresenterImpl implements Presenter {
    private Markers _marker;
//    private MarkersImpl _markersImpl;
    private MyAwesomeAdapter _myAdapter ;

    public PresenterImpl(GoogleMap map){
        LatLng NNGU = new LatLng(56.299166, 43.982493);
        MarkersImpl.Get().addMarker(map.addMarker(new MarkerOptions().position(NNGU).title("Marker in NNGU")));
    }

    public MyAwesomeAdapter getAdapter(MapsActivity map) {
//        _marker = _marker.Get();
//      _marker.addMarker(mMap.addMarker(new MarkerOptions().position(NNGU).title("Marker in NNGU")));
        _myAdapter = new MyAwesomeAdapter(map, _markersImpl);
        return _myAdapter;
    }
//    public Markers getMarker() {
//        return _marker.Get();
//    }

    public void setStartLocation(){}
    public void setEndLocation(){}
    public void addMarker(Marker marker){
        _myAdapter.add(marker);
//        _marker.addMarker(marker);
    }
    public void deleteMarker(){}

    @Override
    public Markers getMarker() {
        return null;
    }

    public Marker getItemMarker(int i){
        return _marker.getItem(i);
    }
    public boolean isMarkerNameExist(String name){
       return _myAdapter.contains(name);
    }
    public void sendRequest(){}
    public void setAdapter(){}
}
