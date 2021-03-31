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
import com.example.meowtify.models.GeneralItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterLibraryListAdd2 extends RecyclerView.Adapter<AdapterLibraryListAdd2.LibraryListAdd2Holder> {
    List<GeneralItem> itmes;
    Context context;

    public AdapterLibraryListAdd2(List<GeneralItem> itmes, Context context) {
        this.itmes = itmes;
        this.context = context;
    }

    @NonNull
    @Override
    public LibraryListAdd2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yourlibrary_recomended2, parent, false);
        return new LibraryListAdd2Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryListAdd2Holder holder, int position) {
        holder.bindData(itmes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return itmes.size();
    }

    public class LibraryListAdd2Holder extends RecyclerView.ViewHolder{
        TextView nameAlbum, nameArtist;
        ImageView image;
        ImageButton followButton;

        public LibraryListAdd2Holder(@NonNull View itemView) {
            super(itemView);

            nameAlbum = itemView.findViewById(R.id.name_album);
            nameArtist = itemView.findViewById(R.id.name_autor);
            image = itemView.findViewById(R.id.image_library);
            followButton = itemView.findViewById(R.id.follow_album);
        }

        public void bindData(GeneralItem generalItem, int position){
            nameAlbum.setText(generalItem.getName());
            nameArtist.setText(generalItem.getExtra1());
            Picasso.with(context).load(generalItem.getImage()).
                    resize(220, 220).into(image);
            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Todo: metodo para añadir albunes a favoritos

                    //Todo: metode per eliminar de la lista de recomenats
                    //si nomes s'ha de eliminar del recycler no de la llista de la api
                    itmes.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
