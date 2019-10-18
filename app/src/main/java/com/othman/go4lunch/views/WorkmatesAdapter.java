package com.othman.go4lunch.views;

import android.content.Context;
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

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.WorkmatesViewHolder> {



    private final List<User> workmatesList;
    private final Context context;


    public WorkmatesAdapter(List<User> workmatesList, Context context) {

        this.workmatesList = workmatesList;
        this.context = context;
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
                workmateText.setText(String.format(context.getResources().getString(R.string.choice), workmate.getUsername(),
                        workmate.getChosenRestaurant().getName()));
            } else {
                workmateText.setText(String.format(context.getResources().getString(R.string.no_choice), workmate.getUsername()));
            }

            if (workmate.getUrlPicture() != null) {
                Picasso.get().load(workmate.getUrlPicture()).into(workmateImage);
            } else {
                Picasso.get().load(R.drawable.blank_profile).into(workmateImage);
            }


        }
    }
}
