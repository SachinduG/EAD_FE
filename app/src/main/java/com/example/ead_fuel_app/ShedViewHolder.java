/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
 */

package com.example.ead_fuel_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ShedViewHolder extends RecyclerView.ViewHolder {

    public TextView shedName,shedAddress;
    public CardView shedCardView;

    public ShedViewHolder(@NonNull View itemView) {
        super(itemView);

        shedName = itemView.findViewById(R.id.shedName);
        shedAddress = itemView.findViewById(R.id.shedAddress);
        shedCardView = itemView.findViewById(R.id.shedCardView);
    }
}
