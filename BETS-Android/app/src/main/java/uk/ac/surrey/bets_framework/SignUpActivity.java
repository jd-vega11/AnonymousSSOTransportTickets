package uk.ac.surrey.bets_framework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uk.ac.surrey.bets_framework.adapters.TicketItem;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final Button button_signup = (Button) findViewById(R.id.b_signup2);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProtocolProcessActivity.class);
                intent.putExtra(ProtocolProcessActivity.PROCESO, ProtocolProcessActivity.PROCESO_REGISTRO);
                v.getContext().startActivity(intent);
            }
        };

        button_signup.setOnClickListener(listener);
    }
}
