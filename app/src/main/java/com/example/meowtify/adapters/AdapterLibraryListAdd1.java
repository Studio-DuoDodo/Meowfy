package com.example.meowtify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.Utilitis;
import com.example.meowtify.models.Artist;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.services.ArtistService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterLibraryListAdd1 extends RecyclerView.Adapter<AdapterLibraryListAdd1.LibraryListAdd1Holder> {
    List<GeneralItem> items;
    Context context;

    public AdapterLibraryListAdd1(List<GeneralItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setItems(List<GeneralItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LibraryListAdd1Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yourlibrary_recomended1, parent, false);
        return new LibraryListAdd1Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryListAdd1Holder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class LibraryListAdd1Holder extends RecyclerView.ViewHolder{
        TextView nameArtist, numSongs;
        ImageView image;
        Button followButton;

        public LibraryListAdd1Holder(@NonNull View itemView) {
            super(itemView);

            nameArtist = itemView.findViewById(R.id.name_artist);
            numSongs = itemView.findViewById(R.id.num_songs);
            image = itemView.findViewById(R.id.image_library);
            followButton = itemView.findViewById(R.id.follow_artist);
        }

        public void bindData(GeneralItem generalItem, int position){
            nameArtist.setText(generalItem.getName());
            String subtitel = generalItem.getExtra1();
            numSongs.setText(subtitel);
            Picasso.with(context).load(generalItem.getImage()).
                resize(220, 220).into(image);;
            followButton.setOnClickListener(view -> {
                ArtistService artistService = new ArtistService(context);
                Artist a = new Artist();
                a.setId(generalItem.getId());
                artistService.followAnArtist(a);
                items.remove(position);
                notifyDataSetChanged();
            });

            itemView.setOnClickListener(view -> Utilitis.navigationToAAP(generalItem, context));
        }
    }
}
