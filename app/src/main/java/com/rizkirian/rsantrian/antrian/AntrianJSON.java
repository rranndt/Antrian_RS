package com.rizkirian.rsantrian.antrian;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class AntrianJSON {

    private static List<Antrian> pList;

    public static List<Antrian> parseData(String content) {
        JSONArray p_arry = null;
        Antrian p = null;
        try {
            p_arry = new JSONArray(content);
            pList = new ArrayList<>();

            for (int i = 0; i < p_arry.length(); i++) {

                JSONObject obj = p_arry.getJSONObject(i);
                p = new Antrian();
                p.setNama_pasien(obj.getString("nama_pasien"));
                p.setNomor_antrian(obj.getString("nomor_antrian"));
                p.setDi_layani(obj.getString("di_layani"));
                pList.add(p);
            }
            return pList;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
