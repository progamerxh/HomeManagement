package tkgd.homemanagement.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tkgd.homemanagement.Activity.NewRoomActivity;
import tkgd.homemanagement.Activity.ScenarioActivity;
import tkgd.homemanagement.Model.System;
import tkgd.homemanagement.R;

public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.MyViewHolder> {
    private Context context;
    private int mtype;
    private ArrayList<System> systems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;
        private TextView txtSystem;
        private TextView txtRoomCount;

        public MyViewHolder(View itemView) {
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
                    else
                    {
                        Intent myIntent = new Intent(context, ScenarioActivity.class);
                        myIntent.putExtra("systemid", systems.get(getAdapterPosition()).getId());
                        myIntent.putExtra("systemname", systems.get(getAdapterPosition()).getName());
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        context.startActivity(myIntent);
                    }
                }
            });
            if (getAdapterPosition() < systems.size())
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final BottomSheetDialog managedialog = new BottomSheetDialog(context);
                        managedialog.setContentView(R.layout.bottomsheet_dialog);
                        LinearLayout Edit = (LinearLayout) managedialog.findViewById(R.id.Edit);
                        LinearLayout Delete = (LinearLayout) managedialog.findViewById(R.id.Delete);
                        LinearLayout Share = (LinearLayout) managedialog.findViewById(R.id.Share);
                        Share.setVisibility(View.VISIBLE);
                        View view = ((Activity) context).findViewById(R.id.layoutProfile);
                        final Snackbar snackbar = Snackbar.make(view, "System " + systems.get(getAdapterPosition()).getName() + " is deleted", Snackbar.LENGTH_SHORT);
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
                                Intent myIntent = new Intent(context, NewRoomActivity.class);
                                myIntent.putExtra("TYPE", "SYSTEM");
                                myIntent.putExtra("NAME", systems.get(getAdapterPosition()).getName());
                                context.startActivity(myIntent);
                            }
                        });
                        Share.setOnClickListener(new View.OnClickListener() {
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


    public SystemAdapter(ArrayList<System> systems,Context context, int type) {
        this.systems = systems;
        this.context = context;
        this.mtype = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_system, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position == systems.size()) {
            holder.txtSystem.setText("New system");
            holder.imgPhoto.setImageResource(R.drawable.plus_circle_outline);
            holder.txtRoomCount.setText("");
        } else {
            System system = systems.get(position);
            holder.txtRoomCount.setText(Integer.toString(system.getRoomcount()) + " room");
            holder.txtSystem.setText(system.getName());
            holder.imgPhoto.setImageResource(system.getPhotoID());
        }
    }

    @Override
    public int getItemCount() {
        return systems.size() + 1;
    }

}