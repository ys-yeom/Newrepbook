package com.example.newrepbook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class listMainActivity extends AppCompatActivity {
    private ListView listView;
    List fileList = new ArrayList<>();
    List fileList2 = new ArrayList<>();
    ArrayAdapter adapter,adapter2;
    static boolean calledAlready = false;
    private DataSnapshot fileSnapshot2;
    private ListView list_items;
    private listMainDTO mListItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready = true;
        }

        listView= (ListView) findViewById(R.id.ListView);

        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.txt_item, fileList);
        adapter2 = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.txt_number, fileList2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference DatabaseReference = database.getReference("webPosts");

        // Read from the database
        DatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // 클래스 모델이 필요
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    String str = fileSnapshot.child("name").getValue(String.class);
                    String str2 = fileSnapshot.child("staffkey").getValue(String.class);
                    Log.i("TAG: value is ", str);
                    listView.setAdapter(adapter);
                    fileList.add(str);

                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}



//    private static final String TAG = "listMainActivity";
//    private FirebaseFirestore firebaseFirestore;
//    private FirebaseUser firebaseUser;
//    private RecyclerView recyclerView;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(listMainActivity.this)); // 아이템 뷰가 나열되는 형태를 관리하기 위한 요소를 제공하는데, 이를 레이아웃매니저라고 함
//
//    }
//
//    protected void onResume(){
//        super.onResume();
//        if(firebaseUser != null) { // 유저가 존재한다면
//            CollectionReference collectionReference = firebaseFirestore.collection("posts"); //CollectionReference 는 파이어스토어의 컬렉션을 참조하는 객체다.
//            collectionReference.orderBy("createdAt", Query.Direction.DESCENDING).get() // 날짜
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) { // 성공적이면
//                                ArrayList<Addpostinfo> postList = new ArrayList<>();  // 글에 대한 정보를
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, document.getId() + " => " + document.getData());
//                                    postList.add(new Addpostinfo( // Addpostinfo에 저장되어 있는 정보들을 postList에 넣는다
//                                            document.getData().get("title").toString(),
//                                            (ArrayList<String>) document.getData().get("contents"),
//                                            document.getData().get("publisher").toString(),
//                                            new Date(document.getDate("createdAt").getTime())));
//                                }
//                                RecyclerView.Adapter mAdapter = new listMainAdapter(listMainActivity.this, postList);
//                                recyclerView.setAdapter(mAdapter);
//                            } else {
//                                Log.d(TAG, "Error getting documents: ", task.getException());
//                            }
//                        }
//                    });
//        }
//    }

