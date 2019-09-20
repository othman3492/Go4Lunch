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


    private final List<Workmate> workmatesList;


    public WorkmatesAdapter(List<Workmate> workmatesList) {

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
        void populateViewHolder(Workmate workmate) {


            workmateText.setText("Hello ! I'm your workmate.");

            Picasso.get().load(R.drawable.app_logo).into(workmateImage);


        }
    }
}
