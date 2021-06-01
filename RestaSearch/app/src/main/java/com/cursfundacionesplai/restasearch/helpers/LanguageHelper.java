package com.cursfundacionesplai.restasearch.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.cursfundacionesplai.restasearch.models.Keys;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Locale;

public class LanguageHelper {

    private static String defaultLanguage = "en";

    private static SharedPreferences prefs;

    /**
     * Carregar el codi de l'idioma seleccionat a les preferencies, si no hi ha n'agafa un per defecte.
     * @param context El context per agafar les preferencies
     */
    public static void loadSavedLanguage(Context context) {
        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe LanguageHelper");

        if (prefs == null) {
            prefs = context.getSharedPreferences(Keys.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        }

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(prefs.getString(Keys.PREFS_SAVED_LANG, defaultLanguage)));
        resources.updateConfiguration(config, dm);
    }

    /**
     * Guardar l'idioma seleccionat a les preferencies
     * @param context El context per guardar les preferencies
     * @param lang el codi de l'idioma que es vol guardar
     */
    public static void saveLanguage(Context context, String lang) {
        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe LanguageHelper");

        if (prefs == null) {
            prefs = context.getSharedPreferences(Keys.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        }

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(Keys.PREFS_SAVED_LANG, lang);
        editor.apply();
    }

    public static String getSavedLanguage(Context context) {

        if (prefs == null) {
            prefs = context.getSharedPreferences(Keys.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        }

        return prefs.getString(Keys.PREFS_SAVED_LANG, defaultLanguage);
    }
}
