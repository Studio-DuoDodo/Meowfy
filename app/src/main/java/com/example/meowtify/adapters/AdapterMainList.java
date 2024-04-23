package com.example.meowtify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.Utilities;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMainList extends RecyclerView.Adapter<AdapterMainList.MainListHolder> {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_main, parent, false);
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

    public class MainListHolder extends RecyclerView.ViewHolder {
        TextView title, subTitle;
        ImageView image;

        public MainListHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_main);
            subTitle = itemView.findViewById(R.id.subtitle_main);
            image = itemView.findViewById(R.id.image_main);

        }

        public void bindData(GeneralItem generalItem) {
            if (generalItem.getName().length() > 18) {
                generalItem.setName(generalItem.getName().substring(0, 17) + "...");
            }
            title.setText(generalItem.getName());

            String subtitle = "";
            if (generalItem.getType() != Type.artist) {
                if (generalItem.getType() != null) {
                    if (generalItem.getType() == Type.track) subtitle = "song";
                    else subtitle = generalItem.getType().toString();
                    if (generalItem.getExtra1() != null && generalItem.getType() != Type.playlist)
                        subtitle += " · " + generalItem.getExtra1();
                }
            } else {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) title.getLayoutParams();
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                title.setLayoutParams(layoutParams);
            }

            if (subtitle.length() > 18) {
                subtitle = subtitle.substring(0, 17) + "...";
            }
            subTitle.setText(subtitle);
            Picasso.with(context).load(generalItem.getImage()).
                    resize(width, width).into(image);

            itemView.setOnClickListener(view -> {
                if (generalItem.getType() == Type.track)
                    generalItem.setExtra2(generalItem.getType().toString());

                Utilities.navigationToAAP(generalItem, context);
            });
        }
    }
}
