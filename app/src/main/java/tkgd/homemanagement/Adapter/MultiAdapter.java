package tkgd.homemanagement.Adapter;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import tkgd.homemanagement.Activity.DeviceActivity;
import tkgd.homemanagement.Activity.NewDeviceActivity;
import tkgd.homemanagement.Activity.NewScenarioActivity;
import tkgd.homemanagement.Activity.RoomActivity;
import tkgd.homemanagement.Model.Notification;
import tkgd.homemanagement.Model.ScenarioItem;
import tkgd.homemanagement.R;


public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MyViewHolder> {
    private int selectedPosition = -1;
    private ArrayList<String> devices;
    private ArrayList<ScenarioItem> scenarioItems;
    private ArrayList<Notification> notifications;
    private int[] colors = {R.color.pastel_1, R.color.pastel_2, R.color.pastel_3, R.color.pastel_4, R.color.light_grey, R.color.light_blue, R.color.light_green, R.color.light_red};
    private Context context;
    private int mtype;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);

        }
    }


    public MultiAdapter(Context context, int type) {
        mtype = type;
        scenarioItems = new ArrayList<>();
        devices = new ArrayList<>();
        notifications = new ArrayList<>();
        scenarioItems.add(new ScenarioItem("I'm at home", false));
        scenarioItems.add(new ScenarioItem("Go to work", false));
        scenarioItems.add(new ScenarioItem("I'm outside", false));
        scenarioItems.add(new ScenarioItem());
        notifications.add(0, new Notification(false));
        notifications.add(0, new Notification(false));
        notifications.add(0, new Notification(false));
        notifications.add(0, new Notification(false));
        notifications.add(0, new Notification(false));
        notifications.add(0, new Notification(false));
        devices.add("LIGHT");
        devices.add("CLIMATE");
        devices.add("");
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
                view = View.inflate(parent.getContext(), R.layout.item_scenario, null);
                viewHolder = new ScenarioViewHolder(view);
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
            case -1:
            case -2:
                view = View.inflate(parent.getContext(), R.layout.add_item, null);
                viewHolder = new AddItemViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Current Restaurant
        switch (mtype) {
            case 0:
            case 1:
                break;
            case 2:
                if (position == scenarioItems.size() - 1)
                    return;
                ScenarioViewHolder scenarioViewHolder = (ScenarioViewHolder) holder;
                ScenarioItem scenarioItem = scenarioItems.get(position);
                scenarioViewHolder.txtName.setText(scenarioItem.getName());
                if (scenarioItem.isSelected()) {
                    scenarioViewHolder.imgicon.setChecked(true);
                    scenarioViewHolder.frameLayout.setBackgroundResource(R.drawable.item_full);
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
                break;
            case 5:
                ColorViewHolder colorViewHolder = (ColorViewHolder) holder;

                if (position == colors.length) {
                    colorViewHolder.imageView.setImageResource(R.drawable.plus_circle_outline);
                    colorViewHolder.imageView.getBackground().setColorFilter(context.getResources().getColor(R.color.dark_grey),
                            PorterDuff.Mode.MULTIPLY);
                } else
                    colorViewHolder.imageView.getBackground().setColorFilter(context.getResources().getColor(colors[position]),
                            PorterDuff.Mode.MULTIPLY);
                break;
            default:
                break;
        }
    }


    @Override
    public int getItemCount() {
        switch (mtype) {
            case 0:
            case 1:
                return devices.size();
            case 2:
                return scenarioItems.size();
            case 3:
                return notifications.size();
            case 4:
                return notifications.size();
            case 5:
                return colors.length + 1;
            default:
                return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ((mtype == 1 || mtype == 0) && (position == (devices.size() - 1)))
            return -1;
        else if (mtype == 2 && (position == (scenarioItems.size() - 1)))
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
        public DeviceViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Toast.makeText(context, "Item choosen: " + devices.get(pos), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, DeviceActivity.class);
                    intent.putExtra("DEVICE_TYPE", devices.get(pos));
                    context.startActivity(intent);
                }
            });
        }
    }

    class ScenarioViewHolder extends MyViewHolder {
        private ToggleButton imgicon;
        private FrameLayout frameLayout;
        private TextView txtName;

        public ScenarioViewHolder(View itemView) {
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
                    Intent myIntent = new Intent(context, RoomActivity.class);
                    context.startActivity(myIntent);
                }
            });
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
                    Toast.makeText(context, "Item choosen: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
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

        public DeviceScheduleViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
            switchBtn = (Switch) itemView.findViewById(R.id.switchBtn);
            switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        cardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_grey));
                    else
                        cardview.setCardBackgroundColor(Color.TRANSPARENT);
                }
            });
        }
    }

    class ColorViewHolder extends MyViewHolder {
        private ImageView imageView;

        public ColorViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgColor);
        }
    }
}

