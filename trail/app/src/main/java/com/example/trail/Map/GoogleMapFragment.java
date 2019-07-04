package com.example.trail.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trail.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView test;
        public MapView mapView;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_map, parent, false));
            test = itemView.findViewById(R.id.tv_test);
            mapView = itemView.findViewById(R.id.map_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("click");
                }
            });
        }
    }
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> implements OnMapReadyCallback{
        // Set numbers of Card in RecyclerView.
        private static final int LENGTH = 2;
        @Nullable
        private GoogleMap map;

        private final String[] mPlaces;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.places);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.test.setText(mPlaces[position % mPlaces.length]);
            System.out.println("here");
            holder.mapView.getMapAsync(this);
            System.out.println("1");
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            System.out.println("2");
            map = googleMap;
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            map.setMyLocationEnabled(true);
            System.out.println("3");
        }
    }
}
