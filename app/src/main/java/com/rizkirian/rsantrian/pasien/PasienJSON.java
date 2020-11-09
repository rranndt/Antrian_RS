package com.rizkirian.rsantrian.pasien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class PasienJSON {

    private static List<Pasien> pList;

    public static List<Pasien> parseData(String content) {
        JSONArray p_arry = null;
        Pasien p = null;
        try {
            p_arry = new JSONArray(content);
            pList = new ArrayList<>();
            for (int i = 0; i < p_arry.length(); i++) {
                JSONObject obj = p_arry.getJSONObject(i);
                p = new Pasien();
                p.setId_pasien(obj.getString("id_pasien"));
                p.setNama_pasien(obj.getString("nama_pasien"));
                pList.add(p);
            }
            return pList;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
