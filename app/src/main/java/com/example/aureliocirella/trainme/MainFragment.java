package com.example.aureliocirella.trainme;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {




    private EditText editTextUsername;
    private EditText editTextPassword;

    private String email;
    private String password;
    static private String success = "attendi";

    private Button buttonAccedi;
    private Button buttonSigin;

    private View view;

    private Database db;

    public static void setSuccess(String success) {
        MainFragment.success = success;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);

        inizializza(view);

        buttonSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.toRegistration);
            }
        });

        buttonAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();

                //Gestisco l'accesso al database da parte degli utenti dell'app.

                db = new Database();
                db.login(email, password, new Database.LoginListener() {
                    @Override
                    public void esitoLogin(Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //Navigazione condizionale in base a che tipo è l'utente.
                            db.getTipoByEmail(email, new Database.ListenerGetTipo() {
                                @Override
                                public void getTipo(String tipo) {
                                    if(tipo.equals("cliente"))
                                    {

                                        db.getUserIdByEmail(email, new Database.ListenerUserId() {
                                            @Override
                                            public void getUserId(String uId) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("utenteArg", email);
                                                bundle.putString("useridArg", uId);
                                                Navigation.findNavController(view).navigate(R.id.toUtenteMain, bundle);

                                            }
                                        });

                                    }
                                    else if(tipo.equals("trainer"))
                                    {
                                        db.getUserIdByEmail(email, new Database.ListenerUserId() {
                                            @Override
                                            public void getUserId(String uId) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("trainerArg", email);
                                                bundle.putString("trainerIdArg",uId);
                                                Navigation.findNavController(view).navigate(R.id.toTrainerMain, bundle);

                                            }
                                        });

                                    }

                                }


                            });

                        }
                        else
                        {
                            Toast.makeText(view.getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        editTextPassword.setText("");
                        editTextUsername.setText("");
                        }

                    });

            }
        });





        return view;
    }

    public void inizializza (View view)
    {
        editTextUsername = (EditText) view.findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)view.findViewById(R.id.editTextPassword);

        buttonAccedi = (Button) view.findViewById(R.id.buttonAccedi);
        buttonSigin = (Button)view.findViewById(R.id.buttonRegistrati);

    }

}
