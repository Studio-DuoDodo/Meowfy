package com.example.meowtify.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.models.GeneralItem;

import java.util.List;

public class AdapterMainList extends RecyclerView.Adapter<AdapterMainList.MainListHolder> {
    List<GeneralItem> itmes;

    public AdapterMainList(List<GeneralItem> itmes) {
        this.itmes = itmes;
    }

    @NonNull
    @Override
    public MainListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_main, parent, false);
        return new MainListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainListHolder holder, int position) {
        holder.title.setText(itmes.get(position).getTitel());
        holder.subTitel.setText(itmes.get(position).getSubTitel());
        holder.image.setImageResource(R.drawable.ic_launcher_foreground);
    }

    @Override
    public int getItemCount() {
        return itmes.size();
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
