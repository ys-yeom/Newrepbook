package com.example.newrepbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class check_basket extends AppCompatActivity {
    Button shopping_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store);

//        Button shopping_btn = (Button) findViewById(R.id.shopping_btn);
        shopping_btn = findViewById(R.id.shopping_btn);

        shopping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), shopping_basket.class);
                startActivity(intent);
            }
        });
    }
}
