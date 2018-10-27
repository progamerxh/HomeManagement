package tkgd.homemanagement.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tkgd.homemanagement.Model.Notification;
import tkgd.homemanagement.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private Context context;
    private int mtype;
    private ArrayList<Notification> notifications;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardview;
        private ImageView imgIcon;
        private TextView txtContent;
        private TextView txtTime;
        public MyViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
            imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
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


    public NotificationAdapter(Context context, int type) {
        notifications.add(0, new Notification( R.drawable.cctv,"Some one ring front door. Open the door?","1 minute",false));
        notifications.add(0, new Notification( R.drawable.frozen,"The house is getting too cold, please increase temperature!","2 hours",false));
        notifications.add(0, new Notification( R.drawable.lightbulb,"Lights have been turned on for over 12 hours. Turn off?","4 hours",false));
        notifications.add(0, new Notification( R.drawable.lightbulb,"Lights have been turned on for over 12 hours. Turn off?","1 day",false));
        notifications.add(0, new Notification( R.drawable.air_conditioner,"Outside's atmosphere is hot. Turn on AC?","1 day",false));
        this.context = context;
        this.mtype = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Notification notification = notifications.get(position);
        holder.imgIcon.setImageResource(notification.getIconID());
        holder.txtContent.setText(notification.getContent());
        holder.txtTime.setText(notification.getTime());

        if (notification.isSelected()) {
            holder.txtContent.setTextColor(context.getColor( R.color.light_grey));
            holder.txtTime.setTextColor(context.getColor( R.color.light_grey));
            holder.cardview.setCardBackgroundColor(context.getColor( R.color.dark_grey));
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

}
