package com.cursfundacionesplai.restasearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cursfundacionesplai.restasearch.classesextended.ToolbarEx;
import com.cursfundacionesplai.restasearch.helpers.DBHelper;
import com.cursfundacionesplai.restasearch.models.Keys;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class EstablimentDetailActivity extends AppCompatActivity {

    ToolbarEx toolbar;
    private String placeId;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establiment_detail);
        Bundle bundle = getIntent().getExtras();
        placeId = bundle.getString("place_id");

        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe EstablimentDetailActivity");

        dbHelper = new DBHelper(this, Keys.DATABASE_NAME, null, Keys.DATABASE_VERSION);
        toolbar = new ToolbarEx(this,
                findViewById(R.id.toolbar),
                findViewById(R.id.drawer_layout),
                findViewById(R.id.navigation_view));

        toolbar.getNavigationView().setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return toolbar.onNavigationItemSelected(item);
            }
        });

        setTitle(bundle.getString("place_name"));

        toolbar.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.favourite){

                    if (!dbHelper.isRestaurantByPlacesId(placeId)){
                        item.setIcon(getResources().getIdentifier("@drawable/baseline_bookmark_24", null, getPackageName()));
                        dbHelper.insertFavourites(placeId);
                    }
                    else{
                        item.setIcon(getResources().getIdentifier("@drawable/baseline_bookmark_border_24", null, getPackageName()));
                        dbHelper.deleteFavourites(placeId);
                    }
                }
                return false;
            }
        });

        EstablimentFragment fragment = EstablimentFragment.newInstance(bundle.getString("place_id"));

        getSupportFragmentManager().beginTransaction().replace(R.id.list, fragment).commit();

    }
    @Override
    public void onBackPressed() {
        if (toolbar.getDrawerLayout().isDrawerOpen(GravityCompat.START)) {
            toolbar.getDrawerLayout().closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dialog, menu);
        if (dbHelper.isRestaurantByPlacesId(placeId)){
            menu.getItem(0).setIcon(getResources().getIdentifier("@drawable/baseline_bookmark_24", null, this.getPackageName()));
        }
        else{
            menu.getItem(0).setIcon(getResources().getIdentifier("@drawable/baseline_bookmark_border_24", null, this.getPackageName()));

        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}