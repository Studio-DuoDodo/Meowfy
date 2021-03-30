package com.example.meowtify.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.fragments.CreatePlaylistFragment;
import com.example.meowtify.models.GeneralItem;
import com.squareup.picasso.Picasso;
import androidx.fragment.app.*;
import java.util.List;

public class AdapterLibraryList extends RecyclerView.Adapter<AdapterLibraryList.LibraryListHolder> {
    List<GeneralItem> itmes;
    Context context;

    public AdapterLibraryList(List<GeneralItem> itmes, Context context) {
        this.itmes = itmes;
        this.context = context;
    }

    @NonNull
    @Override
    public LibraryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yourlibrary, parent, false);
        return new LibraryListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryListHolder holder, int position) {
        holder.bindData(itmes.get(position));
    }

    @Override
    public int getItemCount() {
        return itmes.size();
    }

    public class LibraryListHolder extends RecyclerView.ViewHolder{
        TextView title, subTitel;
        ImageView image;

        public LibraryListHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titel_library);
            subTitel = itemView.findViewById(R.id.subtitel_library);
            image = itemView.findViewById(R.id.image_library);
        }

        public void bindData(GeneralItem generalItem){
            title.setText(generalItem.getTitel());
            if(generalItem.getSubTitel() != null){
                subTitel.setText(generalItem.getSubTitel());
            }
            Picasso.with(context).load(generalItem.getImage()).into(image);

            if(generalItem.getTitel().equals("Create playlist")){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("coutList", getItemCount());
                        CreatePlaylistFragment fragment =  new CreatePlaylistFragment();
                        fragment.setArguments(bundle);

                        ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, fragment).commit();
                    }
                });
            }
        }
    }
}