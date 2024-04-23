package com.example.meowtify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class AdapterSearchRecentlyList extends RecyclerView.Adapter<AdapterSearchRecentlyList.SearchListAdd2Holder> {
    List<GeneralItem> items;
    Context context;

    public AdapterSearchRecentlyList(List<GeneralItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setItems(List<GeneralItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchListAdd2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recently_search, parent, false);
        return new SearchListAdd2Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdd2Holder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class SearchListAdd2Holder extends RecyclerView.ViewHolder {
        TextView title, subtitle;
        ImageView image;
        ImageButton deleteSearch;

        public SearchListAdd2Holder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_search);
            subtitle = itemView.findViewById(R.id.subtitle_search);
            image = itemView.findViewById(R.id.image_search);
            deleteSearch = itemView.findViewById(R.id.delete_search);
        }

        public void bindData(GeneralItem generalItem, int position) {
            title.setText(generalItem.getName());
            String subTitle = "";
            if (generalItem.getType() != null) {
                if (generalItem.getType() == Type.track) subTitle = "song";
                else subTitle = generalItem.getType().toString();
            }
            if (generalItem.getExtra1() != null && generalItem.getType() != Type.artist)
                subTitle += " · " + generalItem.getExtra1();
            subtitle.setText(subTitle);
            Picasso.with(context).load(generalItem.getImage()).
                    resize(130, 130).into(image);
            deleteSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.remove(position);
                    notifyDataSetChanged();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (generalItem.getType() == Type.track) generalItem.setExtra2("track");

                    if (SearchFragment.searched)
                        SearchFragment.recentlySearchList.add(generalItem);
                    Utilities.navigationToAAP(generalItem, context);
                    SearchFragment.searched = false;
                }
            });
        }
    }
}
