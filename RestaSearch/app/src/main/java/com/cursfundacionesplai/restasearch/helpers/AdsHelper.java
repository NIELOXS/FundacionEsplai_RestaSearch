package com.cursfundacionesplai.restasearch.helpers;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;

public class AdsHelper {

    private static Merlin merlin = null;
    private static MerlinsBeard merlinsBeard = null;

    /**
     * Carregar un anunci en un contenidor per que es mostri en pantalla.
     * @param context El context
     * @param adView El contenidor en el qual es mostrarà l'anunci
     */
    public static void loadAd(Context context, AdView adView) {

        if (merlin == null) {
            merlin = new Merlin.Builder().withConnectableCallbacks().withDisconnectableCallbacks().build(context);
            if (merlinsBeard == null) {
                merlinsBeard = new MerlinsBeard.Builder().build(context);
            }
        }

        // si te conexio amb wifi o amb dades, mostrar el contenidor d'anuncis
        if (merlinsBeard.isConnectedToWifi() || merlinsBeard.isConnectedToMobileNetwork()) {

            // crear una peticio per generar un anunci
            AdRequest adRequest = new AdRequest.Builder().build();

            // carregar l'anunci generat al contenidor d'anuncis
            adView.loadAd(adRequest);
        }
    }
}
