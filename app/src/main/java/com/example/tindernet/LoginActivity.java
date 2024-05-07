package com.example.tindernet;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private EditText editTextName, editTextEmail, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        setupSwitch();

        //Instanciación de FireBase
        mAuth = FirebaseAuth.getInstance();
        // Obtención de referencias a los EditTexts
        editTextName = findViewById(R.id.editTextText3);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = findViewById(R.id.editTextTextPassword);

        Button buttonCreateAccount = findViewById(R.id.button3);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtención de los datos de los EditTexts
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // Llamada al método createUserWithEmailAndPassword con los datos obtenidos
                createUserWithEmailAndPassword(email, password);
            }
        });
    }

    protected void setupSwitch() {
        //Cambiar el color del switch al pulsarlo y el texto de cliente a empresa y el texto de nombre a nombre empresa
        Switch switchButton = findViewById(R.id.switch2);
        TextView switchText = findViewById(R.id.textView3);
        TextView switchNombre = findViewById(R.id.textView4);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchButton.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    switchText.setText("Empresa");
                    switchNombre.setText("Nombre Empresa");
                } else {
                    switchButton.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_blue)));
                    switchText.setText("Cliente");
                    switchNombre.setText("Nombre");
                }
            }
        });
    }

    private void createUserWithEmailAndPassword(String email, String password) {
        // Verificar si la contraseña cumple con los requisitos
        if (password.length() < 6 || password.length() > 20) {
            Toast.makeText(LoginActivity.this, "La contraseña debe tener entre 6 y 20 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el correo electrónico es válido
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "El formato del correo electrónico no es válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el correo electrónico ya está registrado en Firebase Authentication
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    SignInMethodQueryResult result = task.getResult();
                    assert result != null;
                    if (result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                        // El correo electrónico ya está registrado
                        Toast.makeText(LoginActivity.this, "El correo electrónico ya está registrado. Por favor, inicia sesión.", Toast.LENGTH_SHORT).show();
                    } else {
                        // El correo electrónico no está registrado, procede con la creación de usuario
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Creación de usuario exitosa
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();

                                            // Verificar si el correo electrónico está verificado
                                            if (user != null && !user.isEmailVerified()) {
                                                // Enviar correo electrónico de verificación
                                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(LoginActivity.this, "Se ha enviado un correo electrónico de verificación. Por favor, verifica tu cuenta.", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Log.e(TAG, "sendEmailVerification", task.getException());
                                                            Toast.makeText(LoginActivity.this, "No se pudo enviar el correo electrónico de verificación.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }

                                            updateUI(user);
                                        } else {
                                            // Si la creación de usuario falla, muestra un mensaje al usuario
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(LoginActivity.this, "Error al crear la cuenta. Por favor, inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
                                            updateUI(null);
                                        }
                                    }
                                });
                    }
                } else {
                    // Error al verificar el correo electrónico
                    Log.e(TAG, "fetchSignInMethodsForEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Error al verificar el correo electrónico.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        // Aquí puedes actualizar la interfaz de usuario según el estado de autenticación
        // Por ejemplo, puedes redirigir al usuario a otra actividad después de que haya iniciado sesión correctamente
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class); //Cambiarlo por la página siguiente
            startActivity(intent);
            finish();
        }
    }

        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                currentUser.reload();
            }
        }

}
