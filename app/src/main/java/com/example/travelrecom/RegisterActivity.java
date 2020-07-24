package com.example.travelrecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //req stuff
        final EditText RegisterEmailID =(EditText) findViewById(R.id.Register_Email);
        final EditText RegisterPassword =(EditText) findViewById(R.id.Register_Password);
        final EditText RegisterRePassword =(EditText) findViewById(R.id.Register_rePassword);
        final EditText RegisterUsername =(EditText) findViewById(R.id.Register_Username);
        TextView RegisterLogin =(TextView) findViewById(R.id.Register_Login);
        Button RegisterBtn =(Button) findViewById(R.id.Register_btn);

        fAuth = FirebaseAuth.getInstance();         //we get the current instance of the database

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = RegisterEmailID.getText().toString().trim();   //if the user uses space after entering the mailID, we get an error(to avoid this error we use trim)
                String password = RegisterPassword.getText().toString().trim();
                String passwordre = RegisterRePassword.getText().toString().trim();
                String username = RegisterUsername.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    RegisterEmailID.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    RegisterPassword.setError("Password is empty");
                    return;
                }
                if(password.length()<5){
                    RegisterPassword.setError("weak password");
                    return;
                }
                if(password.equals(passwordre) == false){
                    RegisterRePassword.setError("Both password dont match");
                    return;
                }
                if(TextUtils.isEmpty(username)){
                    RegisterUsername.setError("Username is empty");
                }

                //firebase part
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
