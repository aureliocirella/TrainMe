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

public class CustomAdapter extends ArrayAdapter{

    Activity activity;
    int layout;
    ArrayList<User> arrayListUser;

    public CustomAdapter(Activity activity, int layout, ArrayList<User> arrayListUser) {
        super(activity, layout, arrayListUser);
        this.activity = activity;
        this.layout = layout;
        this.arrayListUser = arrayListUser;
    }



    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(layout, null);
        TextView textViewCliente = (TextView) convertView.findViewById(R.id.textViewCliente);

        textViewCliente.setText(arrayListUser.get(position).getInformazioni().get("email"));
        return convertView;
    }
}
