package com.example1.user.boardexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class Urlinfo extends AppCompatActivity {
    ArrayList<String> Urls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Board board = (Board)getIntent().getSerializableExtra("BOARD");
        Urls = (ArrayList<String>)getIntent().getSerializableExtra("list");
        board.setDownloadURLS(Urls);
        Information.getDatabase().child("taewoo").push().setValue(board);
    }
}


