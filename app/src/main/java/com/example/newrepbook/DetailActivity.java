package com.example.newrepbook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    Recipe selectedRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSelelctRecipe();

        setValues();
    }

    private void setValues() {
//        TextView tv = findViewById(R.id.recipe_detail_name);
//
//        tv.setText(selectedRecipe.getName());
    }

    private void getSelelctRecipe() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

//        selectedRecipe = MainActivity.recipeList.get(Integer.valueOf(id));
    }
}
