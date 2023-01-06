package com.manuni.deviceinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.manuni.deviceinfo.databinding.ActivityDeviceIdBinding;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceIdActivity extends AppCompatActivity {
    public static final int READ_PHONE_STATE_PERMISSION_CODE = 1;
    ActivityDeviceIdBinding binding;
    private String[] titles;
    private String[] descriptions;

    private TelephonyManager telephonyManager;
    private String imei, simCardSerial, simSubscriberId;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceIdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("Device Id Info");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        String deviceId = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        //IMEI number
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.READ_PHONE_STATE};

                requestPermissions(permission, READ_PHONE_STATE_PERMISSION_CODE);
            }else {
                imei = telephonyManager.getDeviceId();
                simCardSerial = telephonyManager.getSimSerialNumber();
                simSubscriberId = telephonyManager.getSubscriberId();

            }
        }else {
            imei = telephonyManager.getDeviceId();
            simCardSerial = telephonyManager.getSimSerialNumber();
            simSubscriberId = telephonyManager.getSubscriberId();
        }
        String ipAddress = "No Internet Connection";
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3GEnabled = false;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Network[] networks = connectivityManager.getAllNetworks();
            for (Network network: networks){
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                if (networkInfo != null){
                    if (networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                        ipAddress = getMobileIPAddress();
                    }
                }
            }
        }else {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null){
                ipAddress = is3GEnabled+"";
            }
        }

        //wifi mac address
        String wifiIpAddress = "No Wi-Fi Connection";
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Network[] networks = connectivityManager.getAllNetworks();
            for (Network network: networks){
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                if (networkInfo != null){
                    if (networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
                        wifiIpAddress = getWifiIPAddress();
                    }
                }
            }
        }else {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null){
                ipAddress = is3GEnabled+"";
            }
        }
        //Bluetooth ip address
        String bt = Settings.Secure.getString(this.getContentResolver(),"bluetooth_address");

        titles = new String[]{"Android Device Id","IMEI, MEID or ESN","Hardware Serial Number","SIM Card Serial No.","SIM Subscriber ID","IP Address","WI-FI Mac Address","Bluetooth Mac Address","Build Fingerprint"};
        descriptions = new String[]{deviceId,imei,Build.SERIAL,simCardSerial,simSubscriberId,ipAddress,wifiIpAddress,bt,Build.FINGERPRINT};

        MyAdapterForDeviceIdList adapter =new MyAdapterForDeviceIdList(DeviceIdActivity.this,titles,descriptions);
        binding.deviceIdList.setAdapter(adapter);


    }

    private String getWifiIPAddress() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        return Formatter.formatIpAddress(ip);
    }

    private static String getMobileIPAddress(){
        try {
          List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
          for (NetworkInterface networkInterface: interfaces){
              List<InetAddress>  inetAddresses = Collections.list(networkInterface.getInetAddresses());
              for (InetAddress inetAddress: inetAddresses){
                  if (!inetAddress.isLoopbackAddress()){
                      return inetAddress.getHostAddress();
                  }
              }
          }
        }catch (Exception e){

        }
        return "";
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private class MyAdapterForDeviceIdList extends ArrayAdapter<String>{
        Context context;
        String[] titles;
        String[] descriptions;

        public MyAdapterForDeviceIdList(Context context, String[] titles, String[] descriptions){
            super(context,R.layout.tow_rows, androidx.core.R.id.title,titles);
            this.context = context;
            this.titles = titles;
            this.descriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.tow_rows,parent,false);

            TextView titleTxt = view.findViewById(R.id.titleTxt);
            TextView descTxt = view.findViewById(R.id.descTxt);
            titleTxt.setText(titles[position]);
            descTxt.setText(descriptions[position]);

            return view;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case READ_PHONE_STATE_PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    recreate();//restart activity after granting permission

                    imei = telephonyManager.getDeviceId();
                    simCardSerial = telephonyManager.getSimSerialNumber();
                    simSubscriberId = telephonyManager.getSubscriberId();
                }else {
                    Toast.makeText(this, "Allow Phone State Permission To Read Data", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.search).setVisible(false);
        return true;
    }
}