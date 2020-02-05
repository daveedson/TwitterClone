package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity  implements View.OnClickListener{

    private EditText edtSpEmail,
            edtSpUsername
            ,edtSpPassword;
    private Button BtnSignUp;
    private TextView txtLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        edtSpEmail = findViewById(R.id.edtSpEmail);
        edtSpUsername = findViewById(R.id.edtLgUsername);
        edtSpPassword = findViewById(R.id.edtlgPassword);

        BtnSignUp = findViewById(R.id.btnSignUp);
        txtLogin = findViewById(R.id.txtLogin);

        BtnSignUp.setOnClickListener(this);
        txtLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
            TransitionToLoginActivity();
        }


    }
    private void TransitionToLoginActivity() {

        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSignUp:


                    edtSpUsername.setError(null);
                    edtSpPassword.setError(null);

                    if (edtSpUsername.getText().toString().equals("") || edtSpEmail.getText().toString().equals("")
                            ||edtSpPassword.getText().toString().equals("")){

                        Toast.makeText(this, "All Fields Required !", Toast.LENGTH_SHORT).show();
                    }else {

                        final ParseUser user = new ParseUser();
                        user.setUsername(edtSpUsername.getText().toString());
                        user.setEmail(edtSpEmail.getText().toString());
                        user.setPassword(edtSpPassword.getText().toString());

                        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this,R.style.alertDialog);
                        progressDialog.setMessage("Signing UP " + edtSpUsername.getText().toString());
                        progressDialog.show();

                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e==null){
                                    Toast.makeText(SignUp.this,user.getUsername() +" is Signed Up", Toast.LENGTH_SHORT).show();

                                    TransitionToLoginActivity();
                                }else {
                                    Toast.makeText(SignUp.this, " Error Occured" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                    break;
            case R.id.txtLogin:
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
                finish();




        }



    }
    public void layoutTapped(View view){

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }catch(Exception e){

            e.printStackTrace();
        }


    }
}
