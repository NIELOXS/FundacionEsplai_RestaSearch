package com.cursfundacionesplai.restasearch.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cursfundacionesplai.restasearch.R;
import com.cursfundacionesplai.restasearch.models.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<Review> reviews;

    public ReviewAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_review_item, parent, false);
        ReviewAdapter.ViewHolder holder = new ReviewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {

        Review r = reviews.get(position);

        Picasso.get().load(r.getProfile_photo_url()).into(holder.profile);

        holder.author.setText(r.getAuthor_name());
        holder.desc.setText(r.getText());
        holder.time.setText(r.getRelative_time_description());
        holder.rating.setRating(Float.parseFloat("" + r.getRating()));

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profile;
        TextView author;
        TextView desc;
        TextView time;
        RatingBar rating;

        public ViewHolder(View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile_photo);
            author = itemView.findViewById(R.id.review_author_name);
            desc = itemView.findViewById(R.id.review_text);
            time = itemView.findViewById(R.id.review_relative_time);
            rating = itemView.findViewById(R.id.review_rating);
        }
    }
}
