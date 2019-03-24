package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject baseJsonObject = new JSONObject(json);

            JSONObject name = baseJsonObject.getJSONObject("name");

            String mainName = name.getString("mainName");

            JSONArray alsoKnownAs = (JSONArray) name.get("alsoKnownAs");

            JSONArray ingredientsArray = baseJsonObject.getJSONArray("ingredients");

            String placeOfOrigin = baseJsonObject.getString("placeOfOrigin");

            String description = baseJsonObject.getString("description");

            String image = baseJsonObject.getString("image");

            List<String> alsoKnownAsList = new ArrayList<>();
            for (int k = 0; k < alsoKnownAs.length(); k++) {
                alsoKnownAsList.add(alsoKnownAs.getString(k));
            }

            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                String ingredient = ingredientsArray.getString(i);
                ingredientsList.add(ingredient);
            }

            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();

        }

        return null;
    }
}
