package anastasia.maps;

import java.util.List;

import anastasia.maps.Models.Route;


/**
 * Created by Anastasia.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
