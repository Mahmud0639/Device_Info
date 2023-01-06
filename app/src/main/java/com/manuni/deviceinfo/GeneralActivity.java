package com.manuni.deviceinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import static android.os.SystemClock.uptimeMillis;

import static java.lang.System.getProperty;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.manuni.deviceinfo.databinding.ActivityGeneralBinding;

import java.util.concurrent.TimeUnit;

public class GeneralActivity extends AppCompatActivity {
    ActivityGeneralBinding binding;

    private String titles[];
    private String descriptions[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGeneralBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setTitle("General Info");

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }
        //calculating device uptime
        long timeMillis = uptimeMillis();

        String upTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(timeMillis),
                TimeUnit.MILLISECONDS.toMinutes(timeMillis)-
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeMillis)),
                TimeUnit.MILLISECONDS.toSeconds(timeMillis)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeMillis))
                );

        titles = new String[]{"Model","Manufacturer","Device","Board","Hardware","Brand","Android Version","OS Name","Bootloader","Build Number","Radio Version","Kernel","Android Runtime","Uptime"};
        descriptions = new String[]{Build.MODEL,Build.MANUFACTURER,Build.DEVICE,Build.BOARD,Build.HARDWARE,Build.BRAND,Build.VERSION.RELEASE,Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName(),Build.VERSION.SDK_INT+"",Build.BOOTLOADER,Build.FINGERPRINT,Build.getRadioVersion(),getProperty("os.arch"),"ART"+getProperty("java.vm.version"),upTime};

        MyAdapter adapter = new MyAdapter(this,titles,descriptions);
        binding.generalList.setAdapter(adapter);

    }
    public class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String myTitles[];
        String myDescriptions[];

        MyAdapter(Context context,String[] myTitles, String[] myDescriptions){
            super(context,R.layout.tow_rows, androidx.core.R.id.title,myTitles);
            this.context = context;
            this.myTitles = myTitles;
            this.myDescriptions = myDescriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.tow_rows,parent,false);
            TextView titleTxt= row.findViewById(R.id.titleTxt);
            TextView desc = row.findViewById(R.id.descTxt);

            titleTxt.setText(myTitles[position]);
            desc.setText(myDescriptions[position]);

            return row;
        }
    }
    //hiding search view from this activity


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.search).setVisible(false);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//when back button in action bar is pressed then go to the previous activity.
        return true;
    }
}