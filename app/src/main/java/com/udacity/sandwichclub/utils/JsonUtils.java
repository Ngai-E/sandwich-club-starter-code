package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            sandwich = new Sandwich();
            JSONObject jsonObject = new JSONObject(json);
            if(jsonObject.has("name")) {
                JSONObject name = jsonObject.getJSONObject("name");

                sandwich.setMainName(name.has("mainName") ?  name.getString("mainName") : "");

                List<String> lisOfOthernames = new ArrayList<>();

                JSONArray jsonArray = name.getJSONArray("alsoKnownAs");

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        lisOfOthernames.add(jsonArray.getString(i));
                    }
                    sandwich.setAlsoKnownAs(lisOfOthernames);
                }
            }
            if (jsonObject.has("ingredients")) {
                List<String> lisOfIngredients = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("ingredients");

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        lisOfIngredients.add(jsonArray.getString(i));
                    }
                    sandwich.setIngredients( lisOfIngredients);
                }
            }
            if (jsonObject.has("description")) {
                sandwich.setDescription(jsonObject.getString("description"));
            }
            if (jsonObject.has("image")) {
                sandwich.setImage(jsonObject.getString("image"));
            }
            if (jsonObject.has("placeOfOrigin")) {
                sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            }
        } catch (JSONException ex) {
            Log.e("Error ******", ex.getMessage());
            ex.printStackTrace();
        }
        return sandwich;
    }
}
