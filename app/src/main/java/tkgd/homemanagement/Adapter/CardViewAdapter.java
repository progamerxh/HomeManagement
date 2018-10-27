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
import tkgd.homemanagement.Model.ScenarioItem;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.MyDefinition;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.MyViewHolder> {
    private Context context;
    private int mtype;
    private ArrayList<ScenarioItem> scenarioItems;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ToggleButton imgicon;
        private FrameLayout frameLayout;
        private TextView txtName;
        private int selectedPosition = -1;

        public MyViewHolder(View itemView) {
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
                                Intent intent = new Intent(context, NewScenarioActivity.class);
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
                    context.startActivity(myIntent);
                }
            });
        }
    }

    public CardViewAdapter(Context context, int mtype) {
        scenarioItems = new ArrayList<>();
        scenarioItems.add(new ScenarioItem("I'm at home", false));
        scenarioItems.add(new ScenarioItem("Go to work", false));
        scenarioItems.add(new ScenarioItem("I'm outside", false));
        this.context = context;
        this.mtype = mtype;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mtype == -1) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.add_item, parent, false);
            return new AddItemViewHolder(itemView);
        }
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_outline, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position == scenarioItems.size())
            return;
        ScenarioItem scenarioItem = scenarioItems.get(position);
        holder.txtName.setText(scenarioItem.getName());
        if (scenarioItem.isSelected()) {
            holder.imgicon.setChecked(true);
            holder.frameLayout.setBackgroundResource(R.drawable.background_gradient);
        } else {
            holder.imgicon.setChecked(false);
            holder.frameLayout.setBackgroundResource(R.drawable.item_border);
        }
    }

    @Override
    public int getItemCount() {
        return scenarioItems.size() + 1;
    }

}
