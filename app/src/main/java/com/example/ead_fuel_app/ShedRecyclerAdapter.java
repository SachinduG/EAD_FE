/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
 */

package com.example.ead_fuel_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead_fuel_app.models.Shed;

import java.util.ArrayList;

public class ShedRecyclerAdapter extends RecyclerView.Adapter<ShedViewHolder> {

    private final ArrayList<Shed> sheds;
    private final ShedSelectListener shedSelectListener;

    public ShedRecyclerAdapter(Context context, ArrayList<Shed> sheds, ShedSelectListener shedSelectListener) {
        this.sheds = sheds;
        this.shedSelectListener = shedSelectListener;
    }

    @NonNull
    @Override
    public ShedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_shed,parent,false);
        return new ShedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShedViewHolder holder, int position) {
        Shed shed = sheds.get(position);

        holder.shedName.setText(shed.getName());
        holder.shedAddress.setText(shed.getAddress());

        holder.itemView.setOnClickListener(view -> shedSelectListener.onItemClicked(shed));
    }

    @Override
    public int getItemCount() {
        return sheds.size();
    }
}