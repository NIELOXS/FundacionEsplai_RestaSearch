package com.cursfundacionesplai.restasearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.cursfundacionesplai.restasearch.adapters.RestaurantAdapter;
import com.cursfundacionesplai.restasearch.classesextended.ToolbarEx;
import com.cursfundacionesplai.restasearch.helpers.DBHelper;
import com.cursfundacionesplai.restasearch.helpers.LanguageHelper;
import com.cursfundacionesplai.restasearch.helpers.WSHelper;
import com.cursfundacionesplai.restasearch.models.Keys;
import com.cursfundacionesplai.restasearch.models.RestaurantList;
import com.cursfundacionesplai.restasearch.models.RestaurantModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;

public class PreferitsActivity extends AppCompatActivity {

    ToolbarEx toolbarEx;

    RecyclerView list;

    DBHelper dbh;
    RestaurantAdapter adapter;
    ArrayList<RestaurantModel> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe PreferitsActivity");

        LanguageHelper.loadSavedLanguage(this);

        setContentView(R.layout.activity_preferits);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbh = new DBHelper(this, Keys.DATABASE_NAME, null, Keys.DATABASE_VERSION);

        restaurants = dbh.getRestaurantsPreferits();

        adapter = new RestaurantAdapter(this, new WSHelper(this), restaurants);

        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
    }
}