package com.example.cv;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ImageView imageProfile;
    EditText editNume, editPrenume, editData;
    Spinner spinnerStudii;
    CheckBox checkFeminin, checkMasculin;
    RadioButton radioInformatica, radioTehnologii;
    Button buttonSalveaza;

    // Переменная для хранения ссылки на выбранное фото
    private Uri selectedImageUri = null;

    // Лаунчер для выбора файла из галереи
    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов XML
        imageProfile = findViewById(R.id.imageProfile);
        editNume = findViewById(R.id.editNume);
        editPrenume = findViewById(R.id.editPrenume);
        editData = findViewById(R.id.editData);

        spinnerStudii = findViewById(R.id.spinnerStudii);

        checkFeminin = findViewById(R.id.checkFeminin);
        checkMasculin = findViewById(R.id.checkMasculin);

        radioInformatica = findViewById(R.id.radioInformatica);
        radioTehnologii = findViewById(R.id.radioTehnologii);

        buttonSalveaza = findViewById(R.id.buttonSalveaza);

        // Регистрация лаунчера для открытия галереи
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedImageUri = uri;
                        imageProfile.setImageURI(selectedImageUri);
                    }
                }
        );

        // При клике на иконку профиля открывается диалоговое окно
        imageProfile.setOnClickListener(v -> showImageOptionsDialog());

        // Список категорий обучения (Lista studii)
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

        // Выбор даты (DatePicker)
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

        // Сохранение формы
        buttonSalveaza.setOnClickListener(v -> {

            String nume = editNume.getText().toString();
            String prenume = editPrenume.getText().toString();
            String data = editData.getText().toString();

            String selectedStudii = spinnerStudii.getSelectedItem().toString();

            String sex;
            if (checkFeminin.isChecked()) {
                sex = "Feminin";
            } else if (checkMasculin.isChecked()) {
                sex = "Masculin";
            } else {
                sex = "Neselectat";
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
                            "\nStudii: " + selectedStudii +
                            "\nSex: " + sex +
                            "\nSpecialitate: " + specialitate;

            Toast.makeText(
                    MainActivity.this,
                    mesaj,
                    Toast.LENGTH_LONG
            ).show();
        });
    }

    // Диалог с выбором действия: Загрузка/Изменение или Удаление фото
    private void showImageOptionsDialog() {
        String[] options;

        if (selectedImageUri != null) {
            options = new String[]{"Schimbă imaginea", "Șterge imaginea"};
        } else {
            options = new String[]{"Alege imaginea"};
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Imagine de profil");
        builder.setItems(options, (dialog, which) -> {
            if (options[which].equals("Alege imaginea") || options[which].equals("Schimbă imaginea")) {
                // Открыть галерею для выбора/замены
                imagePickerLauncher.launch("image/*");
            } else if (options[which].equals("Șterge imaginea")) {
                // Удалить выбранную картинку и вернуть иконку по умолчанию
                selectedImageUri = null;
                imageProfile.setImageResource(android.R.drawable.ic_menu_camera);
                Toast.makeText(this, "Imaginea a fost ștearsă", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Anulează", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}