package com.example.meowtify.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.meowtify.R;
import com.example.meowtify.services.PlaylistService;

import java.util.Objects;


public class CreatePlaylistFragment extends Fragment {


    EditText newName;
    Button cancel, create;
    int playlistCount;

    public CreatePlaylistFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_playlist, container, false);

        newName = v.findViewById(R.id.new_name);
        cancel = v.findViewById(R.id.cancel_button);
        create = v.findViewById(R.id.create_button);

        Bundle b = getArguments();
        if (b != null) {
            playlistCount = b.getInt("coutList");
        }

        newName.setOnEditorActionListener((textView, i, keyEvent) -> false);

        newName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) create.setText("CREATE");
                else create.setText("SKIP");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cancel.setOnClickListener(view -> Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new YourLibraryFragment()).commit());
        create.setOnClickListener(view -> {
            String name;
            if (newName.getText() == null) name = "My playlist #" + playlistCount;
            else name = newName.getText().toString();

            PlaylistService playlistService = new PlaylistService(getContext());
            playlistService.createAPlaylist(name, "Created with Meowfy", true);
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new YourLibraryFragment()).commit();
        });

        return v;
    }
}