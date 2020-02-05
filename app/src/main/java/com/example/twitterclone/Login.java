package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Login extends AppCompatActivity implements View.OnClickListener {
private EditText edtLgUsername,edtLgpassword;
private Button btnLogin;
    private TextView txtSignUpHere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLgUsername = findViewById(R.id.edtLgUsername);
        edtLgpassword = findViewById(R.id.edtlgPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUpHere = findViewById(R.id.txtSignUpHere);

        btnLogin.setOnClickListener(this);
        txtSignUpHere.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnLogin:

                if (edtLgUsername.getText().toString().equals("") || edtLgpassword.getText().toString().equals("")){

                    Toast.makeText(this, "All Fields Required !", Toast.LENGTH_SHORT).show();
                }else {

                    ParseUser.logInInBackground(edtLgUsername.getText().toString(), edtLgpassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {

                            if (user != null && e==null){
                                Toast.makeText(Login.this, user.getUsername() + " is logged in Successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Login.this,Users.class);
                                startActivity(intent);

                            }else {
                                Toast.makeText(Login.this,e.getMessage() +"Error Occurred", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }break;
            case R.id.txtSignUpHere:
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
                break;
        }

    }
    public void Layout(View view){

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }catch(Exception e){

            e.printStackTrace();
        }



    }
}
