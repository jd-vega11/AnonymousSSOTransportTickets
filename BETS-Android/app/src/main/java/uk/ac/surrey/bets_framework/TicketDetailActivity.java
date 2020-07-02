package uk.ac.surrey.bets_framework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import uk.ac.surrey.bets_framework.adapters.TicketDetailAdapter;
import uk.ac.surrey.bets_framework.adapters.TicketItem;
import uk.ac.surrey.bets_framework.adapters.TicketSummaryAdapter;

public class TicketDetailActivity extends AppCompatActivity {

    private ArrayList<TicketItem> servicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                servicios = new ArrayList<TicketItem>();
            } else {
                servicios = (ArrayList<TicketItem>) extras.getSerializable(TicketItem.SERVICIOS);
            }
        } else {
            servicios = (ArrayList<TicketItem>) savedInstanceState.getSerializable(TicketItem.SERVICIOS);
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_detail);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TicketDetailAdapter adapter = new TicketDetailAdapter(servicios);
        recyclerView.setAdapter(adapter);

        final Button button_menu = (Button) findViewById(R.id.b_detail_menu);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TicketDetailActivity.this, MainManuActivity.class);
                startActivity(intent);
            }
        };

        button_menu.setOnClickListener(listener);
    }
}