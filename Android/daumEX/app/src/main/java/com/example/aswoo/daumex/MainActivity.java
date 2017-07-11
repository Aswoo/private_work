package com.example.aswoo.daumex;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements MapView.MapViewEventListener,MapView.POIItemEventListener {

    static final String API_KEY = "5b8be39f0522d7675bfaaeb9e6dcfc4c";
    private MapView mapView;
    public double n_latitude;
    public double n_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // MapView 객체생성 및 API Key 설정
        mapView = new MapView(MainActivity.this);
        mapView.setDaumMapApiKey(API_KEY);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        RelativeLayout mapViewContainer = (RelativeLayout) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        Log.i("메세지", "처음실행");
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.452288655557076, 127.13049478628298), 2, true);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i("LOG_TAG", String.format("MapView onMapViewCenterPointMoved (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
        n_latitude = mapPointGeo.latitude;
        n_longitude = mapPointGeo.longitude;
        searchFunction();
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    private void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //위치 정보를 받ㄷ을 리스너 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10;
        float minDistance = 0;

        try {
            //GPS를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 네트워크를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
            /*Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();

                Toast.makeText(getApplicationContext(), "Last Known Location : " + "Latitude : " + latitude + "\nLongitude:" + longitude, Toast.LENGTH_LONG).show();
            }*/
            Log.i("TAG", "여기까지 오긴 하냐?");
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "위치 확인이 시작되었습니다. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
    }

    public class GPSListener implements LocationListener {

        public Double latitude;
        public Double longitude;


        //위치정보가 확인될때 자동 호출되는 메소드
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            n_latitude = latitude;
            n_longitude = longitude;
            String msg = "Latitude : " + n_latitude + "\nLongitude:" + n_longitude;
            Log.i("GPSListener", msg);
            // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    private void searchFunction() {
        String apikey = "ad7e39218310d6d441b623bd18c6ea4f";
        String code = null;
        String query = null;

        try {
            query = URLEncoder.encode("카카오프렌즈", "UTF-8");
            String requestUrl = "https://apis.daum.net/local/v1/search/category.json?"
                    + "apikey=" + apikey
                    + "&code=" + "CE7"
                    + "&location="
                    + String.valueOf(n_latitude) + ","
                    + String.valueOf(n_longitude)
                    + "&radius=1000";


            Log.i("requestURL", requestUrl);

            String result = new HttpHandler().execute(requestUrl).get();

            JSONObject root = new JSONObject(result);
            JSONArray itemArray = root.getJSONObject("channel").getJSONArray("item");
            for(int i = 0; i<itemArray.length();i++) {
                JSONObject itemObject = itemArray.getJSONObject(i);
                String title = itemObject.getString("title");
                Double latitude = itemObject.getDouble("latitude");
                Double longitude = itemObject.getDouble("longitude");

                MapPOIItem customMarker = new MapPOIItem();
                customMarker.setItemName(title);
                customMarker.setTag(1);
                customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude));
                customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
                customMarker.setCustomImageResourceId(R.drawable.custom_marker_red); // 마커 이미지.
                customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
                mapView.addPOIItem(customMarker);
            }
            /*JSONObject root = new JSONObject(result);
            JSONArray itemArray = root.getJSONObject("channel")
                    .getJSONArray("item");
            for(int i= 0;i < itemArray.length();i++) {
                JSONObject itemObject = itemArray.getJSONObject(i);
                String phone = itemObject.getString("phone");
                Log.i("phone" + String.valueOf(i) ,phone);
            }

            JSONObject infoJsonObject = root.getJSONObject("channel")
                    .getJSONObject("info");*/


        } catch (UnsupportedEncodingException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }
}