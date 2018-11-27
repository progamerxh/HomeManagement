package tkgd.homemanagement.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tkgd.homemanagement.Activity.DeviceActivity;
import tkgd.homemanagement.Activity.NewDeviceActivity;
import tkgd.homemanagement.Activity.RoomActivity;
import tkgd.homemanagement.Model.Device;
import tkgd.homemanagement.R;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> {
    private Context context;
    private int mtype;
    private String roomid;
    public ArrayList<Device> devices;

    private Map<String, String> prop1 = new HashMap<>();
    private Map<String, String> prop2 = new HashMap<>();
    private Map<String, Integer> minilayouts = new HashMap<>();
    private Map<String, Integer> deviceicons = new HashMap<>();
    public String[] devicetypes = {"Light", "Climate", "Electricity", "Wifi", "Camera"};

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDeviceName;
        private ImageView imgDeviceIcon;
        private ImageView imgState;
        private TextView txtProp1;
        private TextView txtProp2;
        private ViewStub layoutMini;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtDeviceName = (TextView) itemView.findViewById(R.id.txtDeviceName);
            imgDeviceIcon = (ImageView) itemView.findViewById(R.id.imgDeviceIcon);
            imgState = (ImageView) itemView.findViewById(R.id.imgState);
            txtProp1 = (TextView) itemView.findViewById(R.id.txtProp1);
            txtProp2 = (TextView) itemView.findViewById(R.id.txtProp2);
            layoutMini = (ViewStub) itemView.findViewById(R.id.layoutMini);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mtype == 1) {
                        int pos = getAdapterPosition();
                        Intent intent = new Intent(context, DeviceActivity.class);
                        intent.putExtra("DEVICE_TYPE", devices.get(pos).getType());
                        context.startActivity(intent);
                    } else {
                        final BottomSheetDialog editdialog = new BottomSheetDialog(context);
                        editdialog.setContentView(R.layout.bottomsheet_edit_dialog);
                        ImageView imgDeviceicon = (ImageView) editdialog.findViewById(R.id.imgDeviceIcon);
                        TextView txtTitle = (TextView) editdialog.findViewById(R.id.txtTitle);
                        final EditText txtName = (EditText) editdialog.findViewById(R.id.txtName);
                        Button btnOk = (Button) editdialog.findViewById(R.id.btnOk);
                        Button btnCancel = (Button) editdialog.findViewById(R.id.btnCancel);

                        imgDeviceicon.setImageResource(deviceicons.get(devicetypes[getAdapterPosition()]));
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> data = new HashMap<>();
                                data.put("name", txtName.getText().toString());
                                data.put("type", devicetypes[getAdapterPosition()]);
                                data.put("roomid", roomid);
                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                firebaseFirestore.collection("devices")
                                        .add(data)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Intent intent = new Intent(context, RoomActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                context.startActivity(intent);
                                                ((Activity) context).finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });

                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                editdialog.hide();
                            }
                        });
                        editdialog.show();

                    }

                }
            });

        }

    }

    class AddItemViewHolder extends MyViewHolder {
        private FrameLayout frameLayout;
        private TextView txtName;

        public AddItemViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtItemName);
            txtName.setText("New device");
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frameBackGround);
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(context, NewDeviceActivity.class);
                    myIntent.putExtra("roomid", roomid);
                    context.startActivity(myIntent);
                }
            });
        }
    }

    public DeviceAdapter(ArrayList<Device> devices, String roomid, Context context, int type) {
        //
        minilayouts.put("Light", R.layout.mini_device_light);
        minilayouts.put("Climate", R.layout.mini_device_climate);
        minilayouts.put("Electricity", R.layout.mini_device_electricity);
        minilayouts.put("Wifi", R.layout.mini_device_wifi);
        minilayouts.put("Camera", R.layout.mini_device_camera);
        //
        deviceicons.put("Light", R.drawable.lightbulb);
        deviceicons.put("Climate", R.drawable.air_conditioner);
        deviceicons.put("Electricity", R.drawable.socket_selected);
        deviceicons.put("Wifi", R.drawable.wifi);
        deviceicons.put("Camera", R.drawable.cctv);

        prop1.put("Light", "");
        prop1.put("Climate", "Fan on");
        prop1.put("Electricity", "Normal");
        prop1.put("Wifi", "↓ 22.11 gb");
        prop1.put("Camera", "Normal");
        //
        prop2.put("Light", "");
        prop2.put("Climate", "Cold");
        prop2.put("Electricity", "");
        prop2.put("Wifi", "↑ 11.22 gb");
        prop2.put("Camera", "Alert");
        this.roomid = roomid;
        this.devices = devices;
        this.context = context;
        this.mtype = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -1) {
            View itemView = View.inflate(parent.getContext(), R.layout.add_item, null);
            return new AddItemViewHolder(itemView);
        }
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        switch (mtype) {
            case 0:
                holder.txtDeviceName.setText(devicetypes[position]);
                holder.imgDeviceIcon.setImageResource(deviceicons.get(devicetypes[position]));
                break;
            case 1:
                if (position == devices.size())
                    return;
                holder.txtDeviceName.setText(devices.get(position).getName());
                holder.imgDeviceIcon.setImageResource(deviceicons.get(devices.get(position).getType()));
                if (context == NewDeviceActivity.mContext)
                    return;
                holder.imgDeviceIcon.setVisibility(View.GONE);
                holder.imgState.setBackgroundResource(deviceicons.get(devices.get(position).getType()));
                holder.txtProp1.setText(prop1.get(devices.get(position).getType()));
                holder.txtProp2.setText(prop2.get(devices.get(position).getType()));
                holder.layoutMini.setLayoutResource(minilayouts.get(devices.get(position).getType()));
                View inflatedView = holder.layoutMini.inflate();
                if (devices.get(position).getType().equals("Wifi")) {
                    LineChart chartUsage = (LineChart) inflatedView.findViewById(R.id.chartUsage);
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
                    downloadSet.setColor(context.getColor(R.color.light_green));
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
                    xAxis.setTextColor(Color.WHITE);
                    xAxis.setDrawAxisLine(true);
                    xAxis.setDrawLabels(false);
                    xAxis.setTextSize(10f);
                    xAxis.setGridColor(context.getColor(R.color.dark_grey));
                    YAxis yAxisRight = chartUsage.getAxisRight();
                    yAxisRight.setEnabled(false);
                    YAxis yAxisLeft = chartUsage.getAxisLeft();
                    yAxisLeft.setAxisMinValue(0);
                    yAxisLeft.setSpaceTop(30);
                    yAxisLeft.setTextColor(Color.WHITE);
                    yAxisLeft.setTextSize(10);
                    yAxisLeft.setGridColor(context.getColor(R.color.dark_grey));
                    chartUsage.getDescription().setEnabled(false);
                    chartUsage.setNoDataText("No electricity consumed today!");
                    chartUsage.getLegend().setEnabled(false);
                    chartUsage.setHighlightPerTapEnabled(false);
                    chartUsage.setTouchEnabled(false);
                    chartUsage.invalidate(); // refresh
                }
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mtype == 1 && position == devices.size())
            return -1;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return (mtype == 1) ? devices.size() + 1 : devicetypes.length;
    }

}
