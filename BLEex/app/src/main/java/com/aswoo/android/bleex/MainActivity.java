package com.aswoo.android.bleex;

import android.Manifest;
import android.app.*;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aswoo.android.bleex.Model.ApiInterface;
import com.aswoo.android.bleex.Model.Repo;
import com.aswoo.android.bleex.Utils.SampleGattAttributes;
import com.gc.materialdesign.views.ButtonRectangle;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aswoo.android.bleex.BluetoothLeService.g;
import static com.aswoo.android.bleex.BluetoothLeService.h;

/**
 * Created by seungwoo on 2017-10-25.
 */

/*
*  ps 추후에 이 글을 볼 지 모르는 안드로이드 개발자 분에게 남깁니다. 블루투스 하나도 모르는 맨바닥에서 시작해서 따라한다고 북치고 장구치고 온갖 생쇼를 다하고서
*
*  겨우 완성시켰다. 구조를 좀더 예쁘게 정리할 수 도 있었지만 귀찮아서 하기가 싫었습니다. ㅂㅂ
*
* */

public class MainActivity extends Activity {

    private final static String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private String writeTemp = "";

    private TextView mConnectionState;
    private TextView mDataField;
    private String mDeviceName;
    private String mDeviceAddress;

    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    // Code to manage Service lifecycle.
    byte[] k = new byte[20];


    private String resultString;
    private MyAsyncTask asyncTask;

    @BindView(R.id.my_weather_icon)
    WeatherIconView weatherIconView;
    @BindView(R.id.my_windy_icon)
    WeatherIconView windyconView;
    @BindView(R.id.settingDegree)
    ArcProgress settingDegreeProgress;
    @BindView(R.id.currentTemperature)
    TextView currentTemperatureView;
    @BindView(R.id.myGPSPosition)
    TextView myGPSTextView;
    @BindView(R.id.currentWeatherTemp)
    TextView currentWeatherTempView;


    private double lat; // 위도
    private double lon; // 경도

    private static final int BLUETOOTH_AND_LOCATION = 1;

    private static final String API_KEY = "d725a55d45e89cbf8b6b38f0b646ebf5";

    android.location.LocationManager locationManager;

    // 현재 GPS 사용 유무
    boolean isGPSEnabled = false;
    //네트워크 사용 유무
    boolean isNetworkEnabled = false;
    //GPS 상태값
    boolean isGetLocation = false;
    Location location;

    // 최소 gps 정보 업데이트 거리 1000미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1000;

    //최소 업데이트 시간 1분
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;


