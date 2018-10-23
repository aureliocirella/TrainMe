package com.example.aureliocirella.trainme;

import java.util.ArrayList;
import java.util.HashMap;

public class Dieta {
    HashMap<String, String> pasti = new HashMap<String, String>();

   public Dieta() {
       pasti.put("pasto0","");
   }

    public HashMap<String, String> getPasti() {
        return pasti;
    }

    public void setPasti(HashMap<String, String> pasti) {
        this.pasti = pasti;
    }
}
