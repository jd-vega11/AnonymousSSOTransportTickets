/**
 * DICE NFC evaluation.
 *
 * (c) University of Surrey and Pervasive Intelligence Ltd 2017.
 */
package uk.ac.surrey.bets_framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Launch activity for NFC application.
 *
 * @author Matthew Casey
 */
public class LaunchActivity extends AppCompatActivity {

  /** The broadcast receiver. */
  private ProcessingBroadcastReceiver receiver = new ProcessingBroadcastReceiver();

  /**
   * Called when the activity is starting.
   *
   * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains
   *                           the data it most recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is
   *                           null.</i></b>
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launch);

   /* final Button button = (Button) findViewById(R.id.button_example);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        iniciarServicioNFC();
      }
    });*/

    // Register for local broadcasts.
    IntentFilter filter = new IntentFilter(APDUService.BROADCAST_ACTION);
    LocalBroadcastManager.getInstance(this).registerReceiver(this.receiver, filter);

    final Button b_login = (Button)findViewById(R.id.b_login);
    b_login.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(LaunchActivity.this, MainManuActivity.class);
        startActivity(intent);
      }
    });


    final Button b_registrarse = (Button)findViewById(R.id.b_signup);
    b_registrarse.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(LaunchActivity.this, SignUpActivity.class);
        startActivity(intent);
      }
    });

  }

  /**
   * Perform any final cleanup before an activity is destroyed.
   */
  @Override
  protected void onDestroy() {
    // Unregister the broadcast receiver.
    //LocalBroadcastManager.getInstance(this).unregisterReceiver(this.receiver);
    Intent intent = new Intent(this, APDUService.class);
    stopService(intent);
    super.onDestroy();
  }

  private void iniciarServicioNFC()
  {
    Intent intent = new Intent(this, APDUService.class);
    startService(intent);
  }

  /**
   * Used to receive broadcast messages.
   */
  private class ProcessingBroadcastReceiver extends BroadcastReceiver {

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
      /*TextView textView = (TextView) LaunchActivity.this.findViewById(R.id.processingTextView);

      if (textView != null) {
        String message = intent.getStringExtra(APDUService.BROADCAST_ACTION_MESSAGE);

        if (message != null) {
          textView.setText(LaunchActivity.this.getResources().getString(R.string.processingText, message));
        }
        else {
          textView.setText(R.string.noProcessingText);
        }
      }*/
    }
  }
}
