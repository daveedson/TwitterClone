package com.example.twitterclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class  Users extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView ListView;
    private ArrayList<String> Users;
    private ArrayAdapter adapter;
    private  String followedUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        ListView = findViewById(R.id.ListView);
        Users = new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_checked,Users);

        ListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        ListView.setOnItemClickListener(this);


        try {
            ParseQuery<ParseUser> query = new ParseUser().getQuery();
            query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (objects.size()> 0 && e ==null){


                        if (ParseUser.getCurrentUser().getList("fanOf") != null){

                        for (ParseUser tUsers :objects) {
                            if (ParseUser.getCurrentUser().getList("fanOf").contains(Users)) {
                                ListView.setItemChecked(Users.indexOf(tUsers), true);
                            }

                            Users.add(tUsers.getUsername());
                        }



                        }
                        ListView.setAdapter(adapter);


                    }
                }
            });





        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

      /*  MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        */

      getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {



        switch (item.getItemId()){


            case R.id.logOutUser:

                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {

                        Intent intent = new Intent(Users.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
                break;

            case R.id.sendTweet:

                Intent intent = new Intent(Users.this,SendTweet.class);
                startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView checkedTextView =(CheckedTextView) view;

        if (checkedTextView.isChecked()){

            Toast.makeText(this,Users.get(position)+ " is followed", Toast.LENGTH_SHORT).show();
            ParseUser.getCurrentUser().add("fanOf",Users.get(position));
        }else {

            Toast.makeText(this, Users.get(position) + " is Unfollowed", Toast.LENGTH_SHORT).show();

            ParseUser.getCurrentUser().getList("fanOf").remove(Users.get(position));
            List currentusersfanOf = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",currentusersfanOf);
        }

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){

                    Toast.makeText(Users.this, "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
