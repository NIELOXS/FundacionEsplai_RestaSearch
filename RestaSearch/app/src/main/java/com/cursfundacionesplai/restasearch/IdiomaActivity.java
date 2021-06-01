package com.cursfundacionesplai.restasearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cursfundacionesplai.restasearch.helpers.LanguageHelper;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class IdiomaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe IdiomaActivity");

        LanguageHelper.loadSavedLanguage(this);

        setContentView(R.layout.activity_idioma);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.gc();

                LanguageHelper.saveLanguage(IdiomaActivity.this, v.getTag().toString());

                startActivity(new Intent(IdiomaActivity.this, MainActivity.class));
                finish();
            }
        };

        Button en = findViewById(R.id.lang_en);
        en.setOnClickListener(listener);
        Button es = findViewById(R.id.lang_es);
        es.setOnClickListener(listener);
        Button ca = findViewById(R.id.lang_ca);
        ca.setOnClickListener(listener);

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int color = R.color.primary_color;

        switch (LanguageHelper.getSavedLanguage(this)) {
            case "en":
                en.setBackgroundColor(getResources().getColor(color));
                break;
            case "es":
                es.setBackgroundColor(getResources().getColor(color));
                break;
            case "ca":
                ca.setBackgroundColor(getResources().getColor(color));
                break;
        }
    }
}