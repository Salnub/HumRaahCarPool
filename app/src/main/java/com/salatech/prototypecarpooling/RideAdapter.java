package com.salatech.prototypecarpooling;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.ViewHolder> {
    private List<Ride> rides;

    public RideAdapter(List<Ride> rides) {
        this.rides = rides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ride, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ride ride = rides.get(position);
        holder.textViewRideName.setText(ride.getName());
        holder.textViewRideDetails.setText(ride.getDetails());
    }

    @Override
    public int getItemCount() {
        return rides.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewRideName;
        public TextView textViewRideDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRideName = itemView.findViewById(R.id.textViewRideName);
            textViewRideDetails = itemView.findViewById(R.id.textViewRideDetails);
        }
    }
}

