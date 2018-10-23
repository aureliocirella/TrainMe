package com.example.aureliocirella.trainme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profilo2Fragment extends Fragment {

    private Database db;
    public Profilo2Fragment() {
        // Required empty public constructor
    }
    private TextView textViewSesso;
    private TextView textViewPeso;
    private TextView textViewAltezza;
    private TextView textViewEtà;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new Database();

        View view = inflater.inflate(R.layout.fragment_profilo2, container, false);

        textViewAltezza = view.findViewById(R.id.textViewAltezza);
        textViewEtà = view.findViewById(R.id.textViewEtà);
        textViewPeso = view.findViewById(R.id.textViewPeso);
        textViewSesso = view.findViewById(R.id.textViewSesso);
        final String uid = getArguments().getString("userArg");

        db.getUserById(uid, new Database.UserListener() {
            @Override
            public void getUser(User user) {
                textViewAltezza.setText(user.getInformazioni().get("altezza"));
                textViewEtà.setText(user.getInformazioni().get("età"));
                textViewSesso.setText(user.getInformazioni().get("genere"));
                textViewPeso.setText(user.getInformazioni().get("peso"));
            }
        });


        return view;
    }

}
