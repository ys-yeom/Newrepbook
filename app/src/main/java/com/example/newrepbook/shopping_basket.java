package com.example.newrepbook;

import static android.view.View.INVISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class shopping_basket extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);

        LinearLayout order_details = findViewById(R.id.order_details);

        ImageButton order_details_cancel = findViewById(R.id.order_details_cancel);

        order_details_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_details.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기

            }
        });
    }

}
