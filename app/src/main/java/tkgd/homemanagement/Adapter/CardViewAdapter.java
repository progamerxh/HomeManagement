package tkgd.homemanagement.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import tkgd.homemanagement.Activity.NewScenarioActivity;
import tkgd.homemanagement.Activity.ScenarioActivity;
import tkgd.homemanagement.Model.ScenarioItem;
import tkgd.homemanagement.R;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.MyViewHolder> {
    private Context context;
    private int mtype;
    private ArrayList<ScenarioItem> scenarioItems;
    private String systemid;
    private int selectedPosition = -1;

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);

        }
    }


    public CardViewAdapter(ArrayList<ScenarioItem> scenarioItems, Context context, int mtype) {
        this.scenarioItems = scenarioItems;
        this.context = context;
        this.mtype = mtype;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        MyViewHolder viewHolder = null;
        if (viewType == -1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.add_item, parent, false);
            viewHolder = new AddItemViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_cardview_outline, parent, false);

            viewHolder = new ScenarioViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == scenarioItems.size())
            return -1;
        else
            return mtype;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (position == scenarioItems.size())
            return;
        ScenarioViewHolder scenarioViewHolder = (ScenarioViewHolder) holder;
        ScenarioItem scenarioItem = scenarioItems.get(position);
        scenarioViewHolder.txtName.setText(scenarioItem.getName());
        if (scenarioItem.isSelected()) {
            scenarioViewHolder.imgicon.setChecked(true);
            scenarioViewHolder.frameLayout.setBackgroundResource(R.drawable.background_gradient);
        } else {
            scenarioViewHolder.imgicon.setChecked(false);
            scenarioViewHolder.frameLayout.setBackgroundResource(R.drawable.item_border);
        }
    }

    @Override
    public int getItemCount() {
        return scenarioItems.size() + 1;
    }

    public class ScenarioViewHolder extends MyViewHolder {
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

                }
            });
            if (mtype == 1) {
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
                                Intent intent = new Intent(context, NewScenarioActivity.class);
                                intent.putExtra("systemid", ScenarioActivity.systemid);
                                intent.putExtra("SCENARIO_NAME", scenarioItems.get(getAdapterPosition()).getName());
                                intent.putExtra("SCENARIO_ID", scenarioItems.get(getAdapterPosition()).getId());
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

            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(context, NewScenarioActivity.class);
                    myIntent.putExtra("systemid", ScenarioActivity.systemid);
                    context.startActivity(myIntent);
                }
            });
        }
    }
}
