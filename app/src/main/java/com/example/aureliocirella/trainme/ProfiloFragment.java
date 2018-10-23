package com.example.aureliocirella.trainme;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfiloFragment extends Fragment {


    public ProfiloFragment() {
        // Required empty public constructor
    }

    private String età;
    private String genere;
    private String peso;
    private String altezza;
    private View view;
    private Database db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profilo, container, false);
        db = new Database();

        final String uid = getArguments().getString("userid1Arg");

        final Spinner spinnerGenere = view.findViewById(R.id.spinnerGenere);
        String[] elementi = new String[]{"Uomo", "Donna"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, elementi);
        spinnerGenere.setAdapter(adapter);

        final EditText editTextPeso = view.findViewById(R.id.editTextPeso);
        final EditText editTextEtà = view.findViewById(R.id.editTextEtà);
        final EditText editTextAltezza = view.findViewById(R.id.editTextAltezza);




        Button buttonInvio = (Button)view.findViewById(R.id.buttonInvio);
        buttonInvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                età = editTextEtà.getText().toString();
                peso = editTextPeso.getText().toString();
                altezza = editTextAltezza.getText().toString();
                genere = spinnerGenere.getSelectedItem().toString();

                db.setInfoToUid(uid, età, genere, peso, altezza);
                Bundle bundle = new Bundle();
                bundle.putString("useridArg", uid);

                Navigation.findNavController(v).navigate(R.id.toUtenteMain3);
            }
        });

        Button buttonLogout = view.findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                Navigation.findNavController(v).navigate(R.id.toMain);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }




}
