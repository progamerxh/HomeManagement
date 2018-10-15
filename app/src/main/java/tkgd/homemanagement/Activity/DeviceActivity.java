package tkgd.homemanagement.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import tkgd.homemanagement.Adapter.MultiAdapter;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

public class DeviceActivity extends AppCompatActivity {
    private String deviceType;
    private int deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            deviceType = extras.getString("DEVICE_TYPE");
            deviceId = extras.getInt("DEVICE_ID");
        }
        switch (deviceType) {
            case "LIGHT":
                setContentView(R.layout.device_light);
                LightDeviceInitial();
                break;
            case "CLIMATE":
                setContentView(R.layout.device_climate);
                ClimateDeviceInitial();
                break;
            default:
                break;
        }

    }

    private void LightDeviceInitial(){
        RecyclerView lightsrecycler, colorrecycler;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Lighting");
        toolbar.setTitleTextColor(Color.WHITE);

        lightsrecycler = (RecyclerView) findViewById(R.id.lights_recycleview);
        LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        lightsrecycler.setItemAnimator(new DefaultItemAnimator());
        lightsrecycler.setLayoutManager(layoutManagerScenarios);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter lightsadapter = new MultiAdapter(getApplicationContext(), 2);
        lightsrecycler.setAdapter(lightsadapter);
        lightsrecycler.addItemDecoration(itemDecoration);

        colorrecycler = (RecyclerView) findViewById(R.id.color_recycler);
        colorrecycler.setItemAnimator(new DefaultItemAnimator());
        colorrecycler.setLayoutManager(new GridLayoutManager(this, 2));

        MultiAdapter coloradapter = new MultiAdapter(getApplicationContext(), 5);
        colorrecycler.setAdapter(coloradapter);
        colorrecycler.addItemDecoration(itemDecoration);
    }

    private void ClimateDeviceInitial() {
         RecyclerView moderecycler;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Climate");
        toolbar.setTitleTextColor(Color.WHITE);

        moderecycler = (RecyclerView) findViewById(R.id.mode_recycleview);
        LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        moderecycler.setItemAnimator(new DefaultItemAnimator());
        moderecycler.setLayoutManager(layoutManagerScenarios);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter lightsadapter = new MultiAdapter(getApplicationContext(), 2);
        moderecycler.setAdapter(lightsadapter);
        moderecycler.addItemDecoration(itemDecoration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.device_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.stateSwitch);
        menuItem.setActionView(R.layout.switch_layout);
        View actionView = menuItem.getActionView();
        Switch stateSwitch = (Switch) actionView.findViewById(R.id.switchActionBar);
        stateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "State: " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
