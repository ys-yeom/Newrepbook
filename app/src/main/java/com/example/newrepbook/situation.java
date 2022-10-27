package com.example.newrepbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class situation extends AppCompatActivity {
    public static final int sub = 1001;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type);

        Button tab_Item9 = findViewById(R.id.tab_Item9);
        Button tab_Item10 = findViewById(R.id.tab_Item10);
        Button tab_Item11 = findViewById(R.id.tab_Item11);
        Button tab_Item12 = findViewById(R.id.tab_Item12);

        tab_Item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ui1.class);
                startActivityForResult(intent,sub);//액티비티 띄우기
            }
        });

    }
}
