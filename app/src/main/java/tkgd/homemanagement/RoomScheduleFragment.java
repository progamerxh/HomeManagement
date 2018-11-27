package tkgd.homemanagement;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import tkgd.homemanagement.Adapter.DeviceScheduleAdapter;
import tkgd.homemanagement.Model.Device;

public class RoomScheduleFragment extends Fragment {
    private View view;
    private View mProgressView;
    private RecyclerView devicerecycleview;
    public static final String ROOM_NAME = "EXTRA_ROOM_NAME";
    public static final String ROOM = "EXTRA_ROOM";
    public static final String ROOM_ID = "ROOM_ID";
    public static final String SCENARIO_ID = "SCENARIO_ID";
    public static ArrayList<Device> devices;
    ArrayList<Device> roomDevices;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static final RoomScheduleFragment newInstance(String roomname, int room, String roomid, String scenarioid) {

        RoomScheduleFragment f = new RoomScheduleFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(ROOM_NAME, roomname);
        bdl.putString(ROOM_ID, roomid);
        bdl.putString(SCENARIO_ID, scenarioid);
        bdl.putInt(ROOM, room);
        f.setArguments(bdl);
        return f;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final String scenarioid = getArguments().getString(SCENARIO_ID);
        final String message = getArguments().getString(ROOM_NAME);
        final String roomid = getArguments().getString(ROOM_ID);
        int int_image = getArguments().getInt(ROOM);
        view = inflater.inflate(R.layout.new_scenario_pager, container, false);
        mProgressView = view.findViewById(R.id.progress);
        devicerecycleview = view.findViewById(R.id.schedule_recycleview);
        devicerecycleview.setItemAnimator(new DefaultItemAnimator());
        devicerecycleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        devices = new ArrayList<>();
        roomDevices = new ArrayList<>();
        showProgress(true);

        firebaseFirestore.collection("devices")
                .whereEqualTo("roomid", roomid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        roomDevices.clear();
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Device device = document.toObject(Device.class);
                                    device.setActive(false);
                                    device.setId(document.getId());
                                    roomDevices.add(device);
                                    if (scenarioid == null)
                                        devices.add(device);
                                }
                                if (scenarioid != null)
                                    FetchSchedules(roomid, scenarioid);
                                else {
                                    DeviceScheduleAdapter deviceScheduleAdapter = new DeviceScheduleAdapter(roomDevices, getContext());
                                    devicerecycleview.setAdapter(deviceScheduleAdapter);
                                    showProgress(false);
                                }
                            } else {
                            }
                        } else {
                            Log.d("Firebase", "Error getting devices: ", task.getException());
                        }
                    }
                });

        return view;
    }

    void FetchSchedules(String roomid, String scenarioid) {
        firebaseFirestore.collection("deviceschedules")
                .whereEqualTo("roomid", roomid)
                .whereEqualTo("scenarioid", scenarioid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Device tempdevice = document.toObject(Device.class);
                                    tempdevice.setActive(document.getBoolean("isActive"));
                                    for (int i = 0; i < roomDevices.size(); i++) {
                                        if (roomDevices.get(i).getId().equals(tempdevice.getId())) {
                                            roomDevices.set(i, tempdevice);
                                        }
                                        devices.add(roomDevices.get(i));
                                    }
                                }
                                DeviceScheduleAdapter deviceScheduleAdapter = new DeviceScheduleAdapter(roomDevices, getContext());
                                devicerecycleview.setAdapter(deviceScheduleAdapter);
                                showProgress(false);
                            } else {
                                Log.d("Firebase", "None getting schedules: ", task.getException());
                            }
                        } else {
                            Log.d("Firebase", "Error getting schedules: ", task.getException());
                        }
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            devicerecycleview.setVisibility(show ? View.GONE : View.VISIBLE);
            devicerecycleview.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    devicerecycleview.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            devicerecycleview.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
