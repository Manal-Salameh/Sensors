package com.example.sensorsapp;

import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewerAdapter extends RecyclerView.Adapter<RecyclerViewerAdapter.ViewHolder> {

    private List<Sensor> sensorarray = new ArrayList<Sensor>();

    RecyclerViewerAdapter( List<Sensor> sensorarray){
        this.sensorarray = sensorarray;

    }
    @NonNull

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,parent,false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sensorListItem.setText(sensorarray.get(position).toString());
    }

    public int getItemCount() {
        return sensorarray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sensorListItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorListItem = itemView.findViewById(R.id.sensorListItem);
        }
    }
}
