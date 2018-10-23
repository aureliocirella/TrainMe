package com.example.aureliocirella.trainme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class User {

    HashMap<String, String> informazioni = new HashMap<String, String>();
    HashMap<String, String> pasti = new HashMap<String, String>();
    HashMap<String, String> esercizi = new HashMap<String, String>();

    public User()
    {
        informazioni.put("username", "");
        informazioni.put("email", "");
        informazioni.put("tipo", "");
        informazioni.put("userId", "");
        informazioni.put("genere","");
        informazioni.put("altezza","");
        informazioni.put("peso","");
        informazioni.put("età","");
        informazioni.put("trainer","");

        pasti.put("pasto0"," ");

        esercizi.put("es0"," ");
    }

    public User(String nome, String email, String tipo, String uid)
    {
        informazioni.put("username", nome);
        informazioni.put("email", email);
        informazioni.put("tipo", tipo);
        informazioni.put("userId", uid);
        informazioni.put("genere","");
        informazioni.put("altezza","");
        informazioni.put("peso","");
        informazioni.put("età","");
        informazioni.put("trainer","");

        pasti.put("pasto0","");
        
        esercizi.put("es0","");
    }

    public HashMap<String, String> getInformazioni() {
        return informazioni;
    }

    public void setInformazioni(HashMap<String, String> informazioni) {
        this.informazioni = informazioni;
    }

    public HashMap<String, String> getPasti() {
        return pasti;
    }

    public void setPasti(HashMap<String, String> pasti) {
        this.pasti = pasti;
    }

    public HashMap<String, String> getEsercizi() {
        return esercizi;
    }

    public void setEsercizi(HashMap<String, String> esercizi) {
        this.esercizi = esercizi;
    }
}
