package com.cursfundacionesplai.restasearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cursfundacionesplai.restasearch.adapters.ReviewAdapter;
import com.cursfundacionesplai.restasearch.helpers.DBHelper;
import com.cursfundacionesplai.restasearch.helpers.WSHelper;
import com.cursfundacionesplai.restasearch.interfaces.CustomResponse;
import com.cursfundacionesplai.restasearch.models.Keys;
import com.cursfundacionesplai.restasearch.models.Photo;
import com.cursfundacionesplai.restasearch.models.RestaurantList;
import com.cursfundacionesplai.restasearch.models.RestaurantModel;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstablimentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstablimentFragment extends Fragment {

    // Elements de vista
    NestedScrollView visible;
    ConstraintLayout notVisible;

    ImageView photo;
    ImageView iconPrev;
    ImageView iconNext;
    TextView labelPhotoPos;

    TextView labelAddress;
    TextView labelPhoneNumber;
    TextView labelRating;
    TextView labelReviews;
    ImageView iconWebsite;
    TextView labelWebsite;
    TextView labelReview;
    TextView weekText;

    ListView listHours;
    RecyclerView listReviews;

    private int currentPhotoPos;
    private ArrayList<Photo> photos;



    // variables
    private String placeId;

    public EstablimentFragment() {
        // Required empty public constructor
        currentPhotoPos = 0;
    }

    public static EstablimentFragment newInstance(String placeId) {
        EstablimentFragment fragment = new EstablimentFragment();
        Bundle args = new Bundle();
        args.putString("placeId", placeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseCrashlytics.getInstance().setUserId("RESTASEARCH_PROVA");
        FirebaseCrashlytics.getInstance().setCustomKey("RESTASEARCH_PROVA","S'ha produit un error a la classe EstablimentFragment");

        if (getArguments() != null) {
            placeId = getArguments().getString("placeId");
        }

        new WSHelper(getContext()).getEstablimentDetails(placeId, new CustomResponse.EstablimentDetail() {
            @Override
            public void onEstablimentResponse(RestaurantModel r) {
                if (r != null) {
                    labelAddress.setText(r.getFormatted_address());
                    labelRating.setText(getResources().getString(R.string.label_establiment_global_rating, r.getRating()));
                    labelReviews.setText(getResources().getString(R.string.label_establiment_total_reviews, r.getUser_ratings_total()));

                    labelPhoneNumber.setText(r.getInternational_phone_number());
                    labelPhoneNumber.setOnClickListener(v -> {
                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setTitle(getResources().getString(R.string.alert_call_title));
                        alert.setMessage(getResources().getString(R.string.alert_call_message) + " " + r.getInternational_phone_number());
                        alert.setPositiveButton(getResources().getString(R.string.button_accept_policy),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String numero = r.getInternational_phone_number();
                                        if(!TextUtils.isEmpty(numero)){
                                            String dial = "tel:" + numero;
                                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                                        }
                                        Log.d("RESTASEARCH","S'ha volgut trucar al següent número de teléfon: " + numero);
                                    }
                                });
                        alert.setNeutralButton(getResources().getString(R.string.button_cancel),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("RESTASEARCH","S'ha cancel·lat el trucar a un número de teléfon");
                                    }
                                });
                        alert.show();
                    });


                    // comprova si l'establiment conté una pàgina web
                    if (r.getWebsite() == null) {
                        iconWebsite.setVisibility(View.GONE);
                        labelWebsite.setVisibility(View.GONE);
                    } else {
                        labelWebsite.setText(r.getWebsite());
                    }

                    if (r.getOpening_hours()!= null && r.getOpening_hours().getWeekday_text() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.simple_list_item, r.getOpening_hours().getWeekday_text());
                        listHours.setAdapter(adapter);
                    }
                    else{
                        weekText.setVisibility(View.GONE);
                        listHours.setVisibility(View.GONE);
                    }

                    photos = r.getPhotos();

                    if (photos != null && photos.size() > 0) {
                        loadPhotoIntoContainer();
                    }
                    else{
                        photo.setVisibility(View.GONE);
                        iconPrev.setVisibility(View.GONE);
                        iconNext.setVisibility(View.GONE);
                        labelPhotoPos.setVisibility(View.GONE);
                    }
                    if (r.getReviews() != null){
                        ReviewAdapter adapter = new ReviewAdapter(r.getReviews());

                        listReviews.setAdapter(adapter);
                        listReviews.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                    else{
                        listReviews.setVisibility(View.GONE);
                        labelReview.setVisibility(View.GONE);
                    }
                    // amaga el contenidor del progress bar i mostrar el contenidor de les dades
                    notVisible.setVisibility(View.GONE);
                    visible.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void loadPhotoIntoContainer() {
        labelPhotoPos.setText(getResources().getString(R.string.establiment_photo_page, currentPhotoPos + 1, photos.size()));
        Picasso.get().load(photos.get(currentPhotoPos).buildUrl()).into(photo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_establiment, container, false);

        labelReview = view.findViewById(R.id.labelReview);
        visible = view.findViewById(R.id.scroll_loaded);
        notVisible = view.findViewById(R.id.constraint_not_loaded);
        weekText = view.findViewById(R.id.weekText);

        photo = view.findViewById(R.id.rest_photo);
        iconPrev = view.findViewById(R.id.iconPrev);
        iconNext = view.findViewById(R.id.iconNext);
        labelPhotoPos = view.findViewById(R.id.label_photos_pages);

        iconPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPhotoPos--;

                if (currentPhotoPos == -1) {
                    currentPhotoPos = photos.size() - 1;
                }

                loadPhotoIntoContainer();
            }
        });

        iconNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPhotoPos++;

                if (currentPhotoPos == photos.size()) {
                    currentPhotoPos = 0;
                }

                loadPhotoIntoContainer();
            }
        });

        labelAddress = view.findViewById(R.id.label_address);
        labelPhoneNumber = view.findViewById(R.id.label_phone_number);
        labelRating = view.findViewById(R.id.label_rating);
        labelReviews = view.findViewById(R.id.label_reviews);

        iconWebsite = view.findViewById(R.id.icon_website);
        labelWebsite = view.findViewById(R.id.label_website);

        listHours = view.findViewById(R.id.list_hours);
        listReviews = view.findViewById(R.id.list_reviews);

        return view;
    }
}