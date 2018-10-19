package tkgd.homemanagement.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import tkgd.homemanagement.Adapter.MultiAdapter;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;
import tkgd.homemanagement.Utility.JoyStickClass;
import tkgd.homemanagement.Utility.MyDefinition;

import static java.lang.Thread.sleep;

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
            case "Light":
                setContentView(R.layout.device_light);
                LightDeviceInitial();
                break;
            case "Climate":
                setContentView(R.layout.device_climate);
                ClimateDeviceInitial();
                break;
            case "Electricity":
                setContentView(R.layout.device_electricity);
                ElectricityDeviceInitial();
                break;
            case "Wifi":
                setContentView(R.layout.device_wifi);
                WifiDeviceInitial();
                break;
            case "Camera":
                setContentView(R.layout.device_camera);
                CameraDeviceInitial();
                break;
            default:
                break;
        }

    }

    private void CameraDeviceInitial() {
        final ImageView imgPhoto;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(deviceType);
        toolbar.setTitleTextColor(Color.WHITE);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        RelativeLayout layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);
        final JoyStickClass js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.joystick_stick);
        js.setStickSize(150, 150);
        js.setLayoutSize(800, 800);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);
        imgPhoto.setScaleX(2.f);
        imgPhoto.setScaleY(2.f);

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                       || arg1.getAction() == MotionEvent.ACTION_MOVE ) {
                    float Xseek = imgPhoto.getX() - js.getX() / 10;
                    float YSeek = imgPhoto.getY() - js.getY() / 10;
                    int direction = js.get8Direction();

                    imgPhoto.setX(Xseek);
                    imgPhoto.setY(YSeek);
                }
                if (arg1.getAction() == MotionEvent.ACTION_UP) {
                }
                return true;
            }
        });

    }


    private void WifiDeviceInitial() {
        LineChart chartUsage;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(deviceType);
        toolbar.setTitleTextColor(Color.WHITE);

        chartUsage = (LineChart) findViewById(R.id.chartUsage);
        int downloads[][] = {{1, 22}, {2, 35}, {3, 21}, {4, 37}, {5, 42}, {6, 11}, {7, 23}, {8, 47}};
        int uploads[][] = {{1, 33}, {2, 21}, {3, 41}, {4, 27}, {5, 33}, {6, 25}, {7, 22}, {8, 37}};
        List<Entry> entries1 = new ArrayList<Entry>();
        List<Entry> entries2 = new ArrayList<Entry>();
        for (int download[] : downloads) {
            entries1.add(new Entry(download[0], download[1]));
        }
        for (int upload[] : uploads) {
            entries2.add(new Entry(upload[0], upload[1]));
        }
        LineDataSet downloadSet = new LineDataSet(entries1, "Download");
        downloadSet.setColor(Color.GREEN);
        downloadSet.setDrawCircles(false);
        downloadSet.setDrawCircleHole(false);
        downloadSet.setDrawValues(false);
        downloadSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineDataSet uploadSet = new LineDataSet(entries2, "Upload");
        uploadSet.setColor(Color.WHITE);
        uploadSet.setDrawCircles(false);
        uploadSet.setDrawCircleHole(false);
        uploadSet.setDrawValues(false);
        uploadSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData();
        lineData.addDataSet(downloadSet);
        lineData.addDataSet(uploadSet);
        chartUsage.setData(lineData);
        XAxis xAxis = chartUsage.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(true);
        YAxis yAxisRight = chartUsage.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = chartUsage.getAxisLeft();
        yAxisLeft.setAxisMinValue(0);
        yAxisLeft.setSpaceTop(30);
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setTextSize(14);
        yAxisLeft.setDrawGridLines(false);
// set a custom value formatter
        Description description = new Description();
        description.setText("Down/Up load speed");
        description.setTextColor(Color.WHITE);
        description.setTextSize(12);
        chartUsage.setNoDataText("No electricity consumed today!");
        chartUsage.setDescription(description);
        chartUsage.setHighlightPerTapEnabled(false);

        chartUsage.invalidate(); // refresh
    }

    private void ElectricityDeviceInitial() {
        RecyclerView socketsrecyler;
        LineChart chartUsage;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(deviceType);
        toolbar.setTitleTextColor(Color.WHITE);

        socketsrecyler = (RecyclerView) findViewById(R.id.sockets_recycleview);
        LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        socketsrecyler.setItemAnimator(new DefaultItemAnimator());
        socketsrecyler.setLayoutManager(layoutManagerScenarios);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter socketadapter = new MultiAdapter(getApplicationContext(), 6);
        socketsrecyler.setAdapter(socketadapter);
        socketsrecyler.addItemDecoration(itemDecoration);

        chartUsage = (LineChart) findViewById(R.id.chartUsage);
        int usages[][] = {{1, 22}, {2, 35}, {3, 21}, {4, 37}, {5, 42}, {6, 11}, {7, 23}, {8, 47}};
        List<Entry> entries = new ArrayList<Entry>();
        for (int hourusage[] : usages) {
            entries.add(new Entry(hourusage[0], hourusage[1]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Wh");
        dataSet.setColor(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCircleColor(getResources().getColor(R.color.light_green));
        dataSet.setCircleRadius(5f);
        dataSet.setValueTextSize(10f);
        LineData lineData = new LineData(dataSet);
        chartUsage.setData(lineData);
        XAxis xAxis = chartUsage.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(true);
        YAxis yAxisRight = chartUsage.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = chartUsage.getAxisLeft();
        yAxisLeft.setAxisMinValue(0);
        yAxisLeft.setSpaceTop(30);
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setTextSize(14);
        yAxisLeft.setDrawGridLines(false);
// set a custom value formatter
        Description description = new Description();
        description.setText("Kilowatts hours");
        description.setTextColor(Color.WHITE);
        description.setTextSize(12);
        chartUsage.setNoDataText("No electricity consumed today!");
        chartUsage.setDescription(description);
        chartUsage.setHighlightPerTapEnabled(false);

        chartUsage.invalidate(); // refresh
    }

    private void LightDeviceInitial() {
        RecyclerView lightsrecycler, colorrecycler;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(deviceType);
        toolbar.setTitleTextColor(Color.WHITE);

        lightsrecycler = (RecyclerView) findViewById(R.id.lights_recycleview);
        LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        lightsrecycler.setItemAnimator(new DefaultItemAnimator());
        lightsrecycler.setLayoutManager(layoutManagerScenarios);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter lightsadapter = new MultiAdapter(getApplicationContext(), MyDefinition.ADAPTER_LIGHT);
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
        actionBar.setTitle(deviceType);
        toolbar.setTitleTextColor(Color.WHITE);

        moderecycler = (RecyclerView) findViewById(R.id.mode_recycleview);
        LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        moderecycler.setItemAnimator(new DefaultItemAnimator());
        moderecycler.setLayoutManager(layoutManagerScenarios);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter modeadapter = new MultiAdapter(getApplicationContext(), 2);
        moderecycler.setAdapter(modeadapter);
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
