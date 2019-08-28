package com.example.trail.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.trail.MainActivity;
import com.example.trail.NewTask.SimpleTask.MyLocation;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;

import java.util.List;

import solid.ren.skinlibrary.base.SkinBaseFragment;

public class BaiduMapFragment extends SkinBaseFragment {
    private MarkerOptions markerOptions;
    private Marker marker;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocalBroadcastManager broadcastManager;
    private boolean isFirstLoc = true; // 是否首次定位
    private List<Task> mTasks = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_baidu_map, container, false);
        mMapView = v.findViewById(R.id.bmap);
        mBaiduMap = mMapView.getMap();
        initLocationOption();
        registerReceiver();
        return v;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        mTasks = ((MainActivity)getActivity()).getTasks();
        initTaskLocation();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        broadcastManager.unregisterReceiver(mRefreshReceiver);
    }
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refresh map");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final int position = intent.getIntExtra("refresh position", -1);
            final Task t = (Task) intent.getSerializableExtra("refresh task");
            if (t!=null) {
                // 在主线程中刷新UI，用Handler来实现
                new Handler().post(new Runnable() {
                    public void run() {
                        //在这里来写你需要刷新的地方
                        mTasks.set(position,t);
                    }
                });
            }
        }
    };

    private void initTaskLocation() {
        if (mTasks==null){return;}
        for (Task t:mTasks)
        {
            MyLocation location = t.getLocation();
            if (location!=null){
                LatLng position = new LatLng(location.getLatitude(),location.getLongitude());
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.location);
                MarkerOptions markerOptions = new MarkerOptions().position(position).icon(bitmap);
                Marker marker = (Marker) mBaiduMap.addOverlay(markerOptions);
                Bundle bundle = new Bundle();
                bundle.putSerializable("task",t);
                marker.setExtraInfo(bundle);
                mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //在显示新信息窗之前，先关闭已经在显示的信息窗
                        mBaiduMap.hideInfoWindow();
                        //显示信息窗
                        View infoWindowView = getLayoutInflater().inflate(R.layout.map_info_window, null);
                        TextView title = (TextView) infoWindowView.findViewById(R.id.info_window_title);
                        TextView description = (TextView) infoWindowView.findViewById(R.id.info_window_description);
                        //如果显示窗是要用自定义的view，则最外层可直接用UI控件或者layout，如果外层用layout来布局，那么布局中一定要有UI控件，否则BitmapDescriptorFactory.fromView(view);会报空指针
                        Task task = (Task) marker.getExtraInfo().get("task");
                        title.setText(task.getTitle());
                        description.setText(task.getDescription());
                        BitmapDescriptor infoWindowBitmap = BitmapDescriptorFactory.fromView(infoWindowView);
                        //信息窗点击处理事件
                        Log.d("tag", "infoWindow_view="+infoWindowView);
                        Log.d("tag", "infoWindow_bitmap="+infoWindowBitmap);
                        InfoWindow.OnInfoWindowClickListener onInfoWindowClickListener = new InfoWindow.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick() {
                                mBaiduMap.hideInfoWindow();
                            }
                        };
                        //定义信息窗
                        InfoWindow infoWindow = new InfoWindow(infoWindowBitmap,//信息窗布局
                                                               marker.getPosition(), //信息窗的点
                                                               -47, //信息窗与点的位置关系
                                                               //infoWindow监听器
                                                               onInfoWindowClickListener
                        );
                        //显示信息窗
                        mBaiduMap.showInfoWindow(infoWindow);
                        return true;
                    }
                });
            }
        }
    }

    /**
     * 初始化定位参数配置
     */
    private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        LocationClient locationClient = new LocationClient(getContext());
        //声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        BaiduMapFragment.MyLocationListener myLocationListener = new BaiduMapFragment.MyLocationListener();
        //注册监听函数
        locationClient.registerLocationListener(myLocationListener);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02");
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
        //开始定位
        locationClient.start();
    }
    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();

            LatLng myLocation = new LatLng(latitude, longitude);
            //准备 marker 的图片
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_user_location);
            //准备 marker option 添加 marker 使用
            markerOptions = new MarkerOptions().icon(bitmap).position(myLocation);
            //获取添加的 marker 这样便于后续的操作
            marker = (Marker) mBaiduMap.addOverlay(markerOptions);
            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    return false;
                }
            });

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(radius)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(latitude)
                    .longitude(longitude).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            // 当不需要定位图层时关闭定位图层
            //mBaiduMap.setMyLocationEnabled(false);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                                       location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
                    Toast.makeText(getContext(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
                    Toast.makeText(getContext(), location.getAddrStr(), Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
                    Toast.makeText(getContext(), location.getAddrStr(), Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(getContext(), "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(getContext(), "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(getContext(), "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
