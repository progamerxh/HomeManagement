package tkgd.homemanagement.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tkgd.homemanagement.Activity.NewScenarioActivity;
import tkgd.homemanagement.Model.Device;
import tkgd.homemanagement.R;

public class DeviceScheduleAdapter extends RecyclerView.Adapter<DeviceScheduleAdapter.MyViewHolder> {
    private Context context;
    private int mtype;
    public ArrayList<Device> devices;
    private Map<String, Integer> deviceicons = new HashMap<>();

    public interface OnStatusChangeListener {
        void onStatusChange(Device ConfigeDevice);
    }

    private final OnStatusChangeListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardview;
        private Switch switchBtn;
        private ImageView imgDeviceIcon;
        private TextView txtDevice;
        private TextView txtConfig;
        private TextView txtState;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
            switchBtn = (Switch) itemView.findViewById(R.id.switchBtn);
            txtDevice = (TextView) itemView.findViewById(R.id.txtDeviceName);
            txtConfig = (TextView) itemView.findViewById(R.id.txtConfig);
            txtState = (TextView) itemView.findViewById(R.id.txtState);
            imgDeviceIcon = (ImageView) itemView.findViewById(R.id.imgDeviceIcon);

            switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_grey));
                        txtDevice.setTextColor(Color.WHITE);
                        txtConfig.setTextColor(Color.WHITE);
                        txtState.setText("Device will turn on");
                        txtState.setTextColor(ContextCompat.getColor(context, R.color.light_green));
                    } else {
                        txtDevice.setTextColor(ContextCompat.getColor(context, R.color.light_grey));
                        cardview.setCardBackgroundColor(Color.TRANSPARENT);
                        txtState.setText("Device will turn off");
                        txtState.setTextColor(ContextCompat.getColor(context, R.color.light_grey));
                        txtConfig.setTextColor(ContextCompat.getColor(context, R.color.light_grey));
                    }
                    Device device = devices.get(getAdapterPosition());
                    device.setActive(isChecked);
                    listener.onStatusChange(devices.get(getAdapterPosition()));
                }
            });
        }

    }


    public DeviceScheduleAdapter(ArrayList<Device> devices, Context context) {
        deviceicons.put("Light", R.drawable.lightbulb);
        deviceicons.put("Climate", R.drawable.air_conditioner);
        deviceicons.put("Electricity", R.drawable.socket_selected);
        deviceicons.put("Wifi", R.drawable.wifi);
        deviceicons.put("Camera", R.drawable.cctv);
        this.listener = NewScenarioActivity.deviceStatusListener;
        this.devices = devices;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device_schedule, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtDevice.setText(devices.get(position).getName());
        holder.imgDeviceIcon.setImageResource(deviceicons.get(devices.get(position).getType()));
        holder.switchBtn.setChecked( devices.get(position).getActive());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

}
