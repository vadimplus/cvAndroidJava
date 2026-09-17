package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etConfirmPassword;
    private TextView tvError;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Инициализация элементов
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvError = findViewById(R.id.tvError);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                // 1. Проверка email
                if (!isEmailValid(email)) {
                    showError("Adresa de e-mail este invalidă (ex: exemplu@gmail.com, @mail.com, @mail.ru)");
                    return;
                }

                // 2. Проверка сложности пароля
                if (!isPasswordValid(password)) {
                    showError("Parola trebuie să aibă cel puțin 8 caractere, litere mari, mici și cifre");
                    return;
                }

                // 3. Проверка совпадения паролей
                if (!password.equals(confirmPassword)) {
                    showError("Parolele nu coincid");
                    return;
                }

                // Успешный вход — скрываем ошибку и переходим в MainActivity
                tvError.setVisibility(View.GONE);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Метод проверки формата e-mail
    private boolean isEmailValid(String email) {
        if (email.isEmpty()) return false;
        return email.endsWith("@gmail.com") ||
                email.endsWith("@mail.com") ||
                email.endsWith("@mail.ru");
    }

    // Метод проверки сложности пароля (мин. 8 символов, заглавные/строчные буквы и цифры)
    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isLowerCase(c)) hasLowercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
        }

        return hasUppercase && hasLowercase && hasDigit;
    }

    // Метод вывода ошибки на экран
    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }
}