package com.example1.user.boardexam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
    ViewPager pager;
    UrlAdapter adapter2;
    Context context;
    private ArrayList<Board> arrayList;
    String title;
    String content;
    String downloadURL;
    ArrayList<String> downloadurls = new ArrayList<>();
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roominfo);

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        //ImageView imageView = (ImageView) findViewById(R.id.imageview);
        pager = (ViewPager) findViewById(R.id.pager);

        title = (String)getIntent().getSerializableExtra("TITLE");
        content = (String)getIntent().getSerializableExtra("CONTENT");
        arrayList = (ArrayList<Board>) getIntent().getSerializableExtra("LIST");
        textView1.setText(title);
        textView2.setText(content);
        for(Board board : arrayList){
            if(board.title.equals(this.title)){

                this.downloadURL = board.downloadURL;
                this.downloadurls = board.downloadURLS;
                Log.d("filename : ", this.downloadURL);
            }
        }
        //Picasso.with(context).load(downloadURL).fit().centerInside().into(imageView);

        adapter2 = new UrlAdapter(getLayoutInflater(), downloadurls, context);
        pager.setAdapter(adapter2);
    }
}