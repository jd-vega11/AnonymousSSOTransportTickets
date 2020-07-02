package uk.ac.surrey.bets_framework.adapters;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import uk.ac.surrey.bets_framework.ProtocolProcessActivity;
import uk.ac.surrey.bets_framework.R;
import uk.ac.surrey.bets_framework.TicketDetailActivity;
import uk.ac.surrey.bets_framework.TicketSummaryActivity;

public class TicketDetailAdapter extends RecyclerView.Adapter<TicketDetailAdapter.TicketDetailViewHolder>{

    private ArrayList<TicketItem> items;

    public TicketDetailAdapter(ArrayList<TicketItem> pItems) {
        items = pItems;
    }

    @NonNull
    @Override
    public TicketDetailAdapter.TicketDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_item, parent, false);
        TicketDetailAdapter.TicketDetailViewHolder vh = new TicketDetailAdapter.TicketDetailViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketDetailAdapter.TicketDetailViewHolder holder, final int position) {

        holder.medioTransporte.setText(items.get(position).medioTransporte);
        holder.tarifa.setText("$ " +items.get(position).tarifa);

        if(TextUtils.isEmpty(items.get(position).detalleTren))
        {
            holder.detalleTren.setVisibility(View.GONE);
        }
        else
        {
            holder.detalleTren.setText(items.get(position).detalleTren);
        }

        if(items.get(position).validado)
        {
            holder.validar.setVisibility(View.GONE);
        }
        else
        {
            View.OnClickListener listener = new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProtocolProcessActivity.class);
                    intent.putExtra(TicketItem.SERVICIOS, items);
                    intent.putExtra(ProtocolProcessActivity.PROCESO, ProtocolProcessActivity.PROCESO_VALIDACION);
                    intent.putExtra(ProtocolProcessActivity.SERVICIO_SELECCIONADO, position);
                    v.getContext().startActivity(intent);
                }
            };

            holder.validar.setOnClickListener(listener);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class TicketDetailViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView medioTransporte;
        public TextView tarifa;
        public TextView detalleTren;
        public Button validar;

        public TicketDetailViewHolder(View v) {
            super(v);
            medioTransporte = v.findViewById(R.id.detail_item_medio);
            tarifa = v.findViewById(R.id.detail_item_tarifa);
            detalleTren = v.findViewById(R.id.detail_item_tren);
            validar = v.findViewById(R.id.b_detail_validar);
        }
    }
}
