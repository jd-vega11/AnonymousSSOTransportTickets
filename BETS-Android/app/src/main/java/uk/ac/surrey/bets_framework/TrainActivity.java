package uk.ac.surrey.bets_framework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.ac.surrey.bets_framework.adapters.TicketItem;

public class TrainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText edittext_fecha;

    private String origen;
    private String destino;
    private String hora;

    private ArrayList<TicketItem> servicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

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

        //Fecha
        final TextInputLayout textinput_fecha = (TextInputLayout) findViewById(R.id.t_tren_fecha);
        edittext_fecha = textinput_fecha.getEditText();



        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        };

        textinput_fecha.setEndIconOnClickListener(listener);

        //Hora

        final TextInputLayout textinput_hora = (TextInputLayout) findViewById(R.id.t_tren_hora);
        final AutoCompleteTextView edittext_hora = (AutoCompleteTextView)textinput_hora.getEditText();

        final String[] horas = new String[16];
        for(int i = 0; i<horas.length;i++)
        {
            horas[i] = (i+6)+":00";
        }

        ArrayAdapter adapter_hora = new ArrayAdapter(this, R.layout.basic_list_item, horas);
        edittext_hora.setAdapter(adapter_hora);

        AdapterView.OnItemClickListener hora_listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hora = horas[position];
            }
        };
        edittext_hora.setOnItemClickListener(hora_listener);

        //Origen
        final String[] ubicaciones = new String[]{"Bogotá", "Chía", "Cajicá", "Zipaquira", "Tenjo"};

        final TextInputLayout textinput_origen = (TextInputLayout) findViewById(R.id.t_tren_origen);
        final AutoCompleteTextView edittext_origen = (AutoCompleteTextView)textinput_origen.getEditText();

        ArrayAdapter adapter_origen = new ArrayAdapter(this, R.layout.basic_list_item, ubicaciones);
        edittext_origen.setAdapter(adapter_origen);

        AdapterView.OnItemClickListener origen_listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                origen = ubicaciones[position];
            }
        };
        edittext_origen.setOnItemClickListener(origen_listener);
        //Destino
        final TextInputLayout textinput_destino = (TextInputLayout) findViewById(R.id.t_tren_destino);
        final AutoCompleteTextView edittext_destino = (AutoCompleteTextView)textinput_destino.getEditText();

        ArrayAdapter adapter_destino = new ArrayAdapter(this, R.layout.basic_list_item, ubicaciones);
        edittext_destino.setAdapter(adapter_destino);

        AdapterView.OnItemClickListener destino_listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                destino = ubicaciones[position];
            }
        };

        edittext_destino.setOnItemClickListener(destino_listener);

        //Boton
        View.OnClickListener button_listener = new View.OnClickListener() {
            public void onClick(View v) {
                int tarifa = calcularTarifa();

                if(TextUtils.isEmpty(edittext_fecha.getText()))
                {
                    Toast.makeText(getApplicationContext(),"Debe seleccionar una fecha",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(hora))
                {
                    Toast.makeText(getApplicationContext(),"Debe seleccionar una hora",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(tarifa == -2){
                    Toast.makeText(getApplicationContext(),"Debe ingresar tanto origen como destino",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(tarifa == -1){
                    Toast.makeText(getApplicationContext(),"El origen no puede ser igual al destino",Toast.LENGTH_SHORT).show();
                    return;
                }

                String detalleViaje = "Viaje para el " + edittext_fecha.getText() + " a las " + hora + " desde " + origen + " hasta " +destino;

                servicios.add(new TicketItem(TicketItem.TREN, tarifa, detalleViaje));

                Intent intent = new Intent(TrainActivity.this, TicketSummaryActivity.class);
                intent.putExtra(TicketItem.SERVICIOS, servicios);
                startActivity(intent);
            }
        };

        Button button_continuar = (Button) findViewById(R.id.b_tren_datos_continuar);
        button_continuar.setOnClickListener(button_listener);

    }

    private int calcularTarifa()
    {
        if(TextUtils.isEmpty(origen) || TextUtils.isEmpty(destino)) return -2;
        if(origen.equals(destino)) return -1;

        int tarifa = 0;

        //Calculo experimental para probar distintas tarifas
        switch (origen)
        {
            case "Bogotá":
                tarifa = 10000;
                break;
            case "Chía":
                tarifa = 9000;
                break;
            case "Cajicá":
                tarifa = 8000;
                break;
            case "Zipaquira":
                tarifa = 7000;
                break;
            case "Tenjo":
                tarifa = 6000;
                break;
            default:
                tarifa = 5000;
                break;
        }

        switch (destino)
        {
            case "Bogotá":
                tarifa *= 1.8;
                break;
            case "Chía":
                tarifa *= 1.7;
                break;
            case "Cajicá":
                tarifa *= 1.6;
                break;
            case "Zipaquira":
                tarifa *= 1.5;
                break;
            case "Tenjo":
                tarifa *= 1.4;
                break;
            default:
                tarifa *= 1.3;
                break;
        }

        return tarifa;

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Seleccione la fecha de su viaje");
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        edittext_fecha.setText(day + "/" + month+"/"+ year);
    }


    public static class DatePickerFragment extends DialogFragment
             {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), (TrainActivity)getActivity(), year, month, day);
            pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return pickerDialog;
        }
    }

}