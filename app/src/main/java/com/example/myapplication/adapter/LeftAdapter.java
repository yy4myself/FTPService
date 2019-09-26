package com.example.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.ItemDataBean;

import java.util.List;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHolder> {

    private Context context;
    private List<ItemDataBean> list;
    private ItemClickListener listener;

    public LeftAdapter(Context context, List<ItemDataBean> list, ItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_left, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final ItemDataBean itemDataBean = list.get(position);
        viewHolder.imageViewIcon.setBackgroundResource(itemDataBean.getItemImageId());
        viewHolder.textViewName.setText(itemDataBean.getItemName());
        if (listener != null) {
            viewHolder.relativeLayoutLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClickListener(position, itemDataBean.getActivityClassName());
                }
            });
            viewHolder.relativeLayoutLeft.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.OnItemLongClickListener(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayoutLeft;
        ImageView imageViewIcon;
        TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayoutLeft = itemView.findViewById(R.id.relativeLayout_left);
            imageViewIcon = itemView.findViewById(R.id.imageView_icon);
            textViewName = itemView.findViewById(R.id.textView_name);
        }
    }

    public interface ItemClickListener {
        void OnItemClickListener(int position, String activityName);

        void OnItemLongClickListener(int position);
    }
}
