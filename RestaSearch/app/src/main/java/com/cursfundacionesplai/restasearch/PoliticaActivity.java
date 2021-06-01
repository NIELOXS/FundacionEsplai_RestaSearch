package com.cursfundacionesplai.restasearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.cursfundacionesplai.restasearch.classesextended.ToolbarEx;
import com.cursfundacionesplai.restasearch.helpers.LanguageHelper;
import com.cursfundacionesplai.restasearch.models.Keys;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class PoliticaActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    TextView mainTitle;
    CheckBox checkPolicy;
    Button btnAccept;
    ToolbarEx toolbarEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe PoliticaActivity");

        LanguageHelper.loadSavedLanguage(this);

        setContentView(R.layout.activity_politica);

        Bundle extras = getIntent().getExtras();
        setTitle(extras.getString("titol"));

        prefs = getSharedPreferences(Keys.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();

        mainTitle = findViewById(R.id.main_title);
        checkPolicy = this.findViewById(R.id.box_policy);
        btnAccept = this.findViewById(R.id.btn_accept);

        toolbarEx = new ToolbarEx(this,
                this.findViewById(R.id.toolbar),
                this.findViewById(R.id.drawer_layout),
                this.findViewById(R.id.navigation_view));

        if(prefs.getBoolean(Keys.PREFS_SAVE_POLICY,false)){
            mainTitle.setVisibility(View.GONE);
            btnAccept.setVisibility(View.GONE);
            checkPolicy.setVisibility(View.GONE);

            toolbarEx.getNavigationView().setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    return toolbarEx.onNavigationItemSelected(item);
                }
            });
        }
        else{
            toolbarEx.getToolbar().setVisibility(View.GONE);
        }

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RESTASEARCH", "onClick: ");
                boolean isChecked = checkPolicy.isChecked();
                if (!isChecked)
                    Toast.makeText(PoliticaActivity.this, getResources().getString(R.string.accept_privacity), Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    editor.putBoolean(Keys.PREFS_SAVE_POLICY, true);
                    editor.apply();
                    finish();
                }
            }
        });

    }
}