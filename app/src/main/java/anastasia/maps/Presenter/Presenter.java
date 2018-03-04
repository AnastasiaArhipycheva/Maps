package anastasia.maps.Presenter;

import com.google.android.gms.maps.model.Marker;

import anastasia.maps.MapsActivity;
import anastasia.maps.Models.Markers;
import anastasia.maps.Models.MyAwesomeAdapter;

/**
 * Created by Администратор on 01.03.2018.
 */

public interface Presenter {
    void setStartLocation();
    void setEndLocation();
    void addMarker(Marker marker);
    void deleteMarker();
    public Markers getMarker();
    boolean isMarkerNameExist(String name);
    void sendRequest();
    void setAdapter();
    Marker getItemMarker(int i);
    MyAwesomeAdapter getAdapter(MapsActivity mapsActivity);
}
