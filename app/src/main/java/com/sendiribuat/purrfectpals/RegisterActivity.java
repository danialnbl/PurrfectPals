package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText regNameET, regUnameET, regEmailET, regPassET;
    Button regBtn;
    FirebaseAuth mAuth;
    FirebaseDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        db = new FirebaseDbHelper(this);
        regNameET = findViewById(R.id.regNameET);
        regUnameET = findViewById(R.id.regUnameET);
        regEmailET = findViewById(R.id.regEmailET);
        regPassET = findViewById(R.id.regPassET);
        regBtn = findViewById(R.id.regBtn);

        regBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = regNameET.getText().toString().trim();
        String username = regUnameET.getText().toString().trim();
        String email = regEmailET.getText().toString().trim();
        String password = regPassET.getText().toString().trim();

        if(!name.isEmpty() && !username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    User user = new User(name, username, email);
                    if(db.insertUser(user, userId)) {
                        mAuth.signOut();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                }
                else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(RegisterActivity.this, "This email is already registered.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "All text fields are incomplete!", Toast.LENGTH_SHORT).show();
        }
    }

    public void openLogin(View view) {
        Intent next = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(next);
        finish();
    }
}