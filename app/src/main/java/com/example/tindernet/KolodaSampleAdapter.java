package com.example.tindernet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

public class KolodaSampleAdapter extends BaseAdapter {

    private final Context context;
    private final List<Integer> dataList = new ArrayList<>();

    public KolodaSampleAdapter(Context context, List<Integer> data) {
        this.context = context;
        if (data != null) {
            this.dataList.addAll(data);
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Integer getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<Integer> data) {
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataViewHolder holder;
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_koloda, parent, false);
            holder = new DataViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (DataViewHolder) view.getTag();
        }

        holder.bindData(context, getItem(position));

        return view;
    }

    /**
     * Static view items holder
     */
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;

        public DataViewHolder(View view) {
            super(view);
            picture = view.findViewById(R.id.kolodaImage); // Assuming kolodaImage is the ID of your ImageView
        }

        public void bindData(Context context, Integer data) {
            RequestOptions transforms = new RequestOptions()
                    .transforms(new CenterCrop(), new RoundedCorners(20));
            Glide.with(context)
                    .load(data)
                    .apply(transforms)
                    .into(picture);
        }
    }
}
