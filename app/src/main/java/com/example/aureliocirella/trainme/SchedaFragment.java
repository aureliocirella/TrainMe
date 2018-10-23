package com.example.aureliocirella.trainme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchedaFragment extends Fragment {

    private View view;
    private ArrayList<String> arrayListScheda;
    private CustomAdapterDieta customAdapter;
    private Database db;
    private ListView listViewScheda;
    public SchedaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scheda, container, false);

        listViewScheda = view.findViewById(R.id.listViewScheda2);

        final String uid = getArguments().getString("useridArg");
        db = new Database();
        arrayListScheda = new ArrayList<>();
        customAdapter = new CustomAdapterDieta(getActivity(), R.layout.item_layout,arrayListScheda);
        listViewScheda.setAdapter(customAdapter);
        arrayListScheda.clear();
        db.getEserciziByUId(uid, new Database.EserciziListener() {
            @Override
            public void getEsercizio(String esercizio) {
                arrayListScheda.add(esercizio);
                customAdapter.notifyDataSetChanged();

            }
        });




        return view;
    }

}
