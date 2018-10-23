package com.example.aureliocirella.trainme;

import java.util.HashMap;

public class Scheda {

    HashMap<String, String> esercizi = new HashMap<String, String>();

    public Scheda() {
        esercizi.put("es0","");
    }

    public HashMap<String, String> getEsercizi() {
        return esercizi;
    }

    public void setEsercizi(HashMap<String, String> esercizi) {
        this.esercizi = esercizi;
    }
}
