package com.example.newrepbook;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this,"88268c08d19d0174931ca484732ec09a");
    }
//    mOAuthLoginModule = OAuthLogin.getInstance();
//        mOAuthLoginModule.init(
//    LoginActivity.this
//            ,getString(R.string.naver_client_id)
//                ,getString(R.string.naver_client_secret)
//                ,getString(R.string.app_name)
//
//);
}
