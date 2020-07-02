package uk.ac.surrey.bets_framework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import uk.ac.surrey.bets_framework.adapters.TicketItem;

public class NewTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button continue_button = (Button) findViewById(R.id.b_seleccionar_transporte);

        final CheckBox checkbox_transmilenio = (CheckBox) findViewById(R.id.checkbox_transmilenio);
        final CheckBox checkbox_sitp = (CheckBox) findViewById(R.id.checkbox_sitp);
        final CheckBox checkbox_tren = (CheckBox) findViewById(R.id.checkbox_tren);
        final CheckBox checkbox_metro = (CheckBox) findViewById(R.id.checkbox_metro);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {

                if(!checkbox_metro.isChecked() && !checkbox_sitp.isChecked() && !checkbox_transmilenio.isChecked() && !checkbox_tren.isChecked())
                {
                    Toast.makeText(getApplicationContext(),"Debe seleccionar por lo menos un medio de transporte",Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<TicketItem> services = new ArrayList<TicketItem>();

                if(checkbox_transmilenio.isChecked())services.add(new TicketItem(TicketItem.TRANSMILENIO, TicketItem.TRANSMILENIO_TARIFA, null));
                if(checkbox_sitp.isChecked())services.add(new TicketItem(TicketItem.SITP, TicketItem.SITP_TARIFA, null));
                if(checkbox_metro.isChecked())services.add(new TicketItem(TicketItem.METRO, TicketItem.METRO_TARIFA, null));

                if(checkbox_tren.isChecked())
                {
                    Intent intent = new Intent(NewTicketActivity.this, TrainActivity.class);
                    intent.putExtra(TicketItem.SERVICIOS, services);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(NewTicketActivity.this, TicketSummaryActivity.class);
                    intent.putExtra(TicketItem.SERVICIOS, services);
                    startActivity(intent);
                }
            }
        };

        continue_button.setOnClickListener(listener);

    }

}