    public void setRXTX(int groupPosition,
                        int childPosition, long id) {

        if (mGattCharacteristics != null) {
            final BluetoothGattCharacteristic characteristic =
                    mGattCharacteristics.get(groupPosition).get(childPosition);
            final int charaProp = characteristic.getProperties();
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
                if (mNotifyCharacteristic != null) {
                    mBluetoothLeService.setCharacteristicNotification(
                            mNotifyCharacteristic, false);
                    mNotifyCharacteristic = null;
                }
                mBluetoothLeService.readCharacteristic(characteristic);
            }
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                mNotifyCharacteristic = characteristic;
                mBluetoothLeService.setCharacteristicNotification(
                        characteristic, true);
            }
        }
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };
    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                // updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                // updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                //clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
                setRXTX(0, 0, 0);
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                currentTemperatureView.setText("현재온도 : " + Application.getCurrentTemp() + "℃");
                //settingTemp(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };
    // If a given GATT characteristic is selected, check for supported features.  This sample
    // demonstrates 'Read' and 'Notify' features.  Seefbtn_40
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for the complete
    // list of supported characteristic features.

    protected void onCreate(Bundle paramBundle) {

        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

        mDataField = (TextView) findViewById(R.id.data_value);
        //getActionBar().setTitle(mDeviceName);
        //getActionBar().setDisplayHomeAsUpEnabled(true);



        //this.mCircleView.setBarColor(new int[] { getResources().getColor(2131427371), getResources().getColor(2131427332) });
        //this.mCircleView.setTextColor(-65536);
        //this.mCircleView.setTextColor(-16776961);
        //this.mCircleView.setAutoTextColor(true);

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        //기본 셋팅
        asyncTask = new MyAsyncTask();
        resultString = "40";
        Application.setDegreeStatus(resultString);
        asyncTask.execute(resultString);


    }

    private void sendWrite() {
        //BluetoothGattCharacteristic writableChar = null;
        try {
            this.k = qf.a(("" + writeTemp).getBytes());
            BluetoothGattCharacteristic writeTemp = BluetoothLeService.mBluetoothGatt.getService(UUID.fromString(g)).getCharacteristic(UUID.fromString(h));
            writeTemp.setValue(this.k);
            BluetoothLeService.mBluetoothGatt.writeCharacteristic(writeTemp);
            return;
        } catch (Exception paramString) {
            Log.i("txxx exception", paramString.toString());
        }
    }

    public static class qf {
        public static byte[] a(byte[] paramArrayOfByte) {
            if (paramArrayOfByte.length % 2 != 0) {
                throw new IllegalArgumentException("length error");
            }
            byte[] arrayOfByte = new byte[paramArrayOfByte.length / 2];
            int i = 0;
            while (i < paramArrayOfByte.length) {
                String str = new String(paramArrayOfByte, i, 2);
                arrayOfByte[(i / 2)] = ((byte) Integer.parseInt(str, 16));
                i += 2;
            }
            return arrayOfByte;
        }
    }

    private void settingTemp(String resultString) {
        //resultstring = 40,45,50,55;
        if (Application.getWriteCharicteristic() != null) {
            Log.i("settingTemp Write", Application.getWriteCharicteristic());
            String paramString = changeSettingTemp(Application.getWriteCharicteristic(), resultString);
            Log.i("settingTemp paramString", paramString);
            writeTemp = paramString + "FF";
        }
    }

    private String changeSettingTemp(String temp, String resultString) {
        if (temp != null) {
            StringBuilder bf = new StringBuilder(temp);
            temp = String.valueOf(bf.replace(2, 4, resultString));
            return temp;
        }
        return "Error";
    }

    private void displayData(String data) {
        if (data != null) {
            mDataField.setText(data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
            //setRXTX(0,0,0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }


    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);
            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();
            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
    }

    //AsyncTask
    public class MyAsyncTask extends AsyncTask<String, String, String> {

        private String resp;

        public String result;

        @Override
        protected void onPreExecute() {
            Log.d("Done", "Done");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                resp = params[0];
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onProgressUpdate(String... text) {
            //finalResult.setText(text[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //
            settingDegreeProgress.setProgress(Integer.parseInt(Application.getDegreeStatus()));
            //mCircleView.setText((Application.getDegreeStatus()));
            settingTemp(resultString); // "ff"를 추가시킬 전송 string 완성
            Log.i(TAG, writeTemp.toString());
            sendWrite();//이제 전송
            //super.onPostExecute(result);
        }
    }

    @OnClick({R.id.temperature40Btn,R.id.temperature45Btn, R.id.temperature50Btn, R.id.temperature55Btn})
    public void onSelectProductState(Button view) {
        switch (view.getId()) {
            case R.id.temperature40Btn:
                asyncTask = new MyAsyncTask();
                resultString = "40";
                Application.setDegreeStatus(resultString);
                asyncTask.execute(resultString);
                break;
            case R.id.temperature45Btn:
                asyncTask = new MyAsyncTask();
                resultString = "45";
                Application.setDegreeStatus(resultString);
                asyncTask.execute(resultString);
                break;
            case R.id.temperature50Btn:
                asyncTask = new MyAsyncTask();
                resultString = "50";
                Application.setDegreeStatus(resultString);
                asyncTask.execute(resultString);
                break;
            case R.id.temperature55Btn:
                asyncTask = new MyAsyncTask();
                resultString = "55";
                Application.setDegreeStatus(resultString);
                asyncTask.execute(resultString);
                break;
        }
    }

    @Override

    protected void onDestroy() {
        super.onDestroy();
        asyncTask.cancel(true);
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @AfterPermissionGranted(BLUETOOTH_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Log.d("EasyPermission: ", "Aleady have Permission");
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.bluetooth_location),
                    BLUETOOTH_AND_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

            //GPS 정보 가져오기
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            //현재 네트워크 상태 값 알아오기

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                //GPS 와 네트워크 사용이 가능하지 않을때 소스 구현
            } else {
                this.isGetLocation = true;
                //네트워크 정보로 부터 위치값 가져오기
                if (!isNetworkEnabled && !isNetworkEnabled) {
                    //GPS와 네트워크 사용이 가능하지 않을때 소스 구현
                } else {
                    this.isGetLocation = true;
                    //네트워크 정보로 부터 위치값 가져오기
                    if (isNetworkEnabled) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Log.d("TAG", "NOPERMISSION");
                        }
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);

                        if (locationManager != null) {
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                Log.d("TAG", "NOPERMISSION");
                            }
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                                Log.i("GPS ", "Latitude : " + String.valueOf(lat) + " Longitude : " + String.valueOf(lon));
                            }
                        }
                    }
                    if (isGPSEnabled) {
                        if (location == null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                //위도 경도 저장
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                                Log.i("GPS ", "Latitude : " + String.valueOf(lat) + " Longitude : " + String.valueOf(lon));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(getApplicationContext(), "onLocationCahnged", Toast.LENGTH_SHORT)
                    .show();
            Double latitude = 0.0;
            Double longitude = 0.0;
            if(location != null){
                latitude = location.getLatitude();
                longitude =location.getLongitude();
                String currentAddress = getAddress(getApplicationContext(),latitude,longitude);
                if(currentAddress.equals("현재 위치를 확인 할 수 없습니다."))
                {
                    myGPSTextView.setText(currentAddress);
                }
                else{
                    myGPSTextView.setText(currentAddress);
                }
            }
            Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();

            ApiInterface service = client.create(ApiInterface.class);
            Call<Repo> call = service.repo(API_KEY, Double.valueOf(latitude), Double.valueOf(longitude));
            call.enqueue(new Callback<Repo>() {
                @Override
                public void onResponse(Call<Repo> call, Response<Repo> response) {
                    Repo repo = response.body();
                    Log.d("Success","On");
                    if (repo != null){
                        double temp = repo.getMain().getTemp() - (double) 273;
                        String strNumber = String.format("%.1f", temp);
                        currentWeatherTempView.setText(String.valueOf(strNumber));
                        currentTemperatureView.setTextColor(Color.parseColor("#ffffff"));
                        String ic = repo.getWeather().get(0).getIcon();
                        Log.i("IC",ic);
                        String icon = setWeatherIconView(ic);

                        //weatherIconView.setIconResource(icon);
                        //weatherIconView.setIconResource(getString(R.string.wi_day_sunny_overcast));
                    }
                }

                @Override
                public void onFailure(Call<Repo> call, Throwable t) {
                    Log.d("Fail","On");
                }
            });
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @OnClick(R.id.useGPSfuctionBtn)
    void onGPSClick() {
        methodRequiresTwoPermission();
        chkGpsService();

        //OnLocationChange 마다 변경
        if(Application.getCurrentLocation() != null){
            location = Application.getCurrentLocation();
            Log.i("App","Static");
        }else {
            location = getLocation();
            Log.i("getLoaction","onChanged");
        }

    }

    public String setWeatherIconView(String id){
        switch (id){
            case "01d" :
            case "01n":
                //clear sky
                weatherIconView.setIconResource(getString(R.string.wi_day_sunny));
                return "f00d";
            case "02d" :
            case "02n":
                //few clouds
                //return "f002";
                weatherIconView.setIconResource(getString(R.string.wi_day_cloudy));
                return "f002";
            case "03d" :
            case "03n":
                //scattered clouds
                weatherIconView.setIconResource(getString(R.string.wi_cloud));
                return "f041";
            case "04d" :
            case "04n":
                //broken clouds
                weatherIconView.setIconResource(getString(R.string.wi_cloudy));
                return "f013";
            case "09d" :
            case "09n":
                //소나기
                weatherIconView.setIconResource(getString(R.string.wi_showers));
                return "f01a";
            case "10d" :
            case "10n":
                weatherIconView.setIconResource(getString(R.string.wi_rain));
                return "f019";
            case "11d" :
            case "11n":
                //thunderstrom
                weatherIconView.setIconResource(getString(R.string.wi_thunderstorm));
                return "f01e";
            case "13d" :
            case "13n":
                //snow
                weatherIconView.setIconResource(getString(R.string.wi_snow));
                return "f01b";
            case "50d" :
            case "50n":
                //fog
                weatherIconView.setIconResource(getString(R.string.wi_fog));
                return "f014";
            default:
                // N/A
                weatherIconView.setIconResource(getString(R.string.wi_na));
                return "f07b";
        }
    }
    //GPS 설정 체크
    private boolean chkGpsService() {

        String gps = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        Log.d(gps, "aaaa");

        if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {

            // GPS OFF 일때 Dialog 표시
            AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
            gsDialog.setTitle("위치 서비스 설정");
            gsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?");
            gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // GPS설정 화면으로 이동
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    }).create().show();
            return false;

        } else {
            return true;
        }
    }

    public static String getAddress(Context mContext,double lat, double lng) {
        String nowAddress ="현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress  = currentLocationAddress;

                }
            }

        } catch (IOException e) {

            Log.e("주소 출력 에러! ","Error");
            e.printStackTrace();
        }
        return nowAddress;
    }

}

