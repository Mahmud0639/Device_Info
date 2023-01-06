package com.manuni.deviceinfo;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.manuni.deviceinfo.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolBar);

        binding.collapsingToolbar.setTitle("");
        binding.appBarLayout.setExpanded(true);

        binding.collapsingToolbar.setTitle("Device Info");

        binding.myRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        binding.myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myAdapter = new MyAdapter(MainActivity.this, getModels());
        binding.myRecyclerView.setAdapter(myAdapter);


        //getting mobile manufacturer name
        String manufacturer = Build.MANUFACTURER;
        //getting device model
        String model = Build.MODEL;
        //getting device android version
        String version = Build.VERSION.RELEASE;
        //getting android version name
        String verName = Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();//for example lollipop, marshmallow etc.

        binding.nameModel.setText(manufacturer.toUpperCase() + " " + model);
        binding.androidVersion.setText(version + "" + verName);

        //display android version image
        try {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                Glide.with(MainActivity.this).load(R.drawable.lollipop).into(binding.backdrop);
            }
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
                Glide.with(MainActivity.this).load(R.drawable.lollipop).into(binding.backdrop);
            }

            //evabe jotogulo dorkar kore nite hobe...

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ArrayList<Model> getModels() {
        ArrayList<Model> models = new ArrayList<>();
        Model p = new Model();
        p.setName("General");
        p.setImage(R.drawable.general);

        models.add(p);

        p = new Model();
        p.setName("Device Id");
        p.setImage(R.drawable.device_id);

        models.add(p);

        p = new Model();
        p.setName("Display");
        p.setImage(R.drawable.display);

        models.add(p);

        p = new Model();
        p.setName("Battery");
        p.setImage(R.drawable.battery_info);

        models.add(p);

        p = new Model();
        p.setName("User Apps");
        p.setImage(R.drawable.user_apps);

        models.add(p);

        p = new Model();
        p.setName("System Apps");
        p.setImage(R.drawable.system_apps);

        models.add(p);

        p = new Model();
        p.setName("Memory");
        p.setImage(R.drawable.memory);
        models.add(p);

        p = new Model();
        p.setName("CPU");
        p.setImage(R.drawable.cpu);
        models.add(p);

        p = new Model();
        p.setName("Sensor");
        p.setImage(R.drawable.sensor);
        models.add(p);

        p = new Model();
        p.setName("SIM");
        p.setImage(R.drawable.sim);
        models.add(p);

        return models;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
      //SearchView searchView1 = (SearchView) item.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myAdapter.getFilter().filter(query);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_settings){
            Toast.makeText(this, "Settings is clicked.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}