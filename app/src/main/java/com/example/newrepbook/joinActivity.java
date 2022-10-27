package com.example.newrepbook;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class joinActivity extends AppCompatActivity {
    private static final String TAG = "joinActivity";
    private ImageView profileImageView;
    private FirebaseAuth mAuth;
    private String profilePath;
    private RelativeLayout loading;
    EditText mnick, mphon;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        mAuth = FirebaseAuth.getInstance();

        loading = findViewById(R.id.loading);
        profileImageView = findViewById(R.id.join_image);
        profileImageView.setOnClickListener(onClickListener);
        findViewById(R.id.btn_register).setOnClickListener(onClickListener);
        findViewById(R.id.imageModify).setOnClickListener(onClickListener);
        findViewById(R.id.videoModify).setOnClickListener(onClickListener);
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    // 뒤로가기 버튼을 누르면 프로세스 종료
//    public void onBackPressed() {
//        super.onBackPressed();
//        moveTaskToBack(true);
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(1);
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
          case 0 :
              if (resultCode == Activity.RESULT_OK) {
                  profilePath = data.getStringExtra("profilePath");
                  Glide.with(this).load(profilePath).centerCrop().override(500).into(profileImageView);
              }
              break;
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_register: // 회원가입 버튼을 누르면
                    RealtimeDatabaseUpload();
                    break;
                case R.id.btn_login: // 회원가입 페이지의 로그인 버튼을 누르면
                    startActivity(loginActivity.class);
                    break;
                case R.id.imageModify: // 카메라 버튼
                    startActivity(CameraActivity.class);
                    break;
                case R.id.videoModify:
                    startActivity(GalleryActivity.class); // 갤러리 버튼
                    break;
            }
        }
    };

    private void RealtimeDatabaseUpload() {
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.password2)).getText().toString();
        mnick = findViewById(R.id.nick);
        mphon = findViewById(R.id.phon);

        if(email.length()>0 && password.length()>0 && passwordCheck.length()>0) {
            loading.setVisibility(View.VISIBLE);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference storageRef = storage.getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            StorageReference mountainImagesRef = storageRef.child("images/"+ user.getUid() +"/"+user.getUid()+".jpg");


            try{
                InputStream stream = new FileInputStream(new File(profilePath));
                UploadTask uploadTask = mountainImagesRef.putStream(stream);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return mountainImagesRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            Log.e("성공", "성공: " + downloadUri);
                            if (password.equals(passwordCheck)) {
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(joinActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    //가입 성공시
                                                    if (task.isSuccessful()) {
                                                        final ProgressDialog mDialog = new ProgressDialog(joinActivity.this);
                                                        mDialog.dismiss();

                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        String email = user.getEmail();
                                                        String uid = user.getUid();
                                                        String nick = mnick.getText().toString().trim();
                                                        String phon = mphon.getText().toString().trim();

                                                        //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                                        HashMap<Object, String> hashMap = new HashMap<>();

                                                        hashMap.put("uid", uid);
                                                        hashMap.put("email", email);
                                                        hashMap.put("nick", nick);
                                                        hashMap.put("phon", phon);
                                                        hashMap.put("image", downloadUri.toString());

                                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                        DatabaseReference reference = database.getReference("Users");
                                                        reference.child(uid).setValue(hashMap);


                                                        //가입이 이루어져을시 가입 화면을 빠져나감.
                                                        Intent intent = new Intent(joinActivity.this, loginActivity.class);
                                                        startActivity(intent);
                                                        loading.setVisibility(View.GONE);
                                                        finish();
                                                        Toast.makeText(joinActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                                        startToast("회원가입을 성공했습니다.");
                                                    } else {
                                                        if (task.getException().toString() != null) {
                                                            startToast(task.getException().toString());
                                                        }
                                                    }
                                                }
                                            }

                                        });

                            } else {
                                startToast("비밀번호가 일치하지 않습니다.");
                            }
                        } else {
                            // Handle failures
                            // ...
                            Log.e("실패", "실패");
                        }
                    }
                });
            }catch (FileNotFoundException e){
                Log.e("로그", "에러: " +e.toString());
            }


        }

        else{
            startToast("이메일 또는 비밀번호를 확인해주세요.");
        }
    }


    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 0);
    }

}
