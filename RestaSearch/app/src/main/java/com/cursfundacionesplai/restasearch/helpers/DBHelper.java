package com.cursfundacionesplai.restasearch.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cursfundacionesplai.restasearch.models.Keys;
import com.cursfundacionesplai.restasearch.models.Photo;
import com.cursfundacionesplai.restasearch.models.RestaurantList;
import com.cursfundacionesplai.restasearch.models.RestaurantModel;
import com.cursfundacionesplai.restasearch.models.Photo;
import com.cursfundacionesplai.restasearch.models.RestaurantModel;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase database;
    Cursor cursor;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe DBHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creacio taula Restaurant
        db.execSQL("CREATE TABLE historial(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "places_id TEXT," +
                "rating REAL," +
                "address TEXT," +
                "price_level INTEGER," +
                "name TEXT," +
                "user_ratings_total INTEGER)");
        Log.d("RESTASEARCH", "S'ha creat la taula historial");

        db.execSQL("CREATE TABLE favourites(" +
                "places_id TEXT PRIMARY KEY," +
                "rating REAL," +
                "address TEXT," +
                "price_level INTEGER," +
                "name TEXT," +
                "user_ratings_total INTEGER)");
        Log.d("RESTASEARCH", "S'ha creat la taula preferits");

        db.execSQL("CREATE TABLE photos(" +
                "places_id TEXT," +
                "height INTEGER," +
                "html_attributions TEXT," +
                "photo_reference TEXT," +
                "width INTEGER)");
        Log.d("RESTASEARCH", "S'ha creat la taula photos");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertHistorial(RestaurantModel restaurant){

        database = this.getReadableDatabase();

        cursor = database.rawQuery("" +
                "SELECT * " +
                "FROM historial " +
                "WHERE places_id = '" + restaurant.getPlace_id() + "'", null);

        database = this.getWritableDatabase();

        try {
            if(!cursor.moveToFirst()){

                database.execSQL(
                        ("INSERT INTO historial(" +
                                "places_id, rating, " +
                                "address, price_level, " +
                                "name, user_ratings_total) " +
                                "VALUES(" +
                                ":places_id, :rating," +
                                ":address, :price_level," +
                                ":name, :user_ratings_total)"),
                        new Object[]{
                                restaurant.getPlace_id(),
                                restaurant.getRating(),
                                restaurant.getFormatted_address(),
                                restaurant.getPrice_level(),
                                restaurant.getName(),
                                restaurant.getUser_ratings_total()
                        });

                for (Photo photo : restaurant.getPhotos()){
                    database.execSQL("" +
                                    "INSERT INTO photos(" +
                                    "places_id, height," +
                                    "html_attributions," +
                                    "photo_reference," +
                                    "width)" +
                                    "VALUES(" +
                                    ":places_id, :height," +
                                    ":html_attributions," +
                                    ":photo_reference," +
                                    ":width)",
                            new Object[]{
                                    restaurant.getPlace_id(),
                                    photo.getHeight(),
                                    photo.getHtml_attributions().get(0),
                                    photo.getPhoto_reference(),
                                    photo.getWidth()
                            });
                }
            }
            cursor.close();
            database.close();
        }
        catch (Exception e){
            Log.d("RESTASEARCH", "Error al llegir les dades del select. Error: " + e.getMessage());
        }

        Log.d("RESTASEARCH", "s'ha fet un insert a la taula historial");
    }

    public void insertFavourites(String places_id){

        database = this.getReadableDatabase();

        cursor = database.rawQuery("" +
                "SELECT * " +
                "FROM favourites " +
                "WHERE places_id = '" + places_id + "'", null);

        try {
            if(!cursor.moveToFirst()){

                cursor = database.rawQuery("" +
                        "SELECT rating, address, price_level, name, user_ratings_total " +
                        "FROM historial " +
                        "WHERE places_id = '" + places_id + "'", null);

                if(cursor.moveToFirst()){
                    double rating = 0;
                    String address = "";
                    int price_level = 0;
                    String name = "";
                    int user_ratings_total = 0;

                    do{
                        rating = cursor.getDouble(0);
                        address = cursor.getString(1);
                        price_level = cursor.getInt(2);
                        name = cursor.getString(3);
                        user_ratings_total = cursor.getInt(4);
                        Log.d("RESTASEARCH","Rating: " + rating + ", Address " + address + ", Price Level: " + price_level  + ", Name " + name + ", User ratings total " + user_ratings_total);
                    }
                    while (cursor.moveToNext());

                    database.execSQL(
                            "INSERT INTO favourites(" +
                                    "places_id, rating, " +
                                    "address, price_level, " +
                                    "name, user_ratings_total) " +
                                    "VALUES(" +
                                    ":places_id, :rating," +
                                    ":address, :price_level," +
                                    ":name, :user_ratings_total)",
                            new Object[]{
                                    places_id,
                                    rating,
                                    address,
                                    price_level,
                                    name,
                                    user_ratings_total
                            });

                    database.close();
                }
            }
            else{
                Log.d("RESTASEARCH", "No hi han dades a la base de dades");
            }
        }
        catch (Exception e){
            Log.d("RESTASEARCH", "Error al llegir les dades del select. Error: " + e.getMessage());
        }

        Log.d("RESTASEARCH", "s'ha fet un insert a la taula favourites");
    }

    public void deleteFavourites(String places_id){
        database = this.getWritableDatabase();

        database.execSQL("" +
                "DELETE FROM favourites " +
                "WHERE places_id = '" + places_id + "'");

        database.close();

        Log.d("RESTASEARCH", "s'ha fet un delete a la taula favourites");
    }

    public void deleteFirstHistorial(String place_id){
        boolean isRestaurantFind = false;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("SELECT places_id FROM historial WHERE places_id = '" + place_id + "'", null);

        String placesIdHistorial = "";
        try{
            if(cursor.moveToFirst()){
                placesIdHistorial = cursor.getString(0);
                isRestaurantFind = true;
            }
        }
        catch (Exception e){
            Log.d("RESTASEARCH", e.getMessage());
        }

        if((countHistorial() == 15) || isRestaurantFind){
            if(placesIdHistorial.equals("") && !isRestaurantFind){
                cursor = database.rawQuery("SELECT places_id FROM historial ORDER BY id", null);

                try{
                    if(cursor.moveToFirst()){
                        placesIdHistorial = cursor.getString(0);
                        Log.d("NIL", "ID:" + placesIdHistorial);
                    }
                }
                catch(Exception e){
                    Log.d("RESTASEARCH", e.getMessage());
                }
            }

            if(!placesIdHistorial.equals("")){
                cursor = database.rawQuery("SELECT places_id FROM favourites WHERE places_id = '"+placesIdHistorial+"'", null);
                String placesIdFavourites = "";
                try{
                    if(cursor.moveToFirst()){
                        placesIdFavourites = cursor.getString(0);
                    }
                }
                catch(Exception e){
                    Log.d("RESTASEARCH", e.getMessage());
                }

                if(!placesIdHistorial.equals(placesIdFavourites)){
                    database = this.getWritableDatabase();
                    database.execSQL("DELETE FROM photos WHERE places_id = '"+placesIdHistorial+"'");
                }

                database.execSQL("DELETE FROM historial WHERE places_id = '"+placesIdHistorial+"'");
            }
        }
    }

    public ArrayList<RestaurantModel> getRestaurantsHistoric() {

        Log.d("RESTASEARCH", "getRestaurantsHistoric: ");

        ArrayList<RestaurantModel> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String args[] = {Keys.DATABASE_LIMIT_HISTORIC};
        Cursor c = db.rawQuery("SELECT places_id, rating, address, price_level, name, user_ratings_total FROM historial LIMIT ?", args);

        if (c.moveToFirst()) {
            do {
                RestaurantModel model = new RestaurantModel();

                model.setPlace_id(c.getString(0));
                model.setRating(c.getDouble(1));
                model.setFormatted_address(c.getString(2));
                model.setPrice_level(c.getInt(3));
                model.setName(c.getString(4));
                model.setUser_ratings_total(c.getInt(5));

                String args2[] = {model.getPlace_id()};
                Cursor c2 = db.rawQuery("SELECT height, photo_reference, width FROM photos WHERE places_id = ? LIMIT 1", args2);

                if (c2.moveToFirst()) {
                    Photo photo = new Photo(c2.getInt(0), null, c2.getString(1), c2.getInt(2));
                    model.setPhotos(new ArrayList<>(Arrays.asList(photo)));
                }

                list.add(model);
            } while (c.moveToNext());
        }

        return list;
    }

    public ArrayList<RestaurantModel> getRestaurantsPreferits() {

        ArrayList<RestaurantModel> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String args[] = {Keys.DATABASE_LIMIT_HISTORIC};
        Cursor c = db.rawQuery("SELECT places_id, rating, address, price_level, name, user_ratings_total FROM favourites LIMIT ?", args);

        if (c.moveToFirst()) {
            do {
                RestaurantModel model = new RestaurantModel();

                model.setPlace_id(c.getString(0));
                model.setRating(c.getDouble(1));
                model.setFormatted_address(c.getString(2));
                model.setPrice_level(c.getInt(3));
                model.setName(c.getString(4));
                model.setUser_ratings_total(c.getInt(5));

                String args2[] = {model.getPlace_id()};
                Cursor c2 = db.rawQuery("SELECT height, photo_reference, width FROM photos WHERE places_id = ? LIMIT 1", args2);

                if (c2.moveToFirst()) {
                    Photo photo = new Photo(c2.getInt(0), null, c2.getString(1), c2.getInt(2));
                    model.setPhotos(new ArrayList<>(Arrays.asList(photo)));
                }

                list.add(model);
            } while (c.moveToNext());
        }

        return list;
    }

    public boolean isRestaurantByPlacesId(String places_id){
        boolean result = false;
        database = this.getReadableDatabase();

        try{
            cursor = database.rawQuery("" +
                    "SELECT * " +
                    "FROM favourites " +
                    "WHERE places_id = '" + places_id + "'", null);
        }
        catch (Exception e){
            Log.d("RESTASEARCH", e.getMessage());
        }

        try {
            result = cursor.moveToFirst();
        }
        catch (Exception e){
            Log.d("RESTASEARCH", "Error al llegir les dades del select. Error: " + e.getMessage());
        }

        return result;
    }

    public int countHistorial(){
        int result = 0;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("SELECT COUNT(*) FROM historial",null);

        try{
            if(cursor.moveToFirst()){
                result = cursor.getInt(0);
            }
        }
        catch (Exception e){
            Log.d("RESTASEARCH", e.getMessage());
        }

        return result;
    }

}
