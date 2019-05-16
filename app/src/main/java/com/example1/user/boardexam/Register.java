package com.example1.user.boardexam;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Register extends Activity {
    int PICK_IMAGE_MULTIPLE = 1;
    int PICK_IMAGE_SAMSUNG = 2;
    final int REQUEST_TAKE_ALBUM = -1;
    ViewPager pager;
    CustomAdapter adapter;
    ArrayList<Uri> uris;
    //PermissionCheck permissionCheck;
    private static final String TAG = "Register";
    ImageView image_preview;
    ImageButton button_choice;
    Button button_upload;
    EditText title;
    EditText content;
    String filename;
    Date now;
    static int flag = -1;
    private Uri filePath;
    String downloadUrl;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String photoLink;
    Board board;
    Object items;

    ArrayList<StorageReference> storageArray;
    public ArrayList<String> downloadURLS =new ArrayList<>();
    String[] downloadURLS2;
    String[] uriarray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        board = new Board();
        //image_preview = (ImageView)findViewById(R.id.preview);            //미리보기
        button_choice = (ImageButton) findViewById(R.id.camera_connect);  //사진선택
        button_upload = (Button) findViewById(R.id.bt_upload);            //이거 클릭시 데이터 베이스에 업로드
        title = (EditText) findViewById(R.id.EditText_title);
        content = (EditText) findViewById(R.id.EditText_content);
        pager = (ViewPager) findViewById(R.id.pager);

        if (user != null) {
            // do your stuff
        } else {
            signInAnonymously();
        }

        //버튼 클릭 이벤트(사진선택)
        button_choice.setOnClickListener(new View.OnClickListener() { //버튼 클릭했을시 사진첩 접근
            @Override
            public void onClick(View view) {
                //이미지를 선택
                int permissionCheck = ContextCompat.checkSelfPermission(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                } else {
                    getAlbum();
                }
            }
        });
        //완료버튼 클릭시 데이터베이스에 삽입!!!! uploadFile은 밑에 태우가 따로 정의함.
        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //업로드

                uriarray = objectToArray(items);
                //downloadURLS = new ArrayList<>();
                //downloadURLS2 = new String[uriarray.length];
                for (int i = 0; i < uriarray.length; i++) {
                    uploadFile(uriarray[i], i);//사진 데이터베이스에 집어넣기
                    Log.d("태우짱", downloadURLS.toString());
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); //다시 메인엑티비티로
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] == 0) {
                Toast.makeText(this, "권한 승인됨! 승인됐으니 다시 눌러라 색햐~ㅋ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "권한 거절", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getAlbum() {
        //앨범호출
        Intent intent = new Intent("android.intent.action.MULTIPLE_PICK");//("Intent.ACTION_GET_CONTENT);
        // intent.addCategory(Intent.CATEGORY_OPENABLE); // 생략해도 됨 - 삼성 갤럭시S3 테스트
        intent.setType("image/*"); // 생략하면 아래 검사 무의미 > else { ... 구문으로 넘어감
        // Check to see if it can be handled...
        PackageManager manager = getApplicationContext().getPackageManager();
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
        if (infos.size() > 0) {
            Log.e("FAT=", "삼성폰");
            // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 삼성폰 - 생략해도 됨
            // createChooser 실행해도 Intent가 1개 뿐이면 통과 > 이미지 리스트가 바로 열림
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SAMSUNG);
        } else {
            Log.e("FAT=", "일반폰");
            // intent.addCategory(Intent.CATEGORY_OPENABLE); // 없어도 됨 - 엘지 G2 테스트
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 일반폰 - 반드시 있어야 다중선택 가능
            intent.setAction(Intent.ACTION_PICK); // ACTION_GET_CONTENT 사용불가 - 엘지 G2 테스트
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
        }
    }

    public void uploadText() {                  //텍스트 부분을 따로 데이터베이스에 업로드하기 위해서 메소드 정의
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        board.setDownloadURL(downloadUrl);
        board.title = title.getText().toString();
        board.content = content.getText().toString();
        board.filename = filename;
        //Board board = new Board(title.getText().toString(), content.getText().toString(), filename, downloadUrl.toString());    //title하고 content를 받아와서 board클래스에 집어넣음.
        database.child("message").push().setValue(board);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 출처 : http://coder-jeff.blogspot.kr/2016/05/how-to-pick-multiple-files-from.html
        // 수정 : NullPointerException 해결 - (1) 작업취소 (2) 삼성폰 확인
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        // if (data.getAction().equals("android.intent.action.MULTIPLE_PICK")) { // NullPointerException
        if (requestCode == PICK_IMAGE_SAMSUNG) {
            final Bundle extras = data.getExtras();
            int count = extras.getInt("selectedCount");
            items = extras.getStringArrayList("selectedItems");
            // do somthing
            adapter = new CustomAdapter(getLayoutInflater(), items);
            pager.setAdapter(adapter);
            Log.e("FAT=", "삼성폰태우 : " + items.toString());
        } else {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                // do somthing
                Log.e("FAT=", "일반폰/단일 : " + uri.toString());
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        uris = new ArrayList<>();   //여기에 Uri들이 담긴다!!
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            Uri uri = item.getUri();
                            Log.e("FAT=", "일반폰/다중 : " + uri.toString());
                            uris.add(uri);
                        }
                        // Do someting
                        //Intent intent = new Intent(this, ImagesView.class);
                        //intent.putExtra("list", uris);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String[] objectToArray(Object object) {  //요것은 받아온 object를 uri로 만들기 위해 태우가 삽입
        String sUris = object.toString();
        sUris = sUris.substring(1, sUris.length() - 1);
        return sUris.split(", ");
    }

    //upload the file. 데이터베이스에 삽입 하는 부분임.
    private void uploadFile(String filePath, int i) {
        Log.d("파일패스", filePath);
        //downloadURLS = new ArrayList<>();
        //업로드할 파일이 있으면 수행
        if (filePath != null) { //즉, 기존에 filePath부분을 다중업로드로 바꾸니까 이 부분도 items로 바꿔준다!!1 items에는 당연히 uri뭉치들이 있고...!!!
            //String[] uriarray = objectToArray(items);
            //downloadURLS = new String[uriarray.length];
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();
            //storage
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            //Unique한 파일명을 만들자.
            //for (int i = 0; i < uriarray.length; i++) {
            //   Log.d("for문 :", uriarray[i].toString());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            now = new Date();
            filename = formatter.format(now) + i + ".png";
            Log.d("태우의 filename", filename);
            //storage 주소와 폴더 파일명을 지정해 준다.
            final StorageReference storageRef = storage.getReferenceFromUrl("gs://testfirebase-b3ec0.appspot.com").child("images/" + filename);
            //storageArray.add(storageRef);
            //storageArray.add(storageRef); //일단 이렇게 넣어주고!!
            //Uri filepath = Uri.parse(uriarray[i]);
            storageRef.putFile(Uri.parse(filePath))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl()
//                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            downloadUrl = uri.toString();
//                                            downloadURLS.add(downloadUrl);
//                                            //Log.d("보드의 : ", downloadUrl.toString());
//                                            //database.child("message").push().setValue(board);
//                                        }
//                                    })
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()) {
                                                ++flag;
                                                Uri downloadUri = task.getResult();
                                                board.title = title.getText().toString();
                                                board.content = content.getText().toString();
                                                board.filename = filename;
                                                board.setDownloadURL(downloadUri.toString());
                                                //Log.d("보드의 : ", downloadUri.toString());
                                                downloadURLS.add(downloadUri.toString());
                                                //Log.d("태우짱", downloadURLS.toString());
                                                board.setDownloadURLS(downloadURLS);
                                                if(uriarray.length-1 == flag){
                                                    Information.getDatabase().child("message").push().setValue(board);
                                                    flag = -1;
                                                }
//                                                Intent intent = new Intent(Register.this, Urlinfo.class);
//                                                intent.putStringArrayListExtra("list", downloadURLS);
//                                                intent.putExtra("BOARD", board);
//                                                startActivity(intent);
//                                                if(flag == true)
//                                                    Information.getDatabase().child("taewoo").push().setValue(board);
                                            }
                                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//        board.title = title.getText().toString();
//        board.content = content.getText().toString();
//        board.filename = filename;
//        board.setDownloadURL(downloadUrl);
//        //board.setDownloadURLS(downloadURLS);
//        database.child("message").push().setValue(board);
                    });
        }

    }
    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "signInAnonymously:FAILURE", exception);
                    }
                });
    }
}