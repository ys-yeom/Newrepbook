package com.example.newrepbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordResetActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pw);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.passReset_btn).setOnClickListener(onClickListener);
    }


    View.OnClickListener onClickListener = (v) -> {
            switch(v.getId()){
                case R.id.passReset_btn: // 비밀번호 재설정 페이지의 전송을 누르면
                    reset();
                    break;
            }
    };

    private void reset() {
        String passReset_edit = ((EditText) findViewById(R.id.passReset_edit)).getText().toString();

        if(passReset_edit.length()>0) {
            final RelativeLayout loading = findViewById(R.id.loading);
            loading.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(passReset_edit)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                loading.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                            startToast("비밀번호 재설정 이메일을 전송했습니다.");
                                }
                            }
                        });
        }else{
            startToast("이메일을 입력해주세요.");
        }
    }


    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    // 뒤로가기 버튼을 누르면 프로세스 종료
//    public void onBackPressed() {
//        super.onBackPressed();
//        moveTaskToBack(true);
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(1);
//    }
}
