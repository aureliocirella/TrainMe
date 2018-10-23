package com.example.aureliocirella.trainme;


import android.content.Context;
import android.os.AsyncTask;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import androidx.navigation.Navigation;

import static android.support.constraint.Constraints.TAG;

public class RegistrationFragment extends Fragment {


    private String email;
    private String password;
    private String username;
    private String userId;

    private CheckBox checkboxUtente2;
    private CheckBox checkboxTrainer2;

    private Button buttonRegistrati;


    private EditText editTextNuovoUsername;
    private EditText editTextNuovaPassword;
    private EditText editTextUsername;

    private View view;

    private Database db;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        db = new Database();

        inizializza(view);

        buttonRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                username = editTextUsername.getText().toString();
                email = editTextNuovoUsername.getText().toString();
                password = editTextNuovaPassword.getText().toString();

                //Gestisco l'aggiunta di un nuovo account al database.


                db.createAccount(email, password, new Database.AutenticazioneListener() {
                    @Override
                    public void esitoAutenticazione(Task<AuthResult> task) {

                        if(task.isSuccessful()) {// Account creato

                            Log.d("RegistrationFragment", "createUserWithEmail:success");
                            Toast.makeText(view.getContext(), "Registrazione completata con succeso.",
                                    Toast.LENGTH_LONG).show();

                            db.login(email, password, new Database.LoginListener(){

                                public void esitoLogin(Task<AuthResult> task)
                                {
                                    if(task.isSuccessful()) {

                                        // Login eseguito
                                        Log.d(TAG, "signInWithEmail:success");
                                        if (checkboxTrainer2.isChecked()) {

                                            userId = db.generateKey();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("trainerArg", email);
                                            bundle.putString("useridArg", userId);

                                            db.writeNewUser(userId, username, email, "trainer");
                                            Navigation.findNavController(v).navigate(R.id.toTrainerMain2, bundle);
                                        } else {
                                            userId = db.generateKey();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("utenteArg", username);
                                            bundle.putString("useridArg", userId);

                                            db.writeNewUser(userId, username, email, "cliente");
                                            Navigation.findNavController(v).navigate(R.id.toUtenteMain2, bundle);
                                        }
                                    }
                                }
                            });

                        }
                        else //Account non creato
                        {
                            Toast.makeText(mContext, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }


                        editTextNuovaPassword.setText("");
                        editTextNuovoUsername.setText("");
                        editTextUsername.setText("");

                    }
                });


            }
        });
                return view;
    }

    public void inizializza(View view)
    {

        checkboxUtente2 = view.findViewById(R.id.checkBoxUtente2);
        checkboxUtente2.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.roboto_slab1));

        checkboxTrainer2 = view.findViewById(R.id.checkBoxTrainer2);
        checkboxTrainer2.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.roboto_slab1));


        checkboxUtente2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxTrainer2.setChecked(false);
            }
        });

        checkboxTrainer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxUtente2.setChecked(false);
            }
        });

        buttonRegistrati =  view.findViewById(R.id.buttonRegistrati);

        editTextNuovoUsername = view.findViewById(R.id.editTextNuovoUser);
        editTextNuovaPassword = view.findViewById(R.id.editTextNuovaPassword);
        editTextUsername = view.findViewById(R.id.editTextUsername);
    }




}
