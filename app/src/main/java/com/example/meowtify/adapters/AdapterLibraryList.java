package com.example.meowtify.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.Utilitis;
import com.example.meowtify.fragments.CreatePlaylistFragment;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterLibraryList extends RecyclerView.Adapter<AdapterLibraryList.LibraryListHolder> {
    List<GeneralItem> items;
    Context context;

    public AdapterLibraryList(List<GeneralItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setItems(List<GeneralItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LibraryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yourlibrary, parent, false);
        return new LibraryListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryListHolder holder, int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class LibraryListHolder extends RecyclerView.ViewHolder {
        TextView title, subTitel;
        ImageView image;

        public LibraryListHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titel_library);
            subTitel = itemView.findViewById(R.id.subtitel_library);
            image = itemView.findViewById(R.id.image_library);
        }

        public void bindData(GeneralItem generalItem) {
            System.out.println("GeneralItem" + generalItem.toString());
            title.setText(generalItem.getName());
            if (generalItem.getExtra1() != null) {

                String subtitel = "";
                if (generalItem.getType() == Type.playlist) subtitel = "by ";
                subtitel += generalItem.getExtra1();
                subTitel.setText(subtitel);

                Picasso.with(context).load(generalItem.getImage()).
                        resize(220, 220).into(image);
                image.setPadding(0, 0, 0, 0);

                itemView.setOnClickListener(view -> Utilitis.navigationToAAP(generalItem, context));
            } else {
                image.setImageResource(R.drawable.ic_baseline_add_24);
                image.setPadding(80, 80, 80, 80);
                itemView.setOnClickListener(view -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("coutList", getItemCount());
                    CreatePlaylistFragment fragment = new CreatePlaylistFragment();
                    fragment.setArguments(bundle);

                    ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment).commit();
                });
            }
        }
    }
}
