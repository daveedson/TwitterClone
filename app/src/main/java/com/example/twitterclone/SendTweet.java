package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SendTweet extends AppCompatActivity {
    private EditText edtSendTweet;
    private Button btnsendTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);


        edtSendTweet = findViewById(R.id.edtSendTweet);
        btnsendTweet = findViewById(R.id.btnSendTweet);
    }

    public void sendTweet(){

        ParseObject parseObject = new ParseObject("My Tweet");
        parseObject.put("tweet",edtSendTweet.getText().toString());
        parseObject.put("user", ParseUser.getCurrentUser().getUsername());

     final ProgressDialog pd  = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e==null) {
                    Toast.makeText(SendTweet.this,ParseUser.getCurrentUser().getUsername() + " 's tweet" +"("+edtSendTweet.getText().toString()+")"
                            +" is saved!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SendTweet.this,e.getMessage()+ "An Error Occuerrd", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }
        });
    }
}
