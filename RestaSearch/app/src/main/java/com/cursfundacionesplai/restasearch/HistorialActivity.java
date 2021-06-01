package com.cursfundacionesplai.restasearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.cursfundacionesplai.restasearch.adapters.RestaurantAdapter;
import com.cursfundacionesplai.restasearch.classesextended.ToolbarEx;
import com.cursfundacionesplai.restasearch.helpers.DBHelper;
import com.cursfundacionesplai.restasearch.helpers.LanguageHelper;
import com.cursfundacionesplai.restasearch.helpers.WSHelper;
import com.cursfundacionesplai.restasearch.models.Keys;
import com.cursfundacionesplai.restasearch.models.Photo;
import com.cursfundacionesplai.restasearch.models.RestaurantList;
import com.cursfundacionesplai.restasearch.models.RestaurantModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;
import java.util.Arrays;

public class HistorialActivity extends AppCompatActivity {

    ToolbarEx toolbarEx;

    RecyclerView list;

    DBHelper dbh;
    RestaurantAdapter adapter;
    ArrayList<RestaurantModel> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe HistorialActivity");

        LanguageHelper.loadSavedLanguage(this);

        setContentView(R.layout.activity_historial);

        Bundle extras = getIntent().getExtras();
        setTitle(extras.getString("titol"));

        list = findViewById(R.id.list);

        toolbarEx = new ToolbarEx(this,
                this.findViewById(R.id.toolbar),
                this.findViewById(R.id.drawer_layout),
                this.findViewById(R.id.navigation_view));

        toolbarEx.getNavigationView().setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return toolbarEx.onNavigationItemSelected(item);
            }
        });

        dbh = new DBHelper(this, Keys.DATABASE_NAME, null, Keys.DATABASE_VERSION);

        restaurants = dbh.getRestaurantsHistoric();

        adapter = new RestaurantAdapter(this, new WSHelper(this), restaurants);

        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));


        Log.d("RESTASEARCH", "count: " + restaurants.size());

//        adapter.notifyDataSetChanged();

    }
}