package com.example.meowtify.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meowtify.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePlaylistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText newName;
    Button cancel, create;
    int playlistCount;

    public CreatePlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreatePlaylistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatePlaylistFragment newInstance(String param1, String param2) {
        CreatePlaylistFragment fragment = new CreatePlaylistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_playlist, container, false);

        newName = v.findViewById(R.id.new_name);
        cancel = v.findViewById(R.id.cancel_button);
        create = v.findViewById(R.id.create_button);

        Bundle b = getArguments();
        if(b != null){
            playlistCount = b.getInt("coutList");
        }

        newName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });

        newName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null)create.setText("CREATE");
                else create.setText("SKIP");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new YourLibraryFragment()).commit();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name;
                if(newName.getText() == null) name = "My playlist #"+playlistCount; //el 3 tindria que ser el numero de playlist ++1
                else name = newName.getText().toString();

                // TODO: metode per afegir a una playlist amb nom == name

                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new YourLibraryFragment()).commit();
            }
        });

        return v;
    }
}