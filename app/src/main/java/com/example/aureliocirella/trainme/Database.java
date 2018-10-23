package com.example.aureliocirella.trainme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import androidx.navigation.Navigation;

import static android.support.constraint.Constraints.TAG;

public class Database {

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDb;
    private String userId;



    public Database() {

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();
        //mDb.setPersistenceEnabled(true);
        mRef = FirebaseDatabase.getInstance().getReference("profili");
    }



    public interface LoginListener
    {
        void esitoLogin(Task<AuthResult> task);

    }

    public void login(String email, String password, final LoginListener mLogList)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        mLogList.esitoLogin(task);



                    }
                });

    }

    public interface ListenerGetTipo
    {
        void getTipo(String tipo);
    }

    public void getTipoByEmail(String email, final ListenerGetTipo mListTipo)
    {

        //Ho bisogno di sapere che tipo è l'utente appena loggato.
        final String mail = email;



        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String mTipo = ds.child("informazioni").child("tipo").getValue(String.class);
                    String email2 = ds.child("informazioni").child("email").getValue(String.class);


                    if((email2.equals(mail)))
                    {
                       mListTipo.getTipo(mTipo);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public interface ListenerUserId
    {
        void getUserId(String uId);
    }

    public void getUserIdByEmail(String email, final ListenerUserId mList)
    {
        final String mail = email;
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String email2 = ds.child("informazioni").child("email").getValue(String.class);

                    if((email2.equals(mail)))
                    {
                        userId = ds.child("informazioni").child("userId").getValue(String.class);
                        mList.getUserId(userId);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void writeNewUser(String userId, String name, String email, String tipo) {
        User user = new User(name, email, tipo, userId);

        mRef.child(userId).setValue(user);


    }



    public String generateKey()
    {
        return mRef.push().getKey();
    }

    public interface AutenticazioneListener
    {
        void esitoAutenticazione(Task<AuthResult> task);

    }

    public void createAccount(String email, String password, final AutenticazioneListener mAutListener)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        mAutListener.esitoAutenticazione(task);


                    }
                });


    }

    public interface AggiornaClientiListener
    {
        void getClienti(User cliente);
    }

    public void getClientiByTrainer(final String trainerEmail, final AggiornaClientiListener mAggCliListener)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);
                    String tipo = user.getInformazioni().get("tipo");
                    String trainer = user.getInformazioni().get("trainer");

                    if((tipo.equals("cliente"))&&(trainer.equals(trainerEmail)))
                    {
                        mAggCliListener.getClienti(user);

                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public void removeTrainerFromClient(final String uIdDaEliminare)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                mRef.child(uIdDaEliminare).child("informazioni").child("trainer").setValue("");


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });
    }

    public interface addNewClientListener
    {
        void getClient(User client);
    }

    public void addTrainerToClient(final String trainerMail, final String clientMail, final addNewClientListener mNewClient)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);
                    String email = user.getInformazioni().get("email");


                    if(email.equals(clientMail))
                    {
                        String userId = user.getInformazioni().get("userId");
                        mRef.child(userId).child("informazioni").child("trainer").setValue(trainerMail);
                        mNewClient.getClient(user);


                    }
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });
    }

    public interface NameListener
    {
        void getName(String name);
    }

    public void getNameById(final String clienteId, final NameListener mNListener)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.child(clienteId).getValue(User.class);
                String nome = user.getInformazioni().get("username");
                mNListener.getName(nome);




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public interface PastoListener
    {
        void getPasto(String pasto);
    }
    public void getPastoByUId(final String uid, final PastoListener mPasListener)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Dieta dieta = new Dieta();
                GenericTypeIndicator<HashMap<String,String>> t = new GenericTypeIndicator<HashMap<String,String>>() {};
                dieta.setPasti(dataSnapshot.child(uid).child("pasti").getValue(t));

                for (String s : dieta.getPasti().values()) {
                    mPasListener.getPasto(s);
                }




            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void removePastoById(final String uid, final ArrayList<String> arrayListDieta)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Dieta dieta = new Dieta();
                /*GenericTypeIndicator<HashMap<String, String>> t = new GenericTypeIndicator<HashMap<String, String>>() {
                };
                dieta.setPasti(dataSnapshot.child(uid).child("pasti").getValue(t));*/

                    int i = 0;
                    String key;

                    dieta.setPasti(new HashMap<String, String>());


                    for (String p : arrayListDieta) {


                            key = "pasto" + i;
                            dieta.getPasti().put(key, p);
                            i++;


                    }


                    mRef.child(uid).child("pasti").setValue(dieta.getPasti());





            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });
    }

    public void addPastoToUId(final String uid, final ArrayList<String> arrayListDieta)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Dieta dieta = new Dieta();
                GenericTypeIndicator<HashMap<String,String>> t = new GenericTypeIndicator<HashMap<String,String>>() {};
                dieta.setPasti(dataSnapshot.child(uid).child("pasti").getValue(t));
                int i = 0;

                dieta.setPasti(new HashMap<String, String>());
                for(String p : arrayListDieta)
                {

                    String key = "pasto"+i;

                    dieta.getPasti().put(key, p);
                    i++;

                }

                mRef.child(uid).child("pasti").setValue(dieta.getPasti());




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });
    }

    public interface EserciziListener
    {
        void getEsercizio(String esercizio);
    }
    public void getEserciziByUId(final String uid, final EserciziListener mEsListener)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Scheda scheda = new Scheda();
                GenericTypeIndicator<HashMap<String,String>> t = new GenericTypeIndicator<HashMap<String,String>>() {};
                scheda.setEsercizi(dataSnapshot.child(uid).child("esercizi").getValue(t));

                for (String s : scheda.getEsercizi().values())
                {
                    mEsListener.getEsercizio(s);

                }




            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void removeEserciziByUid(final String uid, final ArrayList<String> arrayListEsercizi)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Scheda scheda = new Scheda();
                /*GenericTypeIndicator<HashMap<String, String>> t = new GenericTypeIndicator<HashMap<String, String>>() {
                };
                scheda.setEsercizi(dataSnapshot.child(uid).child("esercizi").getValue(t));*/

                    int i = 0;
                    String key;


                    scheda.setEsercizi(new HashMap<String, String>());


                    for (String p : arrayListEsercizi) {

                        key = "es" + i;
                        scheda.getEsercizi().put(key, p);
                        i++;

                    }


                    mRef.child(uid).child("esercizi").setValue(scheda.getEsercizi());


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });
    }

    public void addEserciziToUId(final String uid, final ArrayList<String> arrayListEsercizi)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Scheda scheda = new Scheda();
                GenericTypeIndicator<HashMap<String,String>> t = new GenericTypeIndicator<HashMap<String,String>>() {};
                scheda.setEsercizi(dataSnapshot.child(uid).child("esercizi").getValue(t));
                int i = 0;

                if(arrayListEsercizi.get(0).equals(""))
                {
                    arrayListEsercizi.remove(0);
                }


                scheda.setEsercizi(new HashMap<String, String>());
                for(String p : arrayListEsercizi)
                {

                    String key = "es"+i;


                    scheda.getEsercizi().put(key, p);
                    i++;

                }

                mRef.child(uid).child("esercizi").setValue(scheda.getEsercizi());




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });

    }


    public interface UserListener
    {
        void getUser(User user);
    }
    public void getUserById(final String uid, final UserListener mUsListener)
    {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                User user = dataSnapshot.child(uid).getValue(User.class);
                mUsListener.getUser(user);



            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void setInfoToUid(final String userId, final String età, final String genere, final String peso, final String altezza)
    {
        mRef.child(userId).child("informazioni").child("età").setValue(età);
        mRef.child(userId).child("informazioni").child("peso").setValue(peso);
        mRef.child(userId).child("informazioni").child("altezza").setValue(altezza);
        mRef.child(userId).child("informazioni").child("genere").setValue(genere);
    }
}
