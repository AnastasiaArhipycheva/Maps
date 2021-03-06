package anastasia.maps.Models;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by Администратор on 26.12.2016.
 */

public class MarkersImpl  implements Markers {
    private static MarkersImpl _Instance;

    ArrayList<Marker> markers = new ArrayList<Marker>();
 //   MyAwesomeAdapter markerAdapter;


    public static MarkersImpl Get() {
        if (_Instance == null)
            _Instance = new MarkersImpl();
        return _Instance;
    }
/*
    public MyAwesomeAdapter getMarker() {
        return markerAdapter;
    }
    public void setMarker(MyAwesomeAdapter mar)  {
        markerAdapter=mar;
    }

*/
    public ArrayList<Marker> getMarkers() {
        return markers;
    }
    public void setMarkers(ArrayList<Marker> mar)  {
        markers=mar;
    }

    public Marker getItem(int i) {
        return markers.get(i);
    }
    public void addMarker(Marker marker) {
        markers.add(marker);
    }
    public void removeMarker(int i) {
        markers.remove(i);
    }

    public void removeMarker(Marker marker) {
        markers.remove(marker);
    }
    public int size() {
        return markers.size();
    }
}
