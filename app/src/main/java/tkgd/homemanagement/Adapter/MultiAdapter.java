package tkgd.homemanagement.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import tkgd.homemanagement.Activity.DeviceActivity;
import tkgd.homemanagement.Activity.NewDeviceActivity;
import tkgd.homemanagement.Activity.NewRoomActivity;
import tkgd.homemanagement.Activity.NewScenarioActivity;
import tkgd.homemanagement.Model.Notification;
import tkgd.homemanagement.Model.ScenarioItem;
import tkgd.homemanagement.Model.System;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.MyDefinition;


public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MyViewHolder> {
    private int selectedPosition = -1;
    private ArrayList<String> devices;
    private ArrayList<ScenarioItem> scenarioItems;
    private ArrayList<Notification> notifications;
    private ArrayList<System> systems;
    private int[] colors = {android.R.color.white, R.color.pastel_1, R.color.pastel_2, R.color.pastel_3, R.color.pastel_4, R.color.light_blue, R.color.light_green};
    private String[] sockets = {"Wifi", "TV", "Fan", "Xaomi"};
    private String[] lights = {"All", "Light 1", "Light 2"};
    private String[] acmodes = {"Auto", "Cold", "Heat" ,"Dry" , "Fan"};
    private int[] acmodeicons = {R.drawable.autorenew, R.drawable.frozen, R.drawable.sunny,R.drawable.water,R.drawable.fan};
    private int[] deviceicons = {R.drawable.lightbulb, R.drawable.air_conditioner, R.drawable.socket_slected, R.drawable.wifi, R.drawable.cctv};
    private String[] prop1 ={"","Fan", "Normal", "↓ 22.11 gb", "Normal"};
    private String[] prop2 ={"","Cold", "", "↑ 11.22 gb", "Alert"};
    private Context context;
    private int mtype;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);

        }
    }


    public MultiAdapter(Context context, int type) {
        mtype = type;
        systems = new ArrayList<>();
        scenarioItems = new ArrayList<>();
        devices = new ArrayList<>();
        notifications = new ArrayList<>();
        systems.add(new System("My house", R.drawable.kitchen));
        systems.add(new System("Son's house", R.drawable.livingroom));
        scenarioItems.add(new ScenarioItem("I'm at home", false));
        scenarioItems.add(new ScenarioItem("Go to work", false));
        scenarioItems.add(new ScenarioItem("I'm outside", false));
        notifications.add(0, new Notification(false));
        notifications.add(0, new Notification(false));
        notifications.add(0, new Notification(false));
        notifications.add(0, new Notification(false));
        notifications.add(0, new Notification(false));
        notifications.add(0, new Notification(false));
        devices.add("Light");
        devices.add("Climate");
        devices.add("Electricity");
        devices.add("Wifi");
        devices.add("Camera");

        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        MyViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_device, parent, false);
                viewHolder = new DeviceViewHolder(view);
                break;
            case 2:
                view = View.inflate(parent.getContext(), R.layout.item_cardview_outline, null);
                viewHolder = new CardViewOutLineViewHolder(view);
                break;
            case 3:
                //Match_parent width
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_notification, parent, false);
                viewHolder = new NotificationViewHolder(view);
                break;
            case 4:
                //Match_parent width
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_device_schedule, parent, false);
                viewHolder = new DeviceScheduleViewHolder(view);
                break;
            case 5:
                //Match_parent width
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_light_color, parent, false);
                viewHolder = new ColorViewHolder(view);
                break;
            case 6:
                //Match_parent width
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_electricity_socket, parent, false);
                viewHolder = new SocketViewHolder(view);
                break;
            case 7:
                view = View.inflate(parent.getContext(), R.layout.item_cardview_outline, null);
                viewHolder = new CardViewOutLineViewHolder(view);
                break;
            case 8:
                view = View.inflate(parent.getContext(), R.layout.item_system, null);
                viewHolder = new SystemViewHolder(view);
                break;
            case 9:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_device, parent, false);
                viewHolder = new DeviceViewHolder(view);
                break;
            case 10:
                view = View.inflate(parent.getContext(), R.layout.item_cardview_outline, null);
                viewHolder = new CardViewOutLineViewHolder(view);
                break;
            case -1:
            case -2:
                view = View.inflate(parent.getContext(), R.layout.add_item, null);
                viewHolder = new AddItemViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // Current Restaurant
        switch (mtype) {
            case 0:
                return;
            case 1:
                if (position == devices.size())
                    return;
                DeviceViewHolder deviceViewHolder = (DeviceViewHolder) holder;
                deviceViewHolder.txtDeviceName.setText(devices.get(position));
                deviceViewHolder.imgDeviceIcon.setImageResource(deviceicons[position]);
                deviceViewHolder.imgState.setBackgroundResource(deviceicons[position]);
                deviceViewHolder.txtProp1.setText(prop1[position]);
                deviceViewHolder.txtProp2.setText(prop2[position]);
                break;
            case 2:
                if (position == scenarioItems.size())
                    return;
                CardViewOutLineViewHolder scenarioViewHolder = (CardViewOutLineViewHolder) holder;
                ScenarioItem scenarioItem = scenarioItems.get(position);
                scenarioViewHolder.txtName.setText(scenarioItem.getName());
                if (scenarioItem.isSelected()) {
                    scenarioViewHolder.imgicon.setChecked(true);
                    scenarioViewHolder.frameLayout.setBackgroundResource(R.drawable.background_gradient);
                } else {
                    scenarioViewHolder.imgicon.setChecked(false);
                    scenarioViewHolder.frameLayout.setBackgroundResource(R.drawable.item_border);
                }
                break;
            case 3:
                NotificationViewHolder notificationViewHolder = (NotificationViewHolder) holder;
                Notification notification = notifications.get(position);
                if (notification.isSelected()) {
                    notificationViewHolder.cardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.dark_grey));
                }
                break;
            case 4:
                DeviceScheduleViewHolder scheduleViewHolder = (DeviceScheduleViewHolder) holder;
                scheduleViewHolder.txtDevice.setText(devices.get(position));
                scheduleViewHolder.imgDeviceIcon.setImageResource(deviceicons[position]);
                break;
            case 5:
                final ColorViewHolder colorViewHolder = (ColorViewHolder) holder;
                colorViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == colors.length)
                            return;
                        if (!colorViewHolder.imageView.isSelected()) {
                            colorViewHolder.imageView.getBackground().setColorFilter(context.getColor(colors[position]),
                                    PorterDuff.Mode.ADD);
                            colorViewHolder.imageView.setSelected(true);
                        } else {
                            colorViewHolder.imageView.getBackground().setColorFilter(context.getColor(colors[position]),
                                    PorterDuff.Mode.MULTIPLY);
                            colorViewHolder.imageView.setSelected(false);
                        }
                    }
                });
                if (position == colors.length) {
                    colorViewHolder.imageView.setImageResource(R.drawable.plus_circle_outline);
                    colorViewHolder.imageView.getBackground().setColorFilter(context.getColor(R.color.dark_grey),
                            PorterDuff.Mode.MULTIPLY);
                } else
                    colorViewHolder.imageView.getBackground().setColorFilter(context.getColor(colors[position]),
                            PorterDuff.Mode.MULTIPLY);
                break;
            case 6:
                final SocketViewHolder socketViewHolder = (SocketViewHolder) holder;

                socketViewHolder.txtSocketName.setText(sockets[position]);
                socketViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        socketViewHolder.imageView.setSelected(!socketViewHolder.imageView.isSelected());
                    }
                });

                break;
            case 7:
                if (position == lights.length)
                    return;
                final CardViewOutLineViewHolder lightViewHolder = (CardViewOutLineViewHolder) holder;
                lightViewHolder.txtName.setText(lights[position]);
                lightViewHolder.imgicon.setBackgroundDrawable(context.getDrawable(R.drawable.lightbulb));
                lightViewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lightViewHolder.frameLayout.setSelected(!lightViewHolder.frameLayout.isSelected());
                    }
                });
                break;
            case 8:
                SystemViewHolder systemViewHolder = (SystemViewHolder) holder;
                if (position == systems.size()) {
                    systemViewHolder.txtSystem.setText("New system");
                    systemViewHolder.imgPhoto.setImageResource(R.drawable.plus_circle_outline);
                    systemViewHolder.txtRoomCount.setText("");
                } else {
                    systemViewHolder.txtSystem.setText(systems.get(position).getName());
                    systemViewHolder.imgPhoto.setImageResource(systems.get(position).getPhotoID());
                }
                break;
            case 9:
                DeviceViewHolder listdeviceViewHolder = (DeviceViewHolder) holder;
                listdeviceViewHolder.txtDeviceName.setText(devices.get(position));
                listdeviceViewHolder.imgDeviceIcon.setImageResource(deviceicons[position]);
                break;
            case 10:
                final CardViewOutLineViewHolder acmodeViewHolder = (CardViewOutLineViewHolder) holder;
                acmodeViewHolder.txtName.setText(acmodes[position]);
                acmodeViewHolder.imgicon.setBackgroundDrawable(context.getDrawable(acmodeicons[position]));
                acmodeViewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        acmodeViewHolder.frameLayout.setSelected(!acmodeViewHolder.frameLayout.isSelected());
                    }
                });
                break;
            default:
                break;
        }
    }


    @Override
    public int getItemCount() {
        switch (mtype) {
            case 0:
                return 1;
            case 1:
                return devices.size() + 1;
            case 2:
                return scenarioItems.size() + 1;
            case 3:
                return notifications.size();
            case 4:
                return devices.size();
            case 5:
                return colors.length + 1;
            case 6:
                return sockets.length;
            case 7:
                return lights.length;
            case 8:
                return systems.size() + 1;
            case 9:
                return deviceicons.length;
            case 10:
                return acmodes.length;
            default:
                return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ((mtype == 1) && (position == (devices.size())))
            return -1;
        else if ((mtype == 0) && (position == 0))
            return -1;
        else if (mtype == 2 && (position == (scenarioItems.size())))
            return -2;
        return mtype;
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
            switch (mtype) {
                case 0:
                case 1:
                    txtName.setText("New device");
                    break;
                case 2:
                    widthdp = 150f;
                    heightdp = 100f;
                    txtName.setText("New scenario");

                    widthpx = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            widthdp,
                            r.getDisplayMetrics()
                    );
                    heightpx = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            heightdp,
                            r.getDisplayMetrics()
                    );
                    frameLayout.setLayoutParams(new FrameLayout.LayoutParams((int) widthpx, (int) heightpx));
                    break;
            }
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mtype == 2) {
                        Intent myIntent = new Intent(context, NewScenarioActivity.class);
                        context.startActivity(myIntent);
                    } else {
                        Intent myIntent = new Intent(context, NewDeviceActivity.class);
                        context.startActivity(myIntent);
                    }
                }
            });
        }
    }

    class DeviceViewHolder extends MyViewHolder {
        private TextView txtDeviceName;
        private ImageView imgDeviceIcon;
        private ImageView imgState;
        private TextView txtProp1;
        private TextView txtProp2;
        public DeviceViewHolder(View itemView) {
            super(itemView);
            txtDeviceName = (TextView) itemView.findViewById(R.id.txtDeviceName);
            imgDeviceIcon = (ImageView) itemView.findViewById(R.id.imgDeviceIcon);
            imgState= (ImageView) itemView.findViewById(R.id.imgState);
            txtProp1 = (TextView) itemView.findViewById(R.id.txtProp1);
            txtProp2 = (TextView) itemView.findViewById(R.id.txtProp2);
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
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final BottomSheetDialog managedialog = new BottomSheetDialog(context);
                        managedialog.setContentView(R.layout.bottomsheet_dialog);
                        final BottomSheetDialog undodialog = new BottomSheetDialog(context);
                        undodialog.setContentView(R.layout.undo_dialog);
                        LinearLayout Edit = (LinearLayout) managedialog.findViewById(R.id.Edit);
                        LinearLayout Delete = (LinearLayout) managedialog.findViewById(R.id.Delete);
                        View view = ((Activity) context).findViewById(R.id.layoutRoom);
                        final Snackbar snackbar = Snackbar.make(view, "Device " + txtDeviceName.getText() + " is deleted", Snackbar.LENGTH_SHORT);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                snackbar.dismiss();
                            }
                        });

                        Edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                managedialog.hide();

                            }
                        });
                        Delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                managedialog.hide();

                                snackbar.show();
                            }
                        });
                        managedialog.show();
                        return true;
                    }
                });
            }
        }
    }

    class CardViewOutLineViewHolder extends MyViewHolder {
        private ToggleButton imgicon;
        private FrameLayout frameLayout;
        private TextView txtName;

        public CardViewOutLineViewHolder(View itemView) {
            super(itemView);
            imgicon = (ToggleButton) itemView.findViewById(R.id.btnIcon);
            txtName = (TextView) itemView.findViewById(R.id.txtScenarioName);
            imgicon.setClickable(false);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frameBackGround);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int oldpos = selectedPosition;
                    selectedPosition = getAdapterPosition();
                    if (selectedPosition != oldpos) {
                        ScenarioItem scenarioItem = null;
                        if (oldpos != -1) {
                            scenarioItem = scenarioItems.get(oldpos);
                            scenarioItem.setSelected(!scenarioItem.isSelected());
                        }
                        scenarioItem = scenarioItems.get(selectedPosition);
                        scenarioItem.setSelected(!scenarioItem.isSelected());
                        notifyDataSetChanged();
                    }

                }
            });
            if (mtype == MyDefinition.ADAPTER_SCENARIO) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final BottomSheetDialog managedialog = new BottomSheetDialog(context);
                        managedialog.setContentView(R.layout.bottomsheet_dialog);
                        final BottomSheetDialog undodialog = new BottomSheetDialog(context);
                        undodialog.setContentView(R.layout.undo_dialog);
                        LinearLayout Edit = (LinearLayout) managedialog.findViewById(R.id.Edit);
                        LinearLayout Delete = (LinearLayout) managedialog.findViewById(R.id.Delete);
                        View view = ((Activity) context).findViewById(R.id.layoutScenario);
                        final Snackbar snackbar = Snackbar.make(view, "Scenario " + txtName.getText() + " is deleted", Snackbar.LENGTH_SHORT);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });

                        Edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                managedialog.hide();
                                Intent intent = new Intent(context,NewScenarioActivity.class);
                                intent.putExtra("SCENARIO_NAME", scenarioItems.get(getAdapterPosition()).getName());
                                context.startActivity(intent);


                            }
                        });
                        Delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                managedialog.hide();

                                snackbar.show();
                            }
                        });
                        managedialog.show();
                        return true;
                    }
                });
            }
        }
    }

    class NotificationViewHolder extends MyViewHolder {
        private CardView cardview;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int selectedPosition = getAdapterPosition();
                    Notification notification = null;
                    notification = notifications.get(selectedPosition);
                    notification.setSelected(true);
                    notifyDataSetChanged();

                }
            });
        }
    }

    class DeviceScheduleViewHolder extends MyViewHolder {
        private CardView cardview;
        private Switch switchBtn;
        private ImageView imgDeviceIcon;
        private TextView txtDevice;
        private TextView txtConfig;
        private TextView txtState;

        public DeviceScheduleViewHolder(View itemView) {
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
                }
            });
        }
    }

    class ColorViewHolder extends MyViewHolder {
        private ImageView imageView;
        private int oldPos = -1;
        private int curPos = -1;

        public ColorViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgColor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int oldpos = curPos;
                    curPos = getAdapterPosition();

                }

            });
        }
    }

    class SocketViewHolder extends MyViewHolder {
        private ImageView imageView;
        private TextView txtSocketName;

        public SocketViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgSocket);
            txtSocketName = (TextView) itemView.findViewById(R.id.txtSocketName);
        }
    }

    class SystemViewHolder extends MyViewHolder {
        private ImageView imgPhoto;
        private TextView txtSystem;
        private TextView txtRoomCount;

        public SystemViewHolder(View itemView) {
            super(itemView);
            imgPhoto = (ImageView) itemView.findViewById(R.id.imgPhoto);
            txtSystem = (TextView) itemView.findViewById(R.id.txtSystemName);
            txtRoomCount = (TextView) itemView.findViewById(R.id.txtRoomCount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == systems.size()) {
                        Intent myIntent = new Intent(context, NewRoomActivity.class);
                        myIntent.putExtra("TYPE", "SYSTEM");
                        context.startActivity(myIntent);
                    }
                }
            });
        }
    }
}

