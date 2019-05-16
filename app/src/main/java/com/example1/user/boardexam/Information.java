package com.example1.user.boardexam;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;



import java.text.SimpleDateFormat;
import java.util.Date;

public class Information {
    private Information() {
    }

    public final static String CHAT_RERALTION = "chat_relation";
    public final static String CHAT_INFOMAION = "chat_contents";

    private static final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://testfirebase-b3ec0.appspot.com");
    private static String USER_NAME;
    private static String USER_ID;




    public static String getUserName() {
        return USER_NAME;
    }

    public static void setUserName(String userName) {
        USER_NAME = userName;
    }

    public static String getUserId() {
        return USER_ID;
    }

    public static void setUserId(String userId) {
        USER_ID = userId;
    }

    public static DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getDatabase(String ref) {
        return FirebaseDatabase.getInstance().getReference(ref);
    }

    // 두개의 스트링을 합치기
    public static String integrate(String hostName, String username) {
        return hostName.compareTo(username) > 0 ? hostName + ", " + username : username + ", " + hostName;
    }

    public static StorageReference getStorageRef() {
        return storageRef;
    }


    public static StorageReference getStorageRef(String child) {
        return storageRef.child(child);
    }

    public static String timeStamp(String user, String type) {
        return user + "_" + timeStamp() + type;
    }

    public static String timeStamp() {
        return new SimpleDateFormat("yyyyMMHH_mmss").format(new Date());
    }

    public static String chatTimeStamp() {
        return new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date(System.currentTimeMillis()));
    }
}
