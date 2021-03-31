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
import com.example.meowtify.fragments.SearchFragment;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSearchList extends RecyclerView.Adapter<AdapterSearchList.SearchListHolder> {
    List<GeneralItem> items;
    Context context;
    SearchFragment searchFragment;

    public void setItems(List<GeneralItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public AdapterSearchList(List<GeneralItem> items, Context context, SearchFragment searchFragment) {
        this.items = items;
        this.context = context;
        this.searchFragment = searchFragment;
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

    public class SearchListHolder extends RecyclerView.ViewHolder{
        TextView title, subTitel;
        ImageView image;

        public SearchListHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titel_search);
            subTitel = itemView.findViewById(R.id.subtitel_search);
            image = itemView.findViewById(R.id.image_search);
        }

        public void bindData(GeneralItem generalItem){
            title.setText(generalItem.getName());
            String subtitel = "";
            if(generalItem.getType() != null && generalItem.getType() != Type.artist) {
                if (generalItem.getType() == Type.track) subtitel = "song";
                else subtitel = generalItem.getType().toString();
            }
            if(generalItem.getExtra1() != null && generalItem.getType() != Type.artist) subtitel += " · " + generalItem.getExtra1();
                subTitel.setText(subtitel);
            Picasso.with(context).load(generalItem.getImage()).
                    resize(130, 130).into(image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Todo: navigation to artist, album, playlist perfil or reproductor del itemGeneral

                    if(!searchFragment.checkRecentlySearch(generalItem)){
                        searchFragment.recentlySearchList.add(generalItem);
                        searchFragment.adapterRecently.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
