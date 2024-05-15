package com.example.tindernet;

import android.content.Intent;
import android.content.IntentSender;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.credentials.CustomCredential;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.security.SecureRandom;
import java.util.Base64;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private EditText editTextName, editTextEmail, editTextPassword;
    private SignInClient oneTapClient;
    private BeginSignInRequest signUpRequest;
    private GoogleSignInClient mGoogleSignInClient;
    private Identity identity;
    private GetGoogleIdOption googleIdOption;

    private static final int REQ_ONE_TAP = 2;  // Puede ser cualquier número entero único para la actividad.
    /*private boolean showOneTapUI = true;
    private Identity oneTapClient; // Agrega esta línea para declarar la variable oneTapClient*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        //initApp();
        setupSwitch();

        //Instanciación de FireBase
        mAuth = FirebaseAuth.getInstance();

        // Inicialización de Identity
        /*identity = Identity.getSharedInstance(this);*/

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

        /*Button buttonSignInWithGoogle = findViewById(R.id.button4);
        buttonSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llama a la función para iniciar sesión con Google
                beginGoogleSignIn();
            }
        });*/

        // Crear una instancia de la solicitud de acceso con Google
        /*googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId("705075391017-o8jd9ciit2ao91oqp395a7p50vmkf0n9.apps.googleusercontent.com")
                .setAutoSelectEnabled(true)
                .setNonce(generateUniqueNonce()) // Reemplaza "YourNonceStringHere" con tu nonce string
                .build();

        // Configurar el flujo de inicio de sesión con Google
        setupGoogleSignInFlow();*/


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

    /*public String generateUniqueNonce() {
        // Generar un valor aleatorio seguro
        SecureRandom random = new SecureRandom();
        byte[] nonceBytes = new byte[16];
        random.nextBytes(nonceBytes);

        // Convertir el valor aleatorio a una cadena Base64
        //Base64 es un método de codificación que convierte datos binarios en una representación de texto ASCII utilizando un conjunto de caracteres legibles (representar datos binarios).
        String nonce = Base64.getUrlEncoder().encodeToString(nonceBytes);

        return nonce;
    }*/

    /*private void setupGoogleSignInFlow() {
        // Crear una solicitud de credencial con la opción de Google Id
        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addGetGoogleIdTokenCredentialRequestOptions(googleIdOption)
                .build();

        // Lanzar la solicitud de credencial en un hilo de fondo
        Task<GetCredentialResponse> task = identity.getCredential(request);
        task.addOnSuccessListener(this::handleSignIn)
                .addOnFailureListener(e -> handleFailure(e));
    }*/

    /*private void handleSignIn(GetCredentialResponse result) {
        // Manejar la credencial devuelta exitosamente
        CustomCredential credential = result.getCredential();

        if (credential instanceof GoogleIdTokenCredential) {
            // Convertir la credencial a GoogleIdTokenCredential
            GoogleIdTokenCredential googleCredential = GoogleIdTokenCredential.createFrom(credential.getData());
            // Extraer el ID de GoogleIdTokenCredential y autenticar en tu servidor
            String googleIdToken = googleCredential.getId();
            // Aquí puedes continuar con la autenticación en tu servidor
        } else {
            // Manejar otros tipos de credenciales
            Log.e(TAG, "Unexpected type of credential");
        }
    }*/

    /*private void handleFailure(Exception e) {
        // Manejar cualquier error de solicitud de credencial
        Log.e(TAG, "Error fetching credential: " + e.getMessage());
    }*/

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

    /*private void initApp() {
        oneTapClient = Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder().setSupported(false).build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId("705075391017-mted1o913v1pe6t4vd658srn1jqamdl8.apps.googleusercontent.com")
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .setAutoSelectEnabled(false)
                .build();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .requestIdToken("705075391017-o8jd9ciit2ao91oqp395a7p50vmkf0n9.apps.googleusercontent.com")
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }*/

    /*private void beginGoogleSignIn() {
        oneTapClient.beginSignIn(signUpRequest)
                .addOnSuccessListener(new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult beginSignInResult) {
                        try {
                            startIntentSenderForResult(
                                    beginSignInResult.getPendingIntent().getIntentSender(), REQ_ONE_TAP, null, 0, 0, 0, null);
                        } catch (IntentSender.SendIntentException e) {
                            Toast.makeText(LoginActivity.this, "Error: No se pudo iniciar el proceso de inicio de sesión con Google. PendingIntent", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(LoginActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, REQ_ONE_TAP);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ONE_TAP) {
            try {
                handleOneTapSignIn(data);
            } catch (ApiException e) {
                Log.e(TAG, "Error en handleOneTapSignIn: " + e.getMessage());
                Toast.makeText(this, "Error en handleOneTapSignIn: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleOneTapSignIn(Intent data) throws ApiException {
        SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
        String token = credential.getGoogleIdToken();
        if (token != null) {
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(token, null);
            mAuth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                updateUI(null);
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "No se pudo obtener el token de identificación de Google.", Toast.LENGTH_SHORT).show();
        }
    }*/


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
