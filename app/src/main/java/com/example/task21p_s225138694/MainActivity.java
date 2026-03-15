package com.example.task21p_s225138694;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //SOURCE SPINNER
        Spinner spinnerSource = (Spinner) findViewById(R.id.spinner_source);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter_src = ArrayAdapter.createFromResource(
                this,
                R.array.source_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter_src.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinnerSource.setAdapter(adapter_src);

        //DESTINATION SPINNER
        Spinner spinnerDestination = (Spinner) findViewById(R.id.spinner_destination);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter_dest = ArrayAdapter.createFromResource(
                this,
                R.array.destination_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter_dest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinnerDestination.setAdapter(adapter_dest);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Function to change the destination_spinner choices based on the source_spinner selected item
        spinnerSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                ArrayAdapter<CharSequence> adapter;

                if (selected.equals("USD")) {
                    adapter = ArrayAdapter.createFromResource(
                            MainActivity.this,
                            R.array.currency_array,
                            android.R.layout.simple_spinner_item
                    );
                } else if (selected.equals("mpg")) {
                    adapter = ArrayAdapter.createFromResource(
                            MainActivity.this,
                            R.array.fuel_kmL_array,
                            android.R.layout.simple_spinner_item
                    );
                } else if (selected.equals("Gallon")) {
                    adapter = ArrayAdapter.createFromResource(
                            MainActivity.this,
                            R.array.fuel_L_array,
                            android.R.layout.simple_spinner_item
                    );
                } else if (selected.equals("Nautical Mile")) {
                    adapter = ArrayAdapter.createFromResource(
                            MainActivity.this,
                            R.array.fuel_km_array,
                            android.R.layout.simple_spinner_item
                    );
                } else if (selected.equals("Celcius")) {
                    adapter = ArrayAdapter.createFromResource(
                            MainActivity.this,
                            R.array.temp_fromCelcius_array,
                            android.R.layout.simple_spinner_item
                    );
                }
                else {
                    adapter = ArrayAdapter.createFromResource(
                            MainActivity.this,
                            R.array.temp_fromFahrenheit_array,
                            android.R.layout.simple_spinner_item
                    );
                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDestination.setAdapter(adapter);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}