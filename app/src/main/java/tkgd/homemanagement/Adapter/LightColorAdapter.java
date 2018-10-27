package tkgd.homemanagement.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tkgd.homemanagement.R;

public class LightColorAdapter extends RecyclerView.Adapter<LightColorAdapter.MyViewHolder> {
    private Context context;
    private int mtype;
    private int[] colors = {android.R.color.white, R.color.pastel_1, R.color.pastel_2, R.color.pastel_3, R.color.pastel_4, R.color.light_blue, R.color.light_green};

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private int oldPos = -1;
        private int curPos = -1;

        public MyViewHolder(View itemView) {
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


    public LightColorAdapter(Context context, int type) {
        this.context = context;
        this.mtype = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_light_color, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == colors.length)
                    return;
                if (!holder.imageView.isSelected()) {
                    holder.imageView.getBackground().setColorFilter(context.getColor(colors[position]),
                            PorterDuff.Mode.ADD);
                    holder.imageView.setSelected(true);
                } else {
                    holder.imageView.getBackground().setColorFilter(context.getColor(colors[position]),
                            PorterDuff.Mode.MULTIPLY);
                    holder.imageView.setSelected(false);
                }
            }
        });
        if (position == colors.length) {
            holder.imageView.setImageResource(R.drawable.plus_circle_outline);
            holder.imageView.getBackground().setColorFilter(context.getColor(R.color.dark_grey),
                    PorterDuff.Mode.MULTIPLY);
        } else
            holder.imageView.getBackground().setColorFilter(context.getColor(colors[position]),
                    PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public int getItemCount() {
        return colors.length;
    }

}
