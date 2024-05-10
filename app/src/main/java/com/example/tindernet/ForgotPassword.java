package com.example.tindernet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ForgotPassword extends AppCompatActivity {
    private EditText editTextEmail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_page);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextTextEmailAddress2);

        Button resetPasswordButton = findViewById(R.id.button3);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = editTextEmail.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa tu correo electrónico.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                // El correo electrónico está registrado, enviar correo electrónico de restablecimiento
                                mAuth.sendPasswordResetEmail(email)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ForgotPassword.this, "Se ha enviado un correo electrónico de restablecimiento de contraseña.", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(ForgotPassword.this, "Error al enviar el correo electrónico de restablecimiento de contraseña.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // El correo electrónico no está registrado en Firebase Authentication
                                Toast.makeText(ForgotPassword.this, "No existe una cuenta asociada a este correo electrónico.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Error al verificar el correo electrónico
                            Toast.makeText(ForgotPassword.this, "Error al verificar el correo electrónico.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
