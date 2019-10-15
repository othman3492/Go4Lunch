package com.othman.go4lunch.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.othman.go4lunch.R;
import com.othman.go4lunch.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsWorkmatesAdapter extends RecyclerView.Adapter<DetailsWorkmatesAdapter.DetailsWorkmatesViewHolder> {



    private final List<User> workmatesList;


    public DetailsWorkmatesAdapter(List<User> workmatesList) {

        this.workmatesList = workmatesList;
    }


    @NonNull
    @Override
    public DetailsWorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_layout, parent, false);

        return new DetailsWorkmatesViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull DetailsWorkmatesViewHolder viewHolder, int position) {


        viewHolder.populateViewHolder(this.workmatesList.get(position));
    }

    @Override
    public int getItemCount() {

        return workmatesList.size();
    }


    class DetailsWorkmatesViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.workmates_image)
        ImageView workmateImage;
        @BindView(R.id.workmates_text_view)
        TextView workmateText;


        DetailsWorkmatesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }


        // Update UI with text and image
        void populateViewHolder(User workmate) {


            workmateText.setText(workmate.getUsername() + " is joining !");

            Picasso.get().load(workmate.getUrlPicture()).into(workmateImage);


        }
    }
}
