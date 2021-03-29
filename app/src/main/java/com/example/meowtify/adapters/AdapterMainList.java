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
import com.example.meowtify.models.GeneralItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMainList extends RecyclerView.Adapter<AdapterMainList.MainListHolder> {
    List<GeneralItem> items;
    Context context;

    public AdapterMainList(List<GeneralItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setItems(List<GeneralItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_main, parent, false);
        return new MainListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainListHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.subTitel.setText(items.get(position).getSubtitle());
        Picasso.with(context).load(items.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MainListHolder extends RecyclerView.ViewHolder{
        TextView title, subTitel;
        ImageView image;

        public MainListHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titol);
            subTitel = itemView.findViewById(R.id.subtitol);
            image = itemView.findViewById(R.id.image_lista);
        }
    }
}
