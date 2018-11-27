package tkgd.homemanagement.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tkgd.homemanagement.Adapter.DeviceScheduleAdapter;
import tkgd.homemanagement.Adapter.PageAdapter;
import tkgd.homemanagement.Model.Device;
import tkgd.homemanagement.R;
import tkgd.homemanagement.RoomScheduleFragment;

public class NewScenarioActivity extends AppCompatActivity {
    private EditText txtScenarioName;
    private FirebaseFirestore firebaseFirestore;
    private String systemid;
    private String scenarioid;
    private Boolean isNewScenario;
    private ViewPager viewPager;
    private ArrayList<Device> deviceScheduleList;
    public static DeviceScheduleAdapter.OnStatusChangeListener deviceStatusListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_scenario);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        txtScenarioName = (EditText) findViewById(R.id.txtScenarioName);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            actionBar.setTitle(extras.getString("SCENARIO_NAME"));
            txtScenarioName.setText(extras.getString("SCENARIO_NAME"));
            scenarioid = extras.getString("SCENARIO_ID");
            systemid = extras.getString("systemid");
            isNewScenario = false;
        } else {
            actionBar.setTitle("New scenario");
            isNewScenario = true;
        }

        toolbar.setTitleTextColor(Color.WHITE);
        viewPager = (ViewPager) findViewById(R.id.roomPager);
        SetPagerAdapter();

        deviceStatusListener = new DeviceScheduleAdapter.OnStatusChangeListener() {
            @Override
            public void onStatusChange(Device ConfigeDevice) {
                deviceScheduleList = RoomScheduleFragment.devices;
                for (Device device : RoomScheduleFragment.devices) {
                    if (device.getId() == ConfigeDevice.getId()) {
                        device.setActive(ConfigeDevice.getActive());
                        device.setConfig(ConfigeDevice.getConfig());
                    }
                }
            }
        };
    }

    private void SetPagerAdapter() {
        final List<Fragment> fList = new ArrayList<Fragment>();
        firebaseFirestore.collection("rooms")
                .whereEqualTo("systemid", systemid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    fList.add(RoomScheduleFragment.newInstance(document.get("name").toString(), 1, document.getId(), scenarioid));
                                }
                            } else {
                            }
                            viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), fList, 1));
                        } else {
                            Log.d("Firebase", "Error getting system: ", task.getException());
                        }
                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scenario_setting_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_save);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Cancel create new scenario?");
                builder.setMessage("Are you sure to discard all?");
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackPressed();
                    }
                });
                builder.show();
                return true;
            case R.id.menu_save:
                SaveScenario();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SaveScenario() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", txtScenarioName.getText().toString());
        data.put("systemid", systemid);
        if (isNewScenario)
            data.put("createdtime", new Date());
        firebaseFirestore.collection("scenarios")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        SaveSchedules(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void SaveSchedules(String scenarioid) {
        for (final Device device : deviceScheduleList) {
            Map<String, Object> data = new HashMap<>();
            data.put("scenarioid", scenarioid);
            data.put("roomid", device.getRoomid());
            data.put("type", device.getType());
            data.put("name", device.getName());
            data.put("isActive", device.getActive());
            data.put("id", device.getId());
            firebaseFirestore.collection("deviceschedules")
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            if (device == deviceScheduleList.get(deviceScheduleList.size() - 1)) {
                                Intent intent = new Intent(getApplicationContext(), ScenarioActivity.class);
                                intent.putExtra("systemid", systemid);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }
}
