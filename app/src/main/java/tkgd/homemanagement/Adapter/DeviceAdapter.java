package tkgd.homemanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import tkgd.homemanagement.Activity.DeviceActivity;
import tkgd.homemanagement.Activity.NewDeviceActivity;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.MyDefinition;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> {
    private Context context;
    private int mtype;
    public ArrayList<String> devices;
    private String[] prop1 = {"", "Fan on", "Normal", "↓ 22.11 gb", "Normal"};
    private String[] prop2 = {"", "Cold", "", "↑ 11.22 gb", "Alert"};
    private int[] minilayouts = {R.layout.mini_device_light, R.layout.mini_device_climate, R.layout.mini_device_electricity, R.layout.mini_device_wifi, R.layout.mini_device_camera};
    private int[] deviceicons = {R.drawable.lightbulb, R.drawable.air_conditioner, R.drawable.socket_selected, R.drawable.wifi, R.drawable.cctv};

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
            if (mtype == MyDefinition.ADAPTER_DEVICE) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        Intent intent = new Intent(context, DeviceActivity.class);
                        intent.putExtra("DEVICE_TYPE", devices.get(pos));
                        context.startActivity(intent);

                    }
                });

            }
        }

    }

    class AddItemViewHolder extends MyViewHolder {
        private FrameLayout frameLayout;
        private TextView txtName;

        public AddItemViewHolder(View itemView) {
            super(itemView);
            Resources r = context.getResources();
            float widthdp = 0f;
            float heightdp = 0f;
            float widthpx = 0f;
            float heightpx = 0f;
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frameBackGround);
            txtName = (TextView) itemView.findViewById(R.id.txtItemName);
            txtName.setText("New device");

            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(context, NewDeviceActivity.class);
                    context.startActivity(myIntent);
                }
            });
        }
    }

    public DeviceAdapter(Context context, int type) {
        devices = new ArrayList<>();
        devices.add("Light");
        devices.add("Climate");
        devices.add("Electricity");
        devices.add("Wifi");
        devices.add("Camera");
        this.context = context;
        this.mtype = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mtype == -1) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.add_item, parent, false);
            return new AddItemViewHolder(itemView);
        }
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position == devices.size())
            return;
        holder.txtDeviceName.setText(devices.get(position));
        holder.imgDeviceIcon.setImageResource(deviceicons[position]);
        if (context == NewDeviceActivity.mContext)
            return;
        holder.imgDeviceIcon.setVisibility(View.GONE);
        holder.imgState.setBackgroundResource(deviceicons[position]);
        holder.txtProp1.setText(prop1[position]);
        holder.txtProp2.setText(prop2[position]);
        holder.layoutMini.setLayoutResource(minilayouts[position]);
        View inflatedView = holder.layoutMini.inflate();
        if (position == 3) {
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
    }

    @Override
    public int getItemCount() {
        return (mtype == -1) ? 0 : devices.size() + 1;
    }

}
