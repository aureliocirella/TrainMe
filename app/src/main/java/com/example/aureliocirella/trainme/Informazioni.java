package com.example.aureliocirella.trainme;

import java.util.HashMap;

public class Informazioni {

    HashMap<String, String> info = new HashMap<String, String>();

    public Informazioni() {
        info.put("userID","");
        info.put("username","");
        info.put("email","");
        info.put("età","");
        info.put("altezza","");
        info.put("genere","");
        info.put("peso","");
        info.put("tipo","");
        info.put("trainer","");

    }

    public HashMap<String, String> getInfo() {
        return info;
    }

    public void setInfo(HashMap<String, String> info) {
        this.info = info;
    }
}
