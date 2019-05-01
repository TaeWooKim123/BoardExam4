package com.example1.user.boardexam;

class Board {
    String title;
    String date;
    String content;
    String filename;
    //int type;


    Board(){}
    Board(String title, String content, String filename){
        this.title=title;
        this.content=content;
        this.filename=filename;
    }

    /*Board(String title, String content, String date, int layouttype){
        this.title=title;
        this.content=content;
        this.date=date;
        this.type=layouttype;
    }*/
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public String getDate(){return date;}
    //public int getType(){return type;}
    public String getFilename(){ return filename;}

    public void setFilename(String filename){
        this.filename = filename;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*public void setType(int type) {
        this.type = type;
    }*/

    public void setContent(String content) {

        this.content = content;
    }

    public void setDate(String date) {

        this.date = date;
    }
}
