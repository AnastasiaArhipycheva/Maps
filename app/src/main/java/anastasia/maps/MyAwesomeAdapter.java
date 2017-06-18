package anastasia.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;



public class MyAwesomeAdapter extends BaseAdapter {
 //   ArrayList<Marker> markers;
    Markers markers;
    LayoutInflater inflater;

    public MyAwesomeAdapter(Context context, Markers markers) {
        this.markers = markers;
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    @Override
    public int getCount() {
        return markers.size();
    }

    @Override
    public Marker getItem(int i) {
        return markers.getItem(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.marker_list_item, viewGroup, false);
        }
        ((TextView)convertView.findViewById(R.id.marker)).setText(markers.getItem(position).getTitle());
        ((ImageButton) convertView.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markers.getItem(position).remove();
                markers.removeMarker(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public void add(Marker marker)
    {
        markers.addMarker(marker);
        notifyDataSetChanged();
    }

    public boolean contains(String name)
    {
        for (Marker marker: markers.getMarkers()){
            if (marker.getTitle().equals(name)){
                return  true;
            }
        }
        return false;
    }
}
