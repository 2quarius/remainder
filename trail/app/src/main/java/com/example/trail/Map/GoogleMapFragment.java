package com.example.trail.Map;


import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.trail.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.location.places.ui.PlacePicker.IntentBuilder;
import static com.google.android.gms.location.places.ui.PlacePicker.getPlace;

public class GoogleMapFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, LocationListener {
    private MapView mMapView;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    //用户权限
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    //用户当前坐标
    private Location mLastLocation;
    // 声明一个 LocationRequest 变量以及一个保存位置更新状态的变量。
    private LocationRequest mLocationRequest;
    private boolean mLocationUpdateState;
    // REQUEST_CHECK_SETTINGS 是用于传递给 onActivityResult 方法的 request code。
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private static final int PLACE_PICKER_REQUEST = 3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_google_map, container, false);

        // Gets the MapView from the XML layout and creates it
        mMapView = (MapView) v.findViewById(R.id.gmap);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPlacePicker();
            }
        });
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        createLocationRequest();
        return v;
    }

    /**
     * 如果REQUEST_CHECK_SETTINGS 请求返回的是一个 RESULT_OK，则发起位置更新请求。
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                mLocationUpdateState = true;
                startLocationUpdates();
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = getPlace(getContext(),data);
                String addressText = place.getName().toString();
                addressText += "\n" + place.getAddress().toString();
                placeMarkerOnMap(place.getLatLng());
            }
        }
    }

    /**
     * 覆盖 onPause() 方法，停止位置变化请求。
     */
    @Override
    public void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    /**
     * 重新开始位置更新请求。
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mLocationUpdateState) {
            startLocationUpdates();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setUpMap();
        //如果用户的位置设置是打开状态的话，启动位置更新。
        if (mLocationUpdateState) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (null != mLastLocation) {
            placeMarkerOnMap(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        }
    }
    /**
     * app 需要使用 ACCESS_FINE_LOCATION 权限以获得用户定位信息.
     * 判断 app 是否获得了 ACCESS_FINE_LOCATION 权限。如果没有，向用户请求授权。
     */
    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                                               android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        //setMyLocationEnabled 一句打开了 my-location 图层，用于在用户坐标出绘制一个浅蓝色的圆点。同时加一个按钮到地图上，当你点击它，地图中心会移动到用户的坐标。
        mMap.setMyLocationEnabled(true);
        //getLocationAvailability 一句判断设备上的位置信息是否有效。
        LocationAvailability locationAvailability =
                LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            //getLastLocation 一句允许你获得当前有效的最新坐标。
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            //如果能够获得最新坐标，将镜头对准用户当前坐标。
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                        .getLongitude());
                //add pin at user's location
                placeMarkerOnMap(currentLocation);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }
    }
    protected void placeMarkerOnMap(LatLng location) {
        // 创建了一个 MarkerOptions 对象并将大头钉要放在的位置设置为用户当前坐标。
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        // 设置个人位置标记样式
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                (getResources(), R.mipmap.ic_user_location)));
        // 显示具体位置
        String titleStr = getAddress(location);
        markerOptions.title(titleStr);
        // 将大头钉添加到地图。
        mMap.addMarker(markerOptions);
    }
    private String getAddress( LatLng latLng ) {
        // 创建一个 Geocoder 对象，用于将一个经纬度坐标转换成地址或进行相反的转换。
        Geocoder geocoder = new Geocoder(getContext() );
        String addressText = "";
        List<Address> addresses = null;
        Address address = null;
        try {
            // 使用 geocoder 将方法参数接收到的经纬度转换成地址信息。
            addresses = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 );
            // 如果响应的 addresses 中包含有地址信息，将这些信息拼接为一个字符串返回。
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    addressText += (i == 0)?address.getAddressLine(i):("\n" + address.getAddressLine(i));
                }
            }
        } catch (IOException e ) {
        }
        return addressText;
    }
    protected void startLocationUpdates() {
        // startLocationUpdates() 中，如果 ACCESS_FINE_LOCATION 权限未获取，则请求授权并返回。
        if (ActivityCompat.checkSelfPermission(getContext(),
                                               android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                                              new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                              LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        // 如果已经获得授权，请求位置变化信息。
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                                                                 this);
    }

    /**
     * 创建一个 LocationRequest 对象，
     * 将它添加到一个 LocationSettingsRequest.Builder 对象，
     * 并基于用户位置设置的当前状态查询位置变化信息并处理。
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        // setInterval() 指定了 app 多长时间接受一次变化通知。
        mLocationRequest.setInterval(10000);
        // setFastestInterval() 指定 app 能够处理的变化通知的最快速度。
        // 设置fastestInterval 能够限制位置变化通知发送给你的 app 的频率。
        // 在开始请求位置变化通知之前，需要检查用户位置设置的状态。
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                                                                   builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    // SUCCESS 状态说明一切正常，你可以初始化一个 location request。
                    case LocationSettingsStatusCodes.SUCCESS:
                        mLocationUpdateState = true;
                        startLocationUpdates();
                        break;
                    // RESOLUTION_REQUIRED 状态表明位置设置有一个问题有待修复。
                    // 有可能是因为用户的位置设置被关闭了。你可以向用户显示一个对话框
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    // SETTINGS_CHANGE_UNAVAILABLE 状态表明位置设置有一些无法修复的问题。
                    // 有可能是用户在上面的对话框里选择了 NEVER。
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }
    private void loadPlacePicker() {
        IntentBuilder builder = new IntentBuilder();

        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch(GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
