package tkgd.homemanagement.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tkgd.homemanagement.R;

public class SocketAdapter extends RecyclerView.Adapter<SocketAdapter.MyViewHolder> {
    private Context context;
    private int mtype;
    private String[] sockets = {"Wifi", "TV", "Fan", "Xaomi"};

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtSocketName;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgSocket);
            txtSocketName = (TextView) itemView.findViewById(R.id.txtSocketName);
        }
    }


    public SocketAdapter(Context context, int type) {
        this.context = context;
        this.mtype = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_electricity_socket, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtSocketName.setText(sockets[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageView.setSelected(!holder.imageView.isSelected());
            }
        });

    }

    @Override
    public int getItemCount() {
        return sockets.length;
    }

}
