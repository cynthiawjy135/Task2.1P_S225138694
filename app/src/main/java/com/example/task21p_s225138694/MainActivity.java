package com.example.task21p_s225138694;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTxtValue;
    Button btnConvert;
    TextView txtViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Connect the element objects to the res object
        editTxtValue  = findViewById((R.id.edittxt_value));
        btnConvert    = findViewById((R.id.btn_convert));
        txtViewResult = findViewById(R.id.txtview_result);

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
        final ArrayAdapter<String> adapter_dest = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
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
                adapter_dest.clear();

                if (selected.equals("USD")) {
                    List<String> items = Arrays.asList(getResources().getStringArray(R.array.currency_array));
                    adapter_dest.addAll(items);
                } else if (selected.equals("mpg")) {
                    List<String> items = Arrays.asList(getResources().getStringArray(R.array.fuel_kmL_array));
                    adapter_dest.addAll(items);
                } else if (selected.equals("Gallon")) {
                    List<String> items = Arrays.asList(getResources().getStringArray(R.array.fuel_L_array));
                    adapter_dest.addAll(items);
                } else if (selected.equals("Nautical Mile")) {
                    List<String> items = Arrays.asList(getResources().getStringArray(R.array.fuel_km_array));
                    adapter_dest.addAll(items);
                } else if (selected.equals("Celcius")) {
                    List<String> items = Arrays.asList(getResources().getStringArray(R.array.temp_fromCelcius_array));
                    adapter_dest.addAll(items);
                }
                else {
                    //If user choose fahrenheit in the source spinner
                    List<String> items = Arrays.asList(getResources().getStringArray(R.array.temp_fromFahrenheit_array));
                    adapter_dest.addAll(items);
                }

                adapter_dest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDestination.setAdapter(adapter_dest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnConvert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("BUTTONS", "Convert !");

                String source = spinnerSource.getSelectedItem().toString();
                String dest = spinnerDestination.getSelectedItem().toString();
                String acceptedInput = editTxtValue.getText().toString().trim();
                double valueFromUser;

                if(acceptedInput.isEmpty()){
                    Toast.makeText(MainActivity.this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();
                } else{

                    try {
                        valueFromUser = Double.parseDouble(acceptedInput);

                        if ((source.equals("mpg") ||
                                source.equals("Nautical Mile") ||
                                source.equals("Gallon") ||
                                source.equals("USD")) && valueFromUser < 0) {
                            Toast.makeText(MainActivity.this, "Value cannot be negative!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(source.equals("USD") && dest.equals("AUD")){
                            double res = (valueFromUser * 1.55);
                            txtViewResult.setText(String.valueOf(res));
                        }else if(source.equals("USD") && dest.equals("EUR")){
                            double res = (valueFromUser * 0.92);
                            txtViewResult.setText(String.valueOf(res));
                        }else if(source.equals("USD") && dest.equals("JPY")){
                            double res = (valueFromUser * 148.50);
                            txtViewResult.setText(String.valueOf(res));
                        }else if(source.equals("mpg") && dest.equals("km/L")){
                            double res = (valueFromUser * 0.425);
                            txtViewResult.setText(String.valueOf(res));
                        }else if(source.equals("Gallon") && dest.equals("Liters")){
                            double res = (valueFromUser * 3.785);
                            txtViewResult.setText(String.valueOf(res));
                        }else if(source.equals("Nautical Mile") && dest.equals("Kilometers")){
                            double res = (valueFromUser * 1.852);
                            txtViewResult.setText(String.valueOf(res));
                        }else if(source.equals("Celcius") && dest.equals("Fahrenheit")){
                            double res = ((valueFromUser * 1.8) + 32);
                            txtViewResult.setText(String.valueOf(res));
                        }else if(source.equals("Celcius") && dest.equals("Kelvin")){
                            double res = (valueFromUser + 273.15);
                            txtViewResult.setText(String.valueOf(res));
                        } else{
                            //Fahrenheit to Celcius
                            double res = ((valueFromUser - 32) / 1.8 );
                            txtViewResult.setText(String.valueOf(res));
                        }
                    }catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Input must be number!", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });
    }
}