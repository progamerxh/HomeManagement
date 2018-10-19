package tkgd.homemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import tkgd.homemanagement.Activity.NewRoomActivity;

public class RoomPhotoFragment extends Fragment {

    private View view;
    private ImageView iv_image;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String IMAGE = "EXTRA_IMAGE";

    public static final RoomPhotoFragment newInstance(String roomname, int int_image) {

        RoomPhotoFragment f = new RoomPhotoFragment();

        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, roomname);
        bdl.putInt(IMAGE, int_image);
        f.setArguments(bdl);

        return f;

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final String message = getArguments().getString(EXTRA_MESSAGE);
        int int_image = getArguments().getInt(IMAGE);
        view = inflater.inflate(R.layout.room_pager_fragment, container, false);
        LinearLayout layoutConnection = (LinearLayout) view.findViewById(R.id.layoutConnection);
        final ImageView iv_image = (ImageView) view.findViewById(R.id.iv_image);

        if (message == "NEW_ROOM") {
            layoutConnection.setVisibility(View.INVISIBLE);
            iv_image.setPadding(150,150,150,150);
        }
        iv_image.setImageResource(int_image);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message == "NEW_ROOM") {
                    Intent intent = new Intent(getContext(), NewRoomActivity.class);
                    intent.putExtra("TYPE", "ROOM");
                    startActivity(intent);
                }
            }
        });

        return view;

    }

}
