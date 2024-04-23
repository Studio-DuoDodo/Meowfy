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
import com.example.meowtify.fragments.SearchFragment;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSearchList extends RecyclerView.Adapter<AdapterSearchList.SearchListHolder> {
    List<GeneralItem> items;
    Context context;
    SearchFragment searchFragment;

    public AdapterSearchList(List<GeneralItem> items, Context context, SearchFragment searchFragment) {
        this.items = items;
        this.context = context;
        this.searchFragment = searchFragment;
    }

    public void setItems(List<GeneralItem> items) {
        this.items = items;
        notifyDataSetChanged();
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
            String subtitle = "";
            if (generalItem.getType() != null) {
                if (generalItem.getType() == Type.track) subtitle = "song";
                else subtitle = generalItem.getType().toString();
            }
            if (generalItem.getExtra1() != null && generalItem.getType() != Type.artist)
                subtitle += " · " + generalItem.getExtra1();
            subTitle.setText(subtitle);
            Picasso.with(context).load(generalItem.getImage()).
                    resize(130, 130).into(image);

            itemView.setOnClickListener(view -> {
                if (generalItem.getType() == Type.track) generalItem.setExtra2("track");

                Utilities.navigationToAAP(generalItem, context);

                if (!searchFragment.checkRecentlySearch(generalItem)) {
                    searchFragment.recentlySearchList.add(generalItem);
                    searchFragment.adapterRecently.notifyDataSetChanged();
                }
            });
        }
    }
}
