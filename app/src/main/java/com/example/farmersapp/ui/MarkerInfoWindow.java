package com.example.farmersapp.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farmersapp.R;
import com.example.farmersapp.model.Instrument;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MarkerInfoWindow implements GoogleMap.InfoWindowAdapter {
    private View view;
    private LayoutInflater layoutInflater;
    private Context context;

    public MarkerInfoWindow(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        view = layoutInflater.inflate(R.layout.marker_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Instrument instrument = (Instrument) marker.getTag();

        String url = "https://www.anime-planet.com/images/characters/vegeta-2186.jpg";
        TextView markerWindow_type, markerWindow_price;
        ImageView markerWindow_image;
        markerWindow_type = view.findViewById(R.id.markerWindow_type);
        markerWindow_price = view.findViewById(R.id.markerWindow_price);
        markerWindow_image = view.findViewById(R.id.markerWindow_image);

        markerWindow_type.setText(marker.getTitle());
        markerWindow_price.setText(marker.getSnippet());
        Log.d("TAG",marker.getTitle());
        assert instrument != null;
        Picasso.get().load(instrument.getInstrumentImageUrl()).fit().into(markerWindow_image, new MarkerCallback(marker));

        return view;
    }

    static class MarkerCallback implements Callback {
        Marker marker = null;

        MarkerCallback(Marker marker) {
            this.marker = marker;
        }

        @Override
        public void onSuccess() {
            Log.d("Test", "onSuccess");

            if (marker == null)
            {
                Log.d("Test", "marker is null");
                return;
            }

            if (!marker.isInfoWindowShown())
            {
                Log.d("Test", "info window not shown");
                return;
            }

            // If Info Window is showing, then refresh it's contents:

            marker.hideInfoWindow(); // Calling only showInfoWindow() throws an error
            marker.showInfoWindow();

        }

        @Override
        public void onError(Exception e) {
            Log.d("Test", "Error loading thumbnail!");
        }
    }
}
