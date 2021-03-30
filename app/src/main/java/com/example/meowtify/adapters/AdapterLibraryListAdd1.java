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
import com.example.meowtify.models.GeneralItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterLibraryListAdd1 extends RecyclerView.Adapter<AdapterLibraryListAdd1.LibraryListAdd1Holder> {
    List<GeneralItem> itmes;
    Context context;

    public AdapterLibraryListAdd1(List<GeneralItem> itmes, Context context) {
        this.itmes = itmes;
        this.context = context;
    }

    @NonNull
    @Override
    public LibraryListAdd1Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yourlibrary, parent, false);
        return new LibraryListAdd1Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryListAdd1Holder holder, int position) {
        holder.bindData(itmes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return itmes.size();
    }

    public class LibraryListAdd1Holder extends RecyclerView.ViewHolder{
        TextView title, subTitel;
        ImageView image;
        Button followButton;

        public LibraryListAdd1Holder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titel_library);
            subTitel = itemView.findViewById(R.id.subtitel_library);
            image = itemView.findViewById(R.id.image_library);
            followButton = itemView.findViewById(R.id.follow_button);
        }

        public void bindData(GeneralItem generalItem, int position){
            title.setText(generalItem.getTitel());
            subTitel.setText(generalItem.getSubTitel());
            Picasso.with(context).load(generalItem.getImage()).into(image);
            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //metodo para añadir artista

                    //metode per eliminar de la lista de recomenats
                    //si nomes s'ha de eliminar del recycler no de la llista de la api
                    itmes.remove(position);
                }
            });
        }
    }
}
