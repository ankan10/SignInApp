package com.example.signup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import  android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class RegisterActivity extends AppCompatActivity {
    EditText regname, regpass, regemail;
    Button regbtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regname= findViewById(R.id.usrnm);
        regpass= findViewById(R.id.edt1);
        regemail=findViewById(R.id.edt2);
        regbtn=findViewById(R.id.btn);
        auth= FirebaseAuth.getInstance();
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //Upload data to database
                    String user_email= regemail.getText().toString().trim();
                    String user_password= regpass.getText().toString().trim();
                    auth.createUserWithEmailAndPassword(user_email, user_password ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                Toast.makeText(RegisterActivity.this,"Registration Successful", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(RegisterActivity.this,"Registration failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private Boolean validate()
    {
        boolean result=false;
        String name=regname.getText().toString();
        String password=regpass.getText().toString();
        String email=regemail.getText().toString();
        if (name.isEmpty() || password.isEmpty()  || email.isEmpty()){
            Toast.makeText(this,"Please Enter All Details",Toast.LENGTH_LONG).show();
        }

        else {
            result=true;
        }
        return result;


    }
}
