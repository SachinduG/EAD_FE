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

    public TextView shedName, shedCity;
    public CardView shedCard;

    public ShedViewHolder(@NonNull View itemView) {
        super(itemView);

        shedName = itemView.findViewById(R.id.shedName);
        shedCity = itemView.findViewById(R.id.shedAddress);
        shedCard = itemView.findViewById(R.id.shedCardView);
    }
}
