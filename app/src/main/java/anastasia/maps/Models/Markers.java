package anastasia.maps.Models;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by Администратор on 02.03.2018.
 */

public interface Markers {
//    public static Markers Get();
    public ArrayList<Marker> getMarkers();
    public void setMarkers(ArrayList<Marker> mar);

    public Marker getItem(int i);
    public void addMarker(Marker marker);
    public void removeMarker(int i);

    public void removeMarker(Marker marker);
    public int size();

}
