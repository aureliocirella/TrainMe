package com.example.aureliocirella.trainme;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import static java.lang.reflect.Modifier.PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class UtenteMainFragment extends Fragment {


    /*private Context context;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
    */
    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utente_main, container, false);
        Button dieta = view.findViewById(R.id.buttonDieta);
        Button scheda = view.findViewById(R.id.buttonScheda);
        String utente = getArguments().getString("utenteArg");

        TextView textView2 = (TextView) view.findViewById(R.id.textView2);

        String benvenuto = "Benvenuto in TrainME.";
        userId = getArguments().getString("useridArg");

        textView2.setText(benvenuto);


        Button buttonProgressi =(Button) view.findViewById(R.id.buttonProgressi);
        buttonProgressi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("userid1Arg", userId);
                Navigation.findNavController(v).navigate(R.id.toProfilo, bundle);
            }
        });
        Button buttonDieta = view.findViewById(R.id.buttonDieta);
        buttonDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("useridArg", userId);
                Navigation.findNavController(v).navigate(R.id.toDietaUtente, bundle);

            }
        });

        Button buttonScheda = view.findViewById(R.id.buttonScheda);
        buttonScheda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("useridArg", userId);
                Navigation.findNavController(v).navigate(R.id.toSchedaUtente, bundle);

            }
        });



        return view;
    }

}
