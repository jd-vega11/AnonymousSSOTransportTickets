package uk.ac.surrey.bets_framework.adapters;

import android.opengl.Visibility;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import uk.ac.surrey.bets_framework.R;

public class TicketSummaryAdapter extends RecyclerView.Adapter<TicketSummaryAdapter.TicketSummaryViewHolder>{

    private List<TicketItem> items;

    public TicketSummaryAdapter(List<TicketItem> pItems) {
        items = pItems;
    }

    @NonNull
    @Override
    public TicketSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resumen_item, parent, false);
        TicketSummaryViewHolder vh = new TicketSummaryViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull TicketSummaryViewHolder holder, int position) {

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


    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class TicketSummaryViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView medioTransporte;
        public TextView tarifa;
        public TextView detalleTren;

        public TicketSummaryViewHolder(View v) {
            super(v);
            medioTransporte = v.findViewById(R.id.resumen_item_medio);
            tarifa = v.findViewById(R.id.resumen_item_tarifa);
            detalleTren = v.findViewById(R.id.resumen_item_tren);
        }
    }

}
