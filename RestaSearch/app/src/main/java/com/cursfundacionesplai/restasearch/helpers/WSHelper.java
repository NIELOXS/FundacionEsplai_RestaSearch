package com.cursfundacionesplai.restasearch.helpers;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cursfundacionesplai.restasearch.MapsFragment;
import com.cursfundacionesplai.restasearch.models.Keys;
import com.cursfundacionesplai.restasearch.models.Photo;
import com.cursfundacionesplai.restasearch.models.RestaurantList;
import com.cursfundacionesplai.restasearch.interfaces.CustomResponse;
import com.cursfundacionesplai.restasearch.models.RestaurantModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WSHelper {

    private RequestQueue cuaPeticions;
    private JsonObjectRequest jsonObjectRequest;

    private ArrayList<RestaurantList> restaurants;
    private DBHelper dbHelper;
    private Context context;

    private double radius;

    public WSHelper(Context context){
        cuaPeticions = Volley.newRequestQueue(context);
        restaurants = new ArrayList<>();
        this.context = context;
        dbHelper = new DBHelper(context, Keys.DATABASE_NAME,null,Keys.DATABASE_VERSION);

        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe WSHelper");
    }

    public void buscarRestaurants(LatLng actualPossition,
                                  double radius,
                                  int priceLevel,
                                  boolean restaurantOpen,
                                  float rating,
                                  MapsFragment mapsFragment,
                                  String nextPage){
        /*
        Aquí és on es posarà la informació dels filtres i despres es passaran a una funció per
        que ens crei la URL
         */
        String url;
        Map<String, Object> values = new HashMap<>();

        if(radius < this.radius){
            restaurants.clear();
        }

        this.radius = radius;

        if(!nextPage.equals("")){
            values.put("key",Keys.API_KEY);
            values.put("pagetoken",nextPage);
        }
        else{
            values.put("location", actualPossition.latitude+","+actualPossition.longitude);
            values.put("type", "restaurant");
            values.put("keyword", "restaurant,food");
            values.put("radius", radius);
            values.put("key",Keys.API_KEY);
        }
        url = posarFiltre(values, Keys.URL_BASE);

        /*
        Aquesta és la crida que s'encarrega de buscar els restaurantLists i de mostrar-los al mapa
         */
        jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                String nextPageToken = "";

                try {
                    //Mirem que el status sigui OK
                    if(response.getString("status").equals(Keys.STATUS_CODE_OK)){
                        Log.d("RESTASEARCH", "Restaurants rebuts");

                        //Agafem l'array de results i el convertim a una array de restaurantLists i els guardem
                        if(response.has("next_page_token")){
                            nextPageToken = response.getString("next_page_token");
                        }
                        else{
                            nextPageToken = "";
                        }
                        JSONArray result = response.getJSONArray("results");
                        ArrayList<RestaurantList> results = new ArrayList<>(Arrays.asList(gson.fromJson(String.valueOf(result), RestaurantList[].class)));

                        for(RestaurantList rest : results){
                            if(!restaurants.contains(rest)){
                                restaurants.add(rest);
                            }
                        }
                    }
                    //Todo: falta tindre en compte els altres STATUS_CODE
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Netejem els markers que hi hagin i podem els dels restaurantLists
                if(!nextPageToken.equals("")){
                    buscarRestaurants(actualPossition, radius, priceLevel, restaurantOpen, rating, mapsFragment, nextPageToken);
                }
                else{
                    showRestaurants(restaurants, actualPossition, radius, priceLevel, restaurantOpen, rating, mapsFragment);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RESTASEARCH", "Error al buscar els restaurantLists");
            }
        });

        cuaPeticions.add(jsonObjectRequest);
    }

    public void getEstablimentDetails(String placeId, CustomResponse.EstablimentDetail listener) {

        Map<String, Object> values = new HashMap<>();
        values.put("key", Keys.API_KEY);
        values.put("place_id", placeId);
        values.put("language", LanguageHelper.getSavedLanguage(context));

        String url = posarFiltre(values, Keys.URL_DETAILS);

        jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals(Keys.STATUS_CODE_OK)) {

                        Log.d("RESTASEARCH", "onResponse: getting results");
                        Gson gson = new Gson();
                        JSONObject result = response.getJSONObject("result");

                        RestaurantModel restaurant = gson.fromJson(String.valueOf(result),RestaurantModel.class);

                        dbHelper.deleteFirstHistorial(restaurant.getPlace_id());
                        dbHelper.insertHistorial(restaurant);

                        listener.onEstablimentResponse(restaurant);
                    }
                } catch (JSONException e) {
                    Log.d("RESTASEARCH", "onErrorResponse: " + e.getMessage());
                    listener.onEstablimentResponse(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RESTASEARCH", "onErrorResponse: " + error.getMessage());
                listener.onEstablimentResponse(null);
            }
        });

        cuaPeticions.add(jsonObjectRequest);
    }

    public void showRestaurants(LatLng possition, double radius, int priceLevel, boolean restaurantOpen, float rating, MapsFragment mapsFragment){
        showRestaurants(restaurants, possition, radius, priceLevel, restaurantOpen, rating, mapsFragment);
    }

    private void showRestaurants(ArrayList<RestaurantList> restaurantsList,
                                 LatLng actualPossition,
                                 double radius,
                                 int priceLevel,
                                 boolean restaurantOpen,
                                 float rating,
                                 MapsFragment mapsFragment){
        mapsFragment.afegirCercle(actualPossition, radius);
        for(RestaurantList restaurant : restaurantsList){
            LatLng restaurantPossition = new LatLng(
                    restaurant.getGeometry().getLocation().getLat(),
                    restaurant.getGeometry().getLocation().getLng()
            );

            if(restaurantOpen) {
                if(restaurant.getOpening_hours() == null || (restaurant.getOpening_hours() != null && restaurant.getOpening_hours().isOpen_now())) {
                    if (rating != 0) {
                        if(restaurant.getRating() >= rating){
                            if(priceLevel != 0){
                                if(restaurant.getPrice_level() == priceLevel){
                                    mapsFragment.loadPossition(restaurant.getPlace_id(), restaurant.getName(), restaurantPossition);
                                }
                            }
                        }
                    }
                    else if(priceLevel != 0){
                        if(restaurant.getPrice_level() == priceLevel){
                            mapsFragment.loadPossition(restaurant.getPlace_id(), restaurant.getName(), restaurantPossition);
                        }
                    }
                    else{
                        mapsFragment.loadPossition(restaurant.getPlace_id(), restaurant.getName(), restaurantPossition);
                    }
                }
            }
            else if (rating != 0) {
                if(restaurant.getRating() >= rating){
                    if(priceLevel != 0){
                        if(restaurant.getPrice_level() == priceLevel){
                            mapsFragment.loadPossition(restaurant.getPlace_id(), restaurant.getName(), restaurantPossition);
                        }
                    }
                    else{
                        mapsFragment.loadPossition(restaurant.getPlace_id(), restaurant.getName(), restaurantPossition);
                    }
                }
            }
            else if(priceLevel != 0){
                if(restaurant.getPrice_level() == priceLevel){
                    mapsFragment.loadPossition(restaurant.getPlace_id(), restaurant.getName(), restaurantPossition);
                }
            }
            else{
                mapsFragment.loadPossition(restaurant.getPlace_id(), restaurant.getName(), restaurantPossition);
            }
        }
    }

    private String posarFiltre(Map<String, Object> values, String url){
        /*
        Aquesta funció s'encarrega de crear-nos la url. El que fa es per cada entrada que hi hagi
        en el map ens afaga la key i el valor i ens el va afegint
         */
        boolean first = true;
        for(Map.Entry<String, Object> entry : values.entrySet()){

            if(!first){
                url += "&";
            }
            else{
                first = false;
            }

            url += entry.getKey() + "=" + entry.getValue().toString();
        }
        Log.d("NIL", url);
        return url;
    }
}
