package com.example.aureliocirella.trainme;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserViewByTrainerFragment extends Fragment {


    public UserViewByTrainerFragment() {
        // Required empty public constructor
    }
    private Database db;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_view_by_trainer, container, false);
        final String clienteId = getArguments().getString("clientArg");
        db = new Database();
        db.getNameById(clienteId, new Database.NameListener() {
            @Override
            public void getName(String name) {
                TextView tv = view.findViewById(R.id.textViewDettaglio);
                tv.setText(name);
            }
        });

        Button buttonProfilo = (Button) view.findViewById(R.id.btnProfilo);
        buttonProfilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("userArg", clienteId);
                Navigation.findNavController(v).navigate(R.id.toProfilo2, bundle);
            }
        });

        Button buttonDieta = view.findViewById(R.id.buttonDieta2);
        buttonDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("userArg", clienteId);
                Navigation.findNavController(v).navigate(R.id.toTrainerDieta, bundle);

            }
        });

        Button buttonScheda = view.findViewById(R.id.buttonScheda2);
        buttonScheda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("userArg", clienteId);
                Navigation.findNavController(v).navigate(R.id.toTrainerScheda, bundle);

            }
        });

        return view;
    }


}
