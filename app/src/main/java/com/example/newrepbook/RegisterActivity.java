package com.example.newrepbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_nickname,et_id, et_pass, et_email, et_phone;
    private Button btn_register, btnId_check;
    private AlertDialog dialog;
    private boolean Id_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        // 아이디 값 찾아주기
//        et_nickname = findViewById(R.id.et_nickname);
//        et_id = findViewById(R.id.et_id);
//        et_pass = findViewById(R.id.et_pass);
//        et_email = findViewById(R.id.et_email);
//        et_phone = findViewById(R.id.et_phone);

        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register);
        // 아이디 중복 확인 버튼
//        btnId_check = findViewById(R.id.btnId_check);
//
//        btnId_check.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                String userID = et_id.getText().toString();
//                if (Id_check)
//                {
//                    return;
//                }
//
//                if (userID.equals("")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                    dialog = builder.setMessage("아이디를 입력해주세요").setPositiveButton("확인", null).create();
//                    dialog.show();
//                    return;
//                }
//
//                Response.Listener<String> responseListner = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                            if (success) {
//                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
//                                dialog.show();
//                                et_id.setEnabled(false);
//                                Id_check = true;
//                                //Id_check.setText("확인");
//                            }
//                            else
//                            {
//                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.").setNegativeButton("확인", null).create();
//                                dialog.show();
//                            }
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                IDcheck_Request IDRequest = new IDcheck_Request(userID, responseListner);
//                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
//                queue.add(IDRequest);
//            }
//        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String userNickname = et_nickname.getText().toString();
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userEmail = et_email.getText().toString();
                String userPhone = et_phone.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, joinActivity.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                RegisterRequest registerRequest = new RegisterRequest(userNickname,userID,userPass,userEmail,userPhone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });

    }
}
