package com.cursfundacionesplai.restasearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cursfundacionesplai.restasearch.EstablimentDetailActivity;
import com.cursfundacionesplai.restasearch.R;
import com.cursfundacionesplai.restasearch.helpers.WSHelper;
import com.cursfundacionesplai.restasearch.interfaces.CustomResponse;
import com.cursfundacionesplai.restasearch.models.Keys;
import com.cursfundacionesplai.restasearch.models.Photo;
import com.cursfundacionesplai.restasearch.models.RestaurantList;
import com.cursfundacionesplai.restasearch.models.RestaurantModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private Context context;
    private WSHelper helper;
    private ArrayList<RestaurantModel> restaurantLists;

    public RestaurantAdapter(Context context, WSHelper helper, ArrayList<RestaurantModel> restaurantLists) {
        this.context = context;
        this.helper = helper;
        this.restaurantLists = restaurantLists;
    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_restaurant_item, parent, false);
        RestaurantAdapter.ViewHolder holder = new RestaurantAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.ViewHolder holder, int position) {

        RestaurantModel r = restaurantLists.get(position);

        holder.name.setText(r.getName());
        holder.address.setText(r.getFormatted_address());
        holder.rating.setText(context.getResources().getString(R.string.label_establiment_global_rating, r.getRating()));
        holder.reviews.setText(context.getResources().getString(R.string.label_establiment_total_reviews, r.getUser_ratings_total()));

        ArrayList<Photo> photos = r.getPhotos();

        if (photos != null && photos.size() > 0) {
            Picasso.get().load(photos.get(0).buildUrl()).into(holder.photo);
        }
    }

    @Override
    public int getItemCount() {
        return restaurantLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;

        TextView name;
        TextView address;
        TextView rating;
        TextView reviews;

        public ViewHolder(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.rest_item_photo);

            name = itemView.findViewById(R.id.rest_item_name);
            address = itemView.findViewById(R.id.rest_item_address);
            rating = itemView.findViewById(R.id.rest_item_rating);
            reviews = itemView.findViewById(R.id.rest_item_num_reviews);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EstablimentDetailActivity.class);
                    intent.putExtra("place_id", restaurantLists.get(getAdapterPosition()).getPlace_id());
                    intent.putExtra("place_name", restaurantLists.get(getAdapterPosition()).getName());

                    context.startActivity(intent);
                }
            });
        }
    }
}
