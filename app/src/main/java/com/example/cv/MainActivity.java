package com.example.cv;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editNume, editPrenume, editData;
    Spinner spinnerStudii;
    CheckBox checkFeminin;
    RadioButton radioInformatica, radioTehnologii;
    Button buttonSalveaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Legarea elementelor XML
        editNume = findViewById(R.id.editNume);
        editPrenume = findViewById(R.id.editPrenume);
        editData = findViewById(R.id.editData);

        spinnerStudii = findViewById(R.id.spinnerStudii);

        checkFeminin = findViewById(R.id.checkFeminin);

        radioInformatica = findViewById(R.id.radioInformatica);
        radioTehnologii = findViewById(R.id.radioTehnologii);

        buttonSalveaza = findViewById(R.id.buttonSalveaza);

        // Lista studii
        String[] studii = {
                "Superioare",
                "Medii"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                studii
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerStudii.setAdapter(adapter);

        // DatePicker
        editData.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            int an = calendar.get(Calendar.YEAR);
            int luna = calendar.get(Calendar.MONTH);
            int zi = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            MainActivity.this,
                            (view, year, month, dayOfMonth) -> {

                                String data =
                                        String.format(
                                                "%02d.%02d.%04d",
                                                dayOfMonth,
                                                month + 1,
                                                year
                                        );

                                editData.setText(data);
                            },
                            an,
                            luna,
                            zi
                    );

            datePickerDialog.show();
        });

        // Salvare formular
        buttonSalveaza.setOnClickListener(v -> {

            String nume = editNume.getText().toString();
            String prenume = editPrenume.getText().toString();
            String data = editData.getText().toString();

            String studii = spinnerStudii.getSelectedItem().toString();

            String sex;

            if (checkFeminin.isChecked()) {
                sex = "Feminin";
            } else {
                // masculin implicit
                sex = "Masculin";
            }

            String specialitate;

            if (radioInformatica.isChecked()) {
                specialitate = "Informatica";
            } else if (radioTehnologii.isChecked()) {
                specialitate = "Tehnologii";
            } else {
                specialitate = "Neselectată";
            }

            String mesaj =
                    "Nume: " + nume +
                            "\nPrenume: " + prenume +
                            "\nData naștere: " + data +
                            "\nStudii: " + studii +
                            "\nSex: " + sex +
                            "\nSpecialitate: " + specialitate;

            Toast.makeText(
                    MainActivity.this,
                    mesaj,
                    Toast.LENGTH_LONG
            ).show();
        });
    }
}