package com.example.newrepbook;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    boolean isMenuOpen = false;
    boolean ishateOpen = false;
    Animation tranlateLeftAnim;
    Animation tranlateRightAnim;
    LinearLayout menu, bookmark;
    Button menubtn, bookmark_btn;
    ImageButton cancel, bookmark_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);
        mAuth = FirebaseAuth.getInstance();

//        LinearLayout bottom_btn = (LinearLayout) findViewById(R.id.bottom_btn);

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        ViewPager pager2 = (ViewPager) findViewById(R.id.viewpager2);

        Button btn_first = (Button) findViewById(R.id.btn_first);     // 홈 버튼
        Button btn_second = (Button) findViewById(R.id.btn_second);   // 레시피
        Button btn_third = (Button) findViewById(R.id.btn_third);     // 랭킹
//        Button btn_four = (Button) findViewById(R.id.btn_four);       // 꿀팁

        Button tab_Item5 = (Button) findViewById(R.id.tab_Item5);
        Button tab_Item6 = (Button) findViewById(R.id.tab_Item6);
        Button tab_Item7 = (Button) findViewById(R.id.tab_Item7);
//        Button tab_Item8 = (Button) findViewById(R.id.tab_Item8);

//        findViewById(R.id.logout_btn).setOnClickListener(onClickListener);
//        findViewById(R.id.bookmark_btn).setOnClickListener(onClickListener);
        findViewById(R.id.bookmark_cancel).setOnClickListener(onClickListener);
//        findViewById(R.id.floatingActionButton).setOnClickListener(onClickListener);

        pager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(0);

        View.OnClickListener movePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                pager.setCurrentItem(tag);
            }
        };

        View.OnClickListener movePageListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                pager.setCurrentItem(tag);
            }
        };

        btn_first.setOnClickListener(movePageListener);
        btn_first.setTag(0);
        btn_second.setOnClickListener(movePageListener1);
        btn_second.setTag(1);
        btn_third.setOnClickListener(movePageListener);
        btn_third.setTag(2);
//        btn_four.setOnClickListener(movePageListener);
//        btn_four.setTag(3);


        // 동그라미 보이기
        pager2.setAdapter(new pagerAdapter2(getSupportFragmentManager()));
        pager2.setCurrentItem(0);

        View.OnClickListener movePageListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                pager2.setCurrentItem(tag);
            }
        };

        tab_Item5.setOnClickListener(movePageListener2);
        tab_Item5.setTag(0);
        tab_Item6.setOnClickListener(movePageListener2);
        tab_Item6.setTag(1);
        tab_Item7.setOnClickListener(movePageListener2);
        tab_Item7.setTag(2);
//        tab_Item8.setOnClickListener(movePageListener2);
//        tab_Item8.setTag(3);


        //메뉴 버튼을 누른 후

        menu = findViewById(R.id.menu);

        menu.bringToFront();  // 맨앞으로 보이기

        tranlateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        tranlateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        SlidingPageAnimationListener animListener3 = new SlidingPageAnimationListener();

        tranlateLeftAnim.setAnimationListener(animListener3);
        tranlateRightAnim.setAnimationListener(animListener3);

        menubtn = findViewById(R.id.menubtn);

        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuOpen) {
                    menu.startAnimation(tranlateRightAnim); // UI 오른쪽 방향으로 감추기
                } else {
                    menu.setVisibility(VISIBLE); // UI 생성
                    menu.startAnimation(tranlateLeftAnim); // 왼쪽 방향으로 등장
                }
                //scroll.setVisibility(ScrollView.INVISIBLE);
            }
        });


        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuOpen) {
                    menu.startAnimation(tranlateRightAnim); // UI 오른쪽 방향으로 감추기
                }
            }
        });


        // 안좋아하는 음식 버튼 누른 후

        LinearLayout hate_food = findViewById(R.id.hate_food);

        hate_food.bringToFront();  // 맨앞으로 보이기


//        tranlateLeftAnim.setAnimationListener(animListener3);
//        tranlateRightAnim.setAnimationListener(animListener3);

//        Button hate_btn = (Button) findViewById(R.id.hate_btn);

//        hate_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    hate_food.setVisibility(VISIBLE);
//            }
//        });


// 메뉴 UI의 취소 버튼

        ImageButton cancel2 = findViewById(R.id.cancel2);

        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    hate_food.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기
            }
        });


//// 내가 본 레시피 버튼 누른 후
//
        LinearLayout identify_food = findViewById(R.id.identify_food);

        identify_food.bringToFront();  // 맨앞으로 보이기

        Button identift_food_btn = (Button) findViewById(R.id.identify_food_btn);

        identift_food_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identify_food.setVisibility(VISIBLE);
            }
        });
