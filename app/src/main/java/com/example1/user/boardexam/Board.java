package com.example1.user.boardexam;

import java.io.Serializable;
import java.util.ArrayList;

class Board implements Serializable { //저거 implements intent에서 값 넘길때 board 객체 넘기려고 추가한거임.
    String title;
    String date;
    String content;
    String filename;
    String downloadURL;
    int type;
    ArrayList<String> downloadURLS;

    Board(){}
    /*Board(String title, String content, String filename, String downloadURL){
        this.title=title;
        this.content=content;
        this.filename=filename;
        this.downloadURL = downloadURL;
    }*/

    public String getTitle(){return title;}
    public String getContent(){return content;}
    public String getDate(){return date;}
    public int getType(){return type;}
    public String getFilename(){ return filename;}
    public String getDownloadURL() { return downloadURL; }
    public ArrayList<String> getDownloadURLS() {
        return downloadURLS;
    }
    public void setDownloadURLS(ArrayList<String> downloadURLS) {
        this.downloadURLS = downloadURLS;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }
    public void setDownloadURL(String downloadURL) { this.downloadURL = downloadURL; }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setContent(String content) {

        this.content = content;
    }
    public void setDate(String date) {

        this.date = date;
    }
}
