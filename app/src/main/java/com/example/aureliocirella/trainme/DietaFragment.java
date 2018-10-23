package com.example.aureliocirella.trainme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
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
public class DietaFragment extends Fragment {

    private View view;
    private ArrayList<String> arrayListDieta;
    private CustomAdapterDieta customAdapter;
    private Database db;
    private ListView listViewDieta;

    public DietaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dieta, container, false);

        listViewDieta = view.findViewById(R.id.listViewDieta2);

        final String uid = getArguments().getString("useridArg");
        db = new Database();

        arrayListDieta = new ArrayList<>();
        customAdapter = new CustomAdapterDieta(getActivity(), R.layout.item_layout,arrayListDieta);
        listViewDieta.setAdapter(customAdapter);
        arrayListDieta.clear();

        db.getPastoByUId(uid, new Database.PastoListener() {
            @Override
            public void getPasto(String pasto) {

                arrayListDieta.add(pasto);
                customAdapter.notifyDataSetChanged();

            }
        });



        return view;
    }

}
