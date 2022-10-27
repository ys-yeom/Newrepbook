package com.example.newrepbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class ui1 extends AppCompatActivity {
    private static final String TAG = "ui1";

    private View loginButton, logoutButton;
    //    private TextView nickname;
   //    private ImageView profileImage;
    //구글
    SignInButton signInButton ;
    Button logoutBt;
    LinearLayout ll_naver_login; //네이버
    Button btn_logout;
    Button loginBtn;
    // Google Sign In API와 호출할 구글 로그인 클라이언트
    GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 123;
    //네이버 호출
    OAuthLogin mOAuthLoginModule;
    OAuthLoginCallback mOAuthLoginCallback;
    Context mContext;
    NidOAuthLogin callDeleteTokenApi;

    private FirebaseAuth mAuth;

    private GoogleApiClient mGoogleApiClient;

    // Initialize Firebase Auth



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui1);

        mContext = getApplicationContext();

        //구글 로그인
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        logoutBt = findViewById(R.id.logoutBt);
        //카카오
        loginButton = findViewById(R.id.login);
        logoutButton = findViewById(R.id.logout);
//        nickname = findViewById(R.id.nickname);
//        profileImage  = findViewById(R.id.profile);
        //네이버
        ll_naver_login = findViewById(R.id.ll_naver_login);
        btn_logout = findViewById(R.id.btn_logout);

        mAuth = FirebaseAuth.getInstance();

        Button loginBtn = (Button) findViewById(R.id.loginBtn);//일반

        // 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정한다.
        // DEFAULT_SIGN_IN parameter는 유저의 ID와 기본적인 프로필 정보를 요청하는데 사용된다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() // email addresses도 요청함
                .build();


        // 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듬
        mGoogleSignInClient = GoogleSignIn.getClient(ui1.this, gso);

        // 기존에 로그인 했던 계정을 확인한다.
        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(ui1.this);
        // 로그인 되있는 경우 (토큰으로 로그인 처리)
        if (gsa != null && gsa.getId() != null) {

        }

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    //TBD
                }
                if (throwable != null) {
                    //TBD
                }
                updateKakaoLoginUi();
                return null;
            }
        };


        ll_naver_login.setOnClickListener(new View.OnClickListener() { //네이버 로그인
            public void onClick(View view) {
                Intent intent = new Intent(ui1.this, MainActivity.class);
                startActivity(intent);
                mOAuthLoginModule = OAuthLogin.getInstance();
                mOAuthLoginModule.init(
                        mContext
                        ,getString(R.string.naver_client_id)
                        ,getString(R.string.naver_client_secret)
                        ,getString(R.string.naver_client_name)
                        //,OAUTH_CALLBACK_INTENT
                        // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
                );
                @SuppressLint("HandlerLeak")
                OAuthLoginCallback mOAuthLoginCallback = new OAuthLoginCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i, @NonNull String s) {

                    }

                    @Override
                    public void onError(int i, @NonNull String s) {

                    }

                    public void run(boolean success) {
                        if (success) {
                            String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                            String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                            long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                            String tokenType = mOAuthLoginModule.getTokenType(mContext);

                            Log.i("LoginData","accessToken : "+ accessToken);
                            Log.i("LoginData","refreshToken : "+ refreshToken);
                            Log.i("LoginData","expiresAt : "+ expiresAt);
                            Log.i("LoginData","tokenType : "+ tokenType);

                        } else {
                            String errorCode = mOAuthLoginModule
                                    .getLastErrorCode(mContext).getCode();
                            String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                            Toast.makeText(mContext, "errorCode:" + errorCode
                                    + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                mOAuthLoginModule.startOauthLoginActivity(ui1.this, mOAuthLoginCallback);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {//구글 로그인
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(ui1.this, MainActivity.class);
                startActivity(Intent);
                signIn();
            }
            private void signIn() {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        logoutBt.setOnClickListener(new View.OnClickListener() {  //구글 로그아웃
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() { //카카오 로그인
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ui1.this, MainActivity.class);
                startActivity(intent);
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(ui1.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(ui1.this, callback);
                }else {
                    UserApiClient.getInstance().loginWithKakaoAccount(ui1.this, callback);
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() { //카카오 로그아웃
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });
            }
        });
//        updateKakaoLoginUi();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ui1.this, loginActivity.class);
                startActivity(intent);
                //signIn();
            }
        });
    }


        private void updateKakaoLoginUi(){
            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                @Override
                public Unit invoke(User user, Throwable throwable) {
                    if (user != null){

                        Log.i(TAG, "invoke: id=" + user.getId());
                        Log.i(TAG, "invoke: emil=" + user.getKakaoAccount().getEmail());
                        Log.i(TAG, "invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());
                        Log.i(TAG, "invoke: gender=" + user.getKakaoAccount().getGender());
                        Log.i(TAG, "invoke: age=" + user.getKakaoAccount().getAgeRange());

//                    nickname.setText(user.getKakaoAccount().getProfile().getNickname());
//                    Glide.with(profileImage).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl()).circleCrop().into(profileImage);

                        loginButton.setVisibility(View.GONE);
                        loginButton.setVisibility(View.VISIBLE);
                    } else {
//                    nickname.setText(null);
//                    profileImage.setImageBitmap(null);
                        loginButton.setVisibility(View.VISIBLE);
                        logoutButton.setVisibility(View.GONE);
                    }
                    return null;
                }
            });
        }

        private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
            try {
                GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

                if (acct != null){
                    String personName = acct.getDisplayName();
                    String personGivenName = acct.getGivenName();
                    String personFamilyName = acct.getFamilyName();
                    String personEmail = acct.getEmail();
                    String personId = acct.getId();
                    Uri personPhoto = acct.getPhotoUrl();

                    Log.d(TAG, "handleSignInResult:personName "+personName);
                    Log.d(TAG, "handleSignInResult:personGivenName "+personGivenName);
                    Log.d(TAG, "handleSignInResult:personEmail "+personEmail);
                    Log.d(TAG, "handleSignInResult:personId "+personId);
                    Log.d(TAG, "handleSignInResult:personFamilyName "+personFamilyName);
                    Log.d(TAG, "handleSignInResult:personPhoto "+personPhoto);
                }

            }catch (ApiException e){
                Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
            }
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

}

