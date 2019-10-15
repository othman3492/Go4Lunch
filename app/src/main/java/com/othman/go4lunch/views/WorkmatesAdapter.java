package com.othman.go4lunch.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.othman.go4lunch.R;
import com.othman.go4lunch.models.User;
import com.othman.go4lunch.models.Workmate;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.WorkmatesViewHolder> {


    // Interface to configure a listener on RecyclerView items
    public interface RecyclerViewOnClickListener {

        void recyclerViewOnClick(int position);
    }


    private final List<User> workmatesList;


    public WorkmatesAdapter(List<User> workmatesList) {

        this.workmatesList = workmatesList;
    }


    @NonNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_layout, parent, false);

        return new WorkmatesViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull WorkmatesViewHolder viewHolder, int position) {


        viewHolder.populateViewHolder(this.workmatesList.get(position));
    }

    @Override
    public int getItemCount() {

        return workmatesList.size();
    }


    class WorkmatesViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.workmates_image)
        ImageView workmateImage;
        @BindView(R.id.workmates_text_view)
        TextView workmateText;


        WorkmatesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }


        // Update UI with text and image
        void populateViewHolder(User workmate) {


            if (workmate.getChosenRestaurant() != null) {
                workmateText.setText(workmate.getUsername() + " has chosen to go to " +  workmate.getChosenRestaurant().getName());
            } else {
                workmateText.setText(workmate.getUsername() + " has not chosen any restaurant today.");
            }

            if (workmate.getUrlPicture() != null) {
                Picasso.get().load(workmate.getUrlPicture()).into(workmateImage);
            } else {
                Picasso.get().load(R.drawable.blank_profile).into(workmateImage);
            }


        }
    }
}
