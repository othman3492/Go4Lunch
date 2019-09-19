package com.othman.go4lunch.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.othman.go4lunch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/*public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.WorkmatesViewHolder> {


// Interface to configure a listener on RecyclerView items
public interface RecyclerViewOnClickListener {

    void recyclerViewOnClick(int position);
}


    private final List<Workmate> workmatesList;
    private final RecyclerViewOnClickListener listener;


    public WorkmatesAdapter(List<Workmate> workmatesList, RecyclerViewOnClickListener listener) {

        this.workmatesList = workmatesList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_layout, parent, false);

        return new ArticlesViewHolder(v, listener);
    }


    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder viewHolder, int position) {


        viewHolder.populateViewHolder(this.workmatesList.get(position));
    }


    @Override
    public int getItemCount() {

        return workmatesList.size();

    }



class ArticlesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    @BindView(R.id.workmates_image)
    ImageView workmateImage;
    @BindView(R.id.workmates_text_view)
    TextView workmateText;

    final RecyclerViewOnClickListener recyclerViewOnClickListener;


    ArticlesViewHolder(View view, RecyclerViewOnClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);
        this.recyclerViewOnClickListener = listener;
        view.setOnClickListener(this);

    }


    // Update UI with text and image
    void populateViewHolder(Workmate workmate) {

        workmateText.setText(article.getSection());






    }

    @Override
    public void onClick(View v) {

        recyclerViewOnClickListener.recyclerViewOnClick(getAdapterPosition());

    }
}

} {
}*/
