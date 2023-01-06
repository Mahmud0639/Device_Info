package com.manuni.deviceinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.manuni.deviceinfo.databinding.ActivityDisplayBinding;

import java.text.NumberFormat;

public class DisplayActivity extends AppCompatActivity {
    ActivityDisplayBinding binding;
    private String[] titles;
    private String[] descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setTitle("Display Info");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //screen size in pixels
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        String resolution = width+"x"+height+" pixels";

        //physical size in inch
        double x = Math.pow(width/displayMetrics.xdpi,2);
        double y = Math.pow(height/displayMetrics.ydpi,2);

        double screenInches = Math.sqrt(x+y);

        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);

        String screenInchesOutput = format.format(screenInches);

        //display refresh
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refreshRate = display.getRefreshRate();

        NumberFormat format1 = NumberFormat.getNumberInstance();
        format1.setMinimumFractionDigits(1);
        format1.setMaximumFractionDigits(1);

        String displayRefreshRateOutput = format.format(refreshRate);


        titles = new String[]{"Resolution","Density","Physical Size","Refresh Rate","Orientation"};
        descriptions = new String[]{resolution,DisplayMetrics.DENSITY_XHIGH+" dpi (xhdpi)",screenInchesOutput+" inch",displayRefreshRateOutput+" Hz",this.getResources().getConfiguration().orientation+""};

        MyDisplayAdapter myDisplayAdapter = new MyDisplayAdapter(this,titles,descriptions);
        binding.displayList.setAdapter(myDisplayAdapter);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.search).setVisible(false);
        return true;
    }
    private class MyDisplayAdapter extends ArrayAdapter<String>{
        Context context;
        String[] myTitles;
        String[] myDescriptions;

        public MyDisplayAdapter(Context context,String[] titles, String[] descriptions){
            super(context,R.layout.tow_rows, androidx.core.R.id.title,titles);

            this.context = context;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
          View view =  layoutInflater.inflate(R.layout.tow_rows,parent,false);

            TextView myTitleTxt = view.findViewById(R.id.titleTxt);
            TextView myDescTxt = view.findViewById(R.id.descTxt);

            myTitleTxt.setText(titles[position]);
            myDescTxt.setText(descriptions[position]);

            return view;
        }

    }

}