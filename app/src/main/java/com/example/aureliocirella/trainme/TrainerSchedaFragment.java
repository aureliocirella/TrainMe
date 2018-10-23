package com.example.aureliocirella.trainme;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
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
public class TrainerSchedaFragment extends Fragment {


    private View view;
    private Button btn_salva3;
    private EditText editTextEsercizio;
    private SwipeMenuListView listViewEsercizi;
    private Database db;
    private CustomAdapterDieta customAdapter;
    private ArrayList<String> arrayListEsercizi;

    public TrainerSchedaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trainer_scheda, container, false);
        inizializza(view);

        final String uid = getArguments().getString("userArg");

        db = new Database();

        arrayListEsercizi = new ArrayList<>();
        customAdapter = new CustomAdapterDieta(getActivity(), R.layout.item_layout,arrayListEsercizi);
        listViewEsercizi.setAdapter(customAdapter);
        arrayListEsercizi.clear();

        db.getEserciziByUId(uid, new Database.EserciziListener() {
            @Override
            public void getEsercizio(String esercizio) {

                arrayListEsercizi.add(esercizio);
                customAdapter.notifyDataSetChanged();
            }
        });


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {


                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        view.getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255,
                        255, 255)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listViewEsercizi.setMenuCreator(creator);
        listViewEsercizi.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if(arrayListEsercizi.size()>1) {
                            arrayListEsercizi.remove(position);

                            customAdapter = new CustomAdapterDieta(getActivity(), R.layout.item_layout, arrayListEsercizi);
                            listViewEsercizi.setAdapter(customAdapter);

                            db.removeEserciziByUid(uid, arrayListEsercizi);
                        }
                        else
                        {
                            arrayListEsercizi.clear();
                            arrayListEsercizi.add(" ");
                            customAdapter = new CustomAdapterDieta(getActivity(), R.layout.item_layout, arrayListEsercizi);
                            listViewEsercizi.setAdapter(customAdapter);

                            db.removeEserciziByUid(uid, arrayListEsercizi);

                        }

                        break;

                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        btn_salva3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nuovoEsercizio = editTextEsercizio.getText().toString();
                editTextEsercizio.setText("");
                arrayListEsercizi.add(nuovoEsercizio);
                if(arrayListEsercizi.get(0).equals(" ")||arrayListEsercizi.get(0).equals(""))
                {
                    arrayListEsercizi.remove(0);
                }
                customAdapter = new CustomAdapterDieta(getActivity(), R.layout.item_layout,arrayListEsercizi);

                listViewEsercizi.setAdapter(customAdapter);

                db.addEserciziToUId(uid, arrayListEsercizi);



            }
        });




        return view;
    }
    public void inizializza(View view)
    {
        listViewEsercizi = view.findViewById(R.id.listViewEsercizi);
        editTextEsercizio = view.findViewById(R.id.editTextEsercizio);
        btn_salva3 = view.findViewById(R.id.btn_salva3);

    }
}
