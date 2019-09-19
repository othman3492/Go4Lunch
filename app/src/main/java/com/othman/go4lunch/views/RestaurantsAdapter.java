package com.othman.go4lunch.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.othman.go4lunch.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/*public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder> {


    // Interface to configure a listener on RecyclerView items
    public interface RecyclerViewOnClickListener {

        void recyclerViewOnClick(int position);
    }


    private final List<Restaurant> restaurantsList;
    private final RecyclerViewOnClickListener listener;


    public RestaurantsAdapter(List<Restaurant> restaurantsList, RecyclerViewOnClickListener listener) {

        this.restaurantsList = restaurantsList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurants_layout, parent, false);

        return new RestaurantsViewHolder(v, listener);
    }


    @Override
    public void onBindViewHolder(@NonNull RestaurantsViewHolder viewHolder, int position) {


        viewHolder.populateViewHolder(this.restaurantsList.get(position));
    }


    @Override
    public int getItemCount() {

        return restaurantsList.size();

    }



    class RestaurantsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.restaurant_image)
        ImageView restaurantImage;
        @BindView(R.id.restaurant_workmates)
        ImageView restaurantWorkmates;
        @BindView(R.id.restaurant_stars)
        ImageView restaurantStars;
        @BindView(R.id.restaurant_name)
        TextView restaurantName;
        @BindView(R.id.restaurant_type_and_address)
        TextView restaurantTypeAndAddress;
        @BindView(R.id.restaurant_hours)
        TextView restaurantHours;
        @BindView(R.id.restaurant_distance)
        TextView restaurantDistance;
        @BindView(R.id.restaurant_workmates_number)
        TextView restaurantWorkmatesNumber;


        final RecyclerViewOnClickListener recyclerViewOnClickListener;


        RestaurantsViewHolder(View view, RecyclerViewOnClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            this.recyclerViewOnClickListener = listener;
            view.setOnClickListener(this);

        }


        // Update UI with text and image
        void populateViewHolder(Restaurant restaurant) {







        }

        @Override
        public void onClick(View v) {

            recyclerViewOnClickListener.recyclerViewOnClick(getAdapterPosition());

        }
    }

}
*/