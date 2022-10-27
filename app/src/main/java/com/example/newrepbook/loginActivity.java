package com.example.newrepbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class loginActivity extends AppCompatActivity {
    private static final String TAG = "joinActivity";
    private FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_login).setOnClickListener(onClickListener);
        findViewById(R.id.resetBtn).setOnClickListener(onClickListener);
        findViewById(R.id.btn_register).setOnClickListener(onClickListener);
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_login: // 로그인 버튼을 누르면
                    login();
                    break;
                case R.id.resetBtn: // 비밀번호 재설정 버튼을 누르면
                    startActivity(PasswordResetActivity.class);
                    break;
                case R.id.btn_register: // 비밀번호 재설정 버튼을 누르면
                    startActivity(joinActivity.class);
                    break;
            }
        }
    };

    private void login() {
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_pass)).getText().toString();

        if(email.length()>0 && password.length()>0) {
            final RelativeLayout loading = findViewById(R.id.loading);
            loading.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //로그인 성공시
                                    loading.setVisibility(View.GONE);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("로그인에 성공하였습니다.");
                                    startActivity(MainActivity.class);
                                    } else {
                                    if(task.getException() != null){
                                            startToast(task.getException().toString());
                                    }
                                }
                            }
                        });
        }else{
            startToast("비밀번호가 일치하지 않습니다.");
        }
    }


    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

}
