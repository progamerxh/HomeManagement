package tkgd.homemanagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tkgd.homemanagement.Adapter.MultiAdapter;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

public class RoomScheduleFragment extends Fragment {
    private View view;
    private RecyclerView devicerecycleview;
    public static final String ROOM_NAME = "EXTRA_ROOM_NAME";
    public static final String ROOM = "EXTRA_ROOM";

    public static final RoomScheduleFragment newInstance(String roomname, int room) {

        RoomScheduleFragment f = new RoomScheduleFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(ROOM_NAME, roomname);
        bdl.putInt(ROOM, room);
        f.setArguments(bdl);
        return f;

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final String message = getArguments().getString(ROOM_NAME);
        int int_image = getArguments().getInt(ROOM);
        view = inflater.inflate(R.layout.new_scenario_pager, container, false);
        devicerecycleview = view.findViewById(R.id.schedule_recycleview);
        devicerecycleview.setItemAnimator(new DefaultItemAnimator());
        devicerecycleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.room_device_cardview_margin);
        MultiAdapter multiAdapter = new MultiAdapter(getContext(), 4);
        devicerecycleview.setAdapter(multiAdapter);
        return view;

    }

}
