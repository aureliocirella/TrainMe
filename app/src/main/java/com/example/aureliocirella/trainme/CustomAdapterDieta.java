package com.example.aureliocirella.trainme;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapterDieta extends ArrayAdapter{

    Activity activity;
    int layout;
    ArrayList<String> arrayListDieta;

    public CustomAdapterDieta(Activity activity, int layout, ArrayList<String> arrayListDieta) {
        super(activity, layout, arrayListDieta);
        this.activity = activity;
        this.layout = layout;
        this.arrayListDieta = arrayListDieta;
    }



    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(layout, null);
        TextView textViewPasto = (TextView) convertView.findViewById(R.id.textViewCliente);

        textViewPasto.setText(arrayListDieta.get(position));
        return convertView;
    }
}