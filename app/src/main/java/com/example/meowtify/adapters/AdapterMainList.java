package com.example.meowtify.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.Utilitis;
import com.example.meowtify.activities.MainActivity;
import com.example.meowtify.fragments.AlbumFragment;
import com.example.meowtify.fragments.ArtistFragment;
import com.example.meowtify.fragments.PlaylistFragment;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.squareup.picasso.Picasso;

import java.util.List;

public class    AdapterMainList extends RecyclerView.Adapter<AdapterMainList.MainListHolder> {
    List<GeneralItem> items;
    int width;
    Context context;

    public AdapterMainList(List<GeneralItem> items, Context context, int width) {
        this.items = items;
        this.context = context;
        this.width = width;
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
        holder.bindData(items.get(position));
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

            title = itemView.findViewById(R.id.titol_main);
            subTitel = itemView.findViewById(R.id.subtitol_main);
            image = itemView.findViewById(R.id.image_main);

        }

        public void bindData(GeneralItem generalItem) {
            if(generalItem.getName().length() > 18){
                generalItem.setName(generalItem.getName().substring(0, 17)+"...");
            }
            title.setText(generalItem.getName());

            String subtitel = "";
            if(generalItem.getType() != Type.artist){
                if(generalItem.getType() != null){
                    if(generalItem.getType() == Type.track) subtitel = "song";
                    else subtitel = generalItem.getType().toString();
                    if(generalItem.getExtra1() != null && generalItem.getType() != Type.playlist) subtitel += " · "+ generalItem.getExtra1();
                }
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    RelativeLayout.LayoutParams layoutParams =
                            (RelativeLayout.LayoutParams)title.getLayoutParams();
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    title.setLayoutParams(layoutParams);
            }

            if(subtitel.length() > 18){
                subtitel = subtitel.substring(0, 17)+"...";
            }
            subTitel.setText(subtitel);
            Picasso.with(context).load(generalItem.getImage()).
                    resize(width, width).into(image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(generalItem.getType() == Type.track) generalItem.setExtra2("track");

                    Utilitis.navigationToAAP(generalItem, context);
                }
            });
        }
    }
}
