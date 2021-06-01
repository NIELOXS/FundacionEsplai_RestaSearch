package com.cursfundacionesplai.restasearch;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.cursfundacionesplai.restasearch.helpers.DBHelper;
import com.cursfundacionesplai.restasearch.models.Keys;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class EstablimentDialog extends DialogFragment {

    boolean checked = false;

    public static final String TAG = "establiment_dialog";

    // Elements de vista
    private Toolbar toolbar;

    // variables
    private String placeId;
    private String placeName;

    public EstablimentDialog(String placeId, String placeName) {
        this.placeId = placeId;
        this.placeName = placeName;
    }

    public static EstablimentDialog display(FragmentManager fragmentManager, String placeId, String placeName) {
        EstablimentDialog dialog = new EstablimentDialog(placeId, placeName);
        dialog.show(fragmentManager, TAG);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe EstablimentDialog");
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        // si el dialeg no es null canviar la mida per que ocupi tota la pantalla
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);

            // aplicar animacions quan s'obre i es tancar el dialeg
            dialog.getWindow().setWindowAnimations(R.style.Theme_RestaSearch_Slide);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.establiment_dialog, container, false);

        toolbar = view.findViewById(R.id.toolbar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DBHelper dbHelper = new DBHelper(view.getContext(), Keys.DATABASE_NAME, null, Keys.DATABASE_VERSION);

        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(placeName);
        toolbar.inflateMenu(R.menu.menu_dialog);

        if (dbHelper.isRestaurantByPlacesId(placeId)){
            toolbar.getMenu().getItem(0).setIcon(getResources().getIdentifier("@drawable/baseline_bookmark_24", null, getContext().getPackageName()));
        }
        else{
            toolbar.getMenu().getItem(0).setIcon(getResources().getIdentifier("@drawable/baseline_bookmark_border_24", null, getContext().getPackageName()));
        }

        toolbar.setOnMenuItemClickListener(item -> {
            checked = !checked;
            if (checked){
                item.setIcon(getResources().getIdentifier("@drawable/baseline_bookmark_24", null, getContext().getPackageName()));
                dbHelper.insertFavourites(placeId);
            }
            else{
                item.setIcon(getResources().getIdentifier("@drawable/baseline_bookmark_border_24", null, getContext().getPackageName()));
                dbHelper.deleteFavourites(placeId);
            }
            return true;
        });

        getChildFragmentManager().beginTransaction().replace(R.id.linear_layout, EstablimentFragment.newInstance(placeId)).commit();
    }
}
