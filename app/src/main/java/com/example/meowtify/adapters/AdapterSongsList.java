package com.example.meowtify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.Utilities;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSongsList extends RecyclerView.Adapter<AdapterSongsList.SearchListHolder> {
    List<GeneralItem> items;
    Context context;
    int width;
    Type listType;
    String idList;

    public AdapterSongsList(List<GeneralItem> items, Context context, int width, Type listType, String idList) {
        this.items = items;
        this.context = context;
        this.width = width;
        this.listType = listType;
        this.idList = idList;
    }

    public void setItems(List<GeneralItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    @NonNull
    @Override
    public SearchListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListHolder holder, int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setListType(Type listType) {
        this.listType = listType;
        notifyDataSetChanged();
    }

    public class SearchListHolder extends RecyclerView.ViewHolder {
        TextView title, subTitle;
        ImageView image;

        public SearchListHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_search);
            subTitle = itemView.findViewById(R.id.subtitle_search);
            image = itemView.findViewById(R.id.image_search);
        }

        public void bindData(GeneralItem generalItem) {
            title.setText(generalItem.getName());
            subTitle.setText(generalItem.getExtra1());
            Picasso.with(context).load(generalItem.getImage()).
                    resize(width, width).into(image);

            itemView.setOnClickListener(view -> {
                if (listType != null) {
                    generalItem.setId(idList);
                    generalItem.setExtra1(String.valueOf(items.indexOf(generalItem)));
                    generalItem.setExtra2(listType.toString());
                } else generalItem.setExtra2(Type.track.toString());

                Utilities.navigationToAAP(generalItem, context);
            });
        }
    }
}
