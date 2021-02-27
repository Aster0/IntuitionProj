package com.example.intuitionproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.intuitionproject.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        binding.btnLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.newEmail.getText().toString().trim();
                String password = binding.newPassword.getText().toString().trim();
                String confirmPassword  =binding.newConfirmPassword.getText().toString().trim();
                registerUser(email,password, confirmPassword);
            }
        });
    }

    private void registerUser(String email, String password, String confirmPassword) {
        if (email.isEmpty()) {
            binding.newEmail.setError("Email required");
            binding.newEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            binding.newPassword.setError("Password required");
            binding.newPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            binding.newPassword.setError("Password must have more than 6 characters");
            binding.newPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            binding.newConfirmPassword.setError("Passwords do not match!");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.newEmail.setError("Please enter a valid email address");
            binding.newEmail.requestFocus();
            return;

        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    binding.newEmail.setError("Email is already taken");
                }
                else {
                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}