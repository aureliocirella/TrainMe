package com.example.aureliocirella.trainme;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
public class TrainerDietaFragment extends Fragment {

    private SwipeMenuListView listViewPasti;
    private ArrayList<String> arrayListDieta;
    private CustomAdapterDieta customAdapter;
    private Database db;
    private View view;
    private Button btn_salva2;
    private EditText editTextPasto;
    public TrainerDietaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_trainer_dieta, container, false);
        inizializza(view);

        final String uid = getArguments().getString("userArg");

        db = new Database();

        arrayListDieta = new ArrayList<>();
        customAdapter = new CustomAdapterDieta(getActivity(), R.layout.item_layout,arrayListDieta);
        listViewPasti.setAdapter(customAdapter);
        arrayListDieta.clear();

        db.getPastoByUId(uid, new Database.PastoListener() {
            @Override
            public void getPasto(String pasto) {
                arrayListDieta.add(pasto);
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
        listViewPasti.setMenuCreator(creator);
        listViewPasti.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        if(arrayListDieta.size()>1) {
                            arrayListDieta.remove(position);

                            customAdapter = new CustomAdapterDieta(getActivity(), R.layout.item_layout, arrayListDieta);
                            listViewPasti.setAdapter(customAdapter);

                            db.removePastoById(uid, arrayListDieta);
                        }



                        break;

                }

                return false;
            }
        });
        btn_salva2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nuovoPasto = editTextPasto.getText().toString();
                editTextPasto.setText("");

                arrayListDieta.add(nuovoPasto);
                if(arrayListDieta.get(0).equals(" ")||arrayListDieta.get(0).equals(""))
                {
                    arrayListDieta.remove(0);
                }
                customAdapter = new CustomAdapterDieta(getActivity(), R.layout.item_layout,arrayListDieta);

                listViewPasti.setAdapter(customAdapter);
                db.addPastoToUId(uid, arrayListDieta);




            }
        });

        return view;
    }

    public void inizializza(View view)
    {
        listViewPasti = view.findViewById(R.id.listViewPasti);
        editTextPasto = view.findViewById(R.id.editTextPasto);
        btn_salva2 = view.findViewById(R.id.btn_salva2);

    }

}
