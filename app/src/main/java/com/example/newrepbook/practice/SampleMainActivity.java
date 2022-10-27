package com.example.newrepbook.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newrepbook.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SampleMainActivity extends AppCompatActivity implements memo_Adapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private memo_Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<memo_list> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practicemainactivity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("webPosts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    memo_list tv_memo_list = snapshot.getValue(memo_list.class);
                    String str2 = dataSnapshot.child("name").getValue(String.class);
                    arrayList.add(tv_memo_list);

                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SampleMainActivity", String.valueOf(error.toException()));
            }
        });

        adapter = new memo_Adapter(arrayList, this);

        adapter.OnItemClickListener(new memo_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SampleMainActivity.this, postprint.class);
                intent.putExtra("표지션 값", arrayList.get(position).getName());
                startActivity(intent);
//                Toast.makeText(SampleMainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {

    }
}