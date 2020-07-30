package com.example.signup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class MainActivity extends AppCompatActivity {
//static initialized
ProgressDialog progressDialog;

    int counter=5;
    EditText editText;
    Button btn;
    TextView text, name, pass, info;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.txtt4);
        name = findViewById(R.id.usrnm);
        pass = findViewById(R.id.pass);
        mAuth = FirebaseAuth.getInstance();
        info =findViewById(R.id.txtrem);
        progressDialog=new ProgressDialog(this);
        btn= findViewById(R.id.btn1);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(), pass.getText().toString());
            }
        });

    }


    private void validate(final String userName, String userPassword) {
        progressDialog.setMessage("You can Click Here");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "LogIN Success", Toast.LENGTH_LONG).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    info.setText("No of attempts: 5");
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, "LogIn Failed", Toast.LENGTH_LONG).show();
                    counter--;
                    info.setText("No of atempts remaining:" + counter);
                    progressDialog.dismiss();
                    if (counter == 0) {
                        btn.setEnabled(false);
                    }
                }
            }
        });
    }
}

