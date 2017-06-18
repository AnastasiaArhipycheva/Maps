package anastasia.maps;

import java.util.List;



/**
 * Created by Anastasia.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
