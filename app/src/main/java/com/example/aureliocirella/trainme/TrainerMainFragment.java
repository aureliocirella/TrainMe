package com.example.aureliocirella.trainme;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrainerMainFragment extends Fragment {


    public TrainerMainFragment() {
        // Required empty public constructor
    }

    private SwipeMenuListView listViewClienti;

    private Button buttonSalva;
    private Button buttonLogout;

    private EditText editTextCliente;

    private ArrayList<User> clienti = new ArrayList<>();

    private CustomAdapter customAdapter;

    private String trainerMail;

    private Database db;

    private Context context;


    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // getActivity().getActionBar().setTitle(R.string.lista_clienti);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trainer_main, container, false);
        final Database db = new Database();
        inizializza(view);

        trainerMail = getArguments().getString("trainerArg");


        customAdapter = new CustomAdapter(getActivity(), R.layout.item_layout,clienti);
        listViewClienti.setAdapter(customAdapter);
        clienti.clear();
        db.getClientiByTrainer(trainerMail, new Database.AggiornaClientiListener() {
            @Override
            public void getClienti(User cliente) {
                clienti.add(cliente);
                customAdapter.notifyDataSetChanged();

            }
        });



        listViewClienti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                String name = clienti.get(position).getInformazioni().get("userId");
                bundle.putString("clientArg", name);
                Navigation.findNavController(getView()).navigate(R.id.toUserViewByTrainer,bundle);
            }
        });
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {


                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        context);
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
        listViewClienti.setMenuCreator(creator);
        listViewClienti.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:


                        final String uIdlDaEliminare = clienti.get(position).getInformazioni().get("userId");
                        db.removeTrainerFromClient(uIdlDaEliminare);
                        clienti.remove(position);
                        customAdapter = new CustomAdapter(getActivity(), R.layout.item_layout,clienti);
                        listViewClienti.setAdapter(customAdapter);


                        break;
                }

                return false;
            }
        });



        TextView textViewtrainerWelcome = view.findViewById(R.id.textViewTrainerWelcome);
        String trainer = getArguments().getString("trainerArg");
        String benvenuto = trainer+", di seguito trovi la lista dei tuoi clienti.";
        textViewtrainerWelcome.setText(benvenuto);

        buttonSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String clienteEmail = editTextCliente.getText().toString();
                editTextCliente.setText("");


                //Modifico il campo trainer dell'utente appena aggiunto.
                db.addTrainerToClient(trainerMail, clienteEmail, new Database.addNewClientListener() {
                    @Override
                    public void getClient(User client) {
                        clienti.add(client);
                        customAdapter.notifyDataSetChanged();
                    }
                });

            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(v).navigate(R.id.toMain2);
            }

        });

        return view;
    }


    public void inizializza(View view)
    {
        listViewClienti = (SwipeMenuListView) view.findViewById(R.id.listViewPasti);
        //buttonElimina = (Button) view.findViewById(R.id.btn_Rimuovi);
        buttonSalva = (Button) view.findViewById(R.id.btn_salva);
        editTextCliente = (EditText) view.findViewById(R.id.editTextPasto);
        buttonLogout = view.findViewById(R.id.buttonLogout);



    }
}
