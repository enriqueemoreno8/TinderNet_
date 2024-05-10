package com.example.tindernet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page2);

        //Instanciación de FireBase
        mAuth = FirebaseAuth.getInstance();
        // Obtención de referencias a los EditTexts
        editTextEmail = findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = findViewById(R.id.editTextTextPassword);

        Button loginButton = findViewById(R.id.button3);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtención de los datos de los EditTexts
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // Llamada al método signInWithEmailAndPassword con los datos obtenidos
                signInWithEmailAndPassword(email, password);
            }
        });

        TextView textViewForgotPassword = findViewById(R.id.textView8);

        textViewForgotPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Cambiar el color del texto a gris oscuro cuando se toca el TextView
                        textViewForgotPassword.setTextColor(getResources().getColor(R.color.light_blue));
                        break;
                }
                // Devolver false para permitir que otros Listeners manejen el evento
                return false;
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar una nueva actividad que muestre la página de restablecimiento de contraseña
                Intent intent = new Intent(LoginActivity2.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
    }

    private void signInWithEmailAndPassword(String email, String password) {
        // Intenta iniciar sesión con el correo electrónico y la contraseña proporcionados
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                // Si el correo electrónico ha sido verificado, actualiza la UI
                                updateUI2(user);
                            } else {
                                // Si el correo electrónico no ha sido verificado, muestra un mensaje al usuario
                                mAuth.signOut(); // Cerrar sesión porque el correo electrónico no ha sido verificado
                                Toast.makeText(LoginActivity2.this, "Por favor, verifica tu correo electrónico para iniciar sesión.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            // Si el inicio de sesión falla, muestra un mensaje al usuario
                            Toast.makeText(LoginActivity2.this, "Error al iniciar sesión. Por favor, asegúrate de que los datos sean válidos.", Toast.LENGTH_LONG).show();
                            updateUI2(null);
                        }
                    }
                });
    }

    private void updateUI2(FirebaseUser user) {
        // Actualiza la interfaz de usuario según el estado de autenticación
        if (user != null) {
            // Si el usuario ha iniciado sesión con éxito, redirige a la actividad principal (o cualquier otra actividad)
            Intent intent = new Intent(LoginActivity2.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finaliza LoginActivity2 para evitar que el usuario retroceda a esta actividad después de iniciar sesión
        }
    }

}
