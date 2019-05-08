package com.example1.user.boardexam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Roominfo extends AppCompatActivity {
    Context context;
    private ArrayList<Board> arrayList;
    String title;
    String content;
    String downloadURL;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roominfo);

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        ImageView imageView = (ImageView) findViewById(R.id.imageview);

        title = (String)getIntent().getSerializableExtra("TITLE");
        content = (String)getIntent().getSerializableExtra("CONTENT");
        arrayList = (ArrayList<Board>) getIntent().getSerializableExtra("LIST");
        textView1.setText(title);
        textView2.setText(content);
        for(Board board : arrayList){
            if(board.title.equals(this.title)){
                //position = arrayList.get
                this.downloadURL = board.downloadURL;
                //textView1.setText(board.getTitle());
                Log.d("filename : ", this.downloadURL);
            }
        }
        Picasso.with(context).load(downloadURL).fit().centerInside().into(imageView);

        //arrayList = (ArrayList<Board>) getIntent().getSerializableExtra("OBJECT");
        //Intent intent = getIntent();
        //View board = (View)intent.getSerializableExtra("OBJECT");
        //System.out.printf("인텐트의 제목 제발 : %s", board.getText);

        /*for(Board board : arrayList){
            if(board.title.equals(match))
                position = arrayList.indexOf(board);
                textView1.setText(board.getTitle());
        }*/

        //textView1.setText(board.getTitle());
        //EditText idText = (EditText)findViewById(R.id.idText);
        //EditText passwordText = (EditText)findViewById(R.id.passwordText);
        //Button loginButton = (Button)findViewById(R.id.loginButton);
        //TextView registerButton = (TextView)findViewById(R.id.registerButton);
    }
}