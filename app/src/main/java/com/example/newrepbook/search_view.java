package com.example.newrepbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class search_view extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<Recipe> list;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        ref = FirebaseDatabase.getInstance().getReference().child("Recipe");
        recyclerView = findViewById(R.id.rv);
        searchView = findViewById(R.id.search_view1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren())
                        {
                            list.add(ds.getValue(Recipe.class));
                        }
                        RecipeAdapter recipeAdapter = new RecipeAdapter(list);
                        recyclerView.setAdapter(recipeAdapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }

    private void search(String str) {
        ArrayList<Recipe> myList = new ArrayList<>();
        for (Recipe object : myList)
        {
            if (object.getName().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        RecipeAdapter recipeAdapter = new RecipeAdapter(myList);
        recyclerView.setAdapter(recipeAdapter);
    }
}