//
//
//// 내가 본 레시피 UI의 취소 버튼
//
        ImageButton identify_food_cancel = findViewById(R.id.identify_food_cancel);

        identify_food_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identify_food.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기

            }
        });

        //즐겨찾기 버튼 누른 후
        LinearLayout bookmarking = findViewById(R.id.bookmarking);

        bookmarking.bringToFront();

        Button bookmark_btn = (Button) findViewById(R.id.bookmark_btn);

        bookmark_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarking.setVisibility(VISIBLE);
            }
        });

        //즐겨찾기 버튼 취소 버튼

        ImageButton bookmarking_cancel = findViewById(R.id.bookmarking_cancel);

        bookmarking_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarking.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기

            }
        });

        // 찜한 목록 버튼 누른 후
        LinearLayout product_storage = findViewById(R.id.product_storage);

        product_storage.bringToFront();

        Button product_storage_btn = (Button) findViewById(R.id.product_storage_btn);

        product_storage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_storage.setVisibility(VISIBLE);
            }
        });


//     찜한 목록 UI의 취소 버튼
        ImageButton product_storage_cancel = findViewById(R.id.product_storage_cancel);

        product_storage_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_storage.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기

            }
        });

        //장바구니 버튼 누른 후
        LinearLayout product_storage2 = findViewById(R.id.product_storage2);

        product_storage2.bringToFront();

        Button product_storage2_btn = (Button) findViewById(R.id.product_storage2_btn);

        product_storage2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_storage2.setVisibility(VISIBLE);
            }
        });

        //장바구니 UI의 취소 버튼
        ImageButton product_storage2_cancel = findViewById(R.id.product_storage2_cancel);

        product_storage2_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_storage2.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기

            }
        });

        //최근 본 상품 누른 후
        LinearLayout product = findViewById(R.id.product);

        product.bringToFront();

        Button product_btn = (Button) findViewById(R.id.product_btn);

        product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setVisibility(VISIBLE);
            }
        });

        //최근 본 상품 UI의 취소 버튼
        ImageButton product_cancel = findViewById(R.id.product_cancel);

        product_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기

            }
        });

       //주문 내역 누른 후
        LinearLayout product_list = findViewById(R.id.product_list);

        product_list.bringToFront();

        Button product_list_btn = (Button) findViewById(R.id.product_list_btn);

        product_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_list.setVisibility(VISIBLE);
            }
        });

        //주문 내역 UI의 취소 버튼
        ImageButton product_list_cancel = findViewById(R.id.product_list_cancel);

        product_list_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_list.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기

            }
        });

        //내가 쓴 후기 누른 후
        LinearLayout evaluation = findViewById(R.id.evaluation);

        evaluation.bringToFront();

        Button evaluation_btn = (Button) findViewById(R.id.evaluation_btn);

        evaluation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluation.setVisibility(VISIBLE);
            }
        });

        //내가 쓴 후기 UI의 취소 버튼
        ImageButton evaluation_cancel = findViewById(R.id.evaluation_cancel);

        evaluation_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluation.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기

            }
        });

        //설정 누른 후
        LinearLayout setting = findViewById(R.id.setting);

        setting.bringToFront();

        Button setting_btn = (Button) findViewById(R.id.setting_btn);

        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.setVisibility(VISIBLE);
            }
        });

        //설정 UI의 취소 버튼
        ImageButton setting_cancel = findViewById(R.id.setting_cancel);

        setting_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기

            }
        });
    }




    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bookmark: //즐겨찾기를 누르면
                    bookmark_click();
                    break;
//                case R.id.logout_btn: //로그아웃을 누르면
//                    FirebaseAuth.getInstance().signOut();
//                    startMainActivity();
//                    break;
                case R.id.bookmark_cancel: //즐겨찾기 UI 취소 누르면
                    bookmark_cancel();
                    break;
//                case R.id.floatingActionButton: //게시물 만들기 버튼
//                    startAddPostActivity();
//                    break;
            }
        }
    };

    // 즐겨찾기 버튼 누른 후
    private void bookmark_click() {
        bookmark = findViewById(R.id.bookmark);
        bookmark.bringToFront();  // 맨앞으로 보이기
        bookmark.setVisibility(VISIBLE);
    }

    // 즐겨찾기 UI 취소 버튼
    private void bookmark_cancel() {
        bookmark.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기
    }

    // 다시 로그인 페이지로 돌아가기
    private void startMainActivity() {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }

    // 다시 로그인 페이지로 돌아가기
    private void startAddPostActivity() {
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }



    private class SlidingPageAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        public void onAnimationEnd(Animation animation){

            if (isMenuOpen){
                menu.setVisibility(INVISIBLE);

                isMenuOpen = false;
            }
            else{
                isMenuOpen = true;
            }

//            if (ishateOpen){
//                hate_food.setVisibility(INVISIBLE);
//
//                ishateOpen = false;
//            }
//            else{
//                ishateOpen = true;
//            }

        }
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}