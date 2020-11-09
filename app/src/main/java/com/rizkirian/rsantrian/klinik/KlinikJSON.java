package com.rizkirian.rsantrian.klinik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class KlinikJSON {

    private static List<Klinik> pList;

    public static List<Klinik> parseData(String content) {
        JSONArray p_arry = null;
        Klinik p = null;
        try {
            p_arry = new JSONArray(content);
            pList = new ArrayList<>();
            for (int i = 0; i < p_arry.length(); i++) {
                JSONObject obj = p_arry.getJSONObject(i);
                p = new Klinik();
                p.setId_klinik(obj.getString("id_klinik"));
                p.setNama_klinik(obj.getString("nama_klinik"));
                pList.add(p);
            }
            return pList;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
