package com.example.newrepbook.practice;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newrepbook.R;

public class postprint extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postprint);

        String pos = getIntent().getStringExtra("표지션 값");

        TextView postvalue = (TextView) findViewById(R.id.pos);

        postvalue.setText(pos);
    }
}
