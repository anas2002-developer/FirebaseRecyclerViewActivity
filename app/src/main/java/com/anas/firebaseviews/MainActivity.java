package com.anas.firebaseviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView vRV;
    Adapter_Student adapter_student;

    FloatingActionButton fbtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbtnAdd=findViewById(R.id.fbtnAdd);
        vRV=findViewById(R.id.vRV);
        vRV.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Students"), model.class)
                        .build();

        adapter_student = new Adapter_Student(options);
        vRV.setAdapter(adapter_student);


        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,AddActivity.class));
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem search_item = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) search_item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searching(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searching(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searching(String query) {
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("Students")
                                .orderByChild("Course").startAt(query).endAt(query+"\uf8ff"), model.class)
                        .build();

        adapter_student = new Adapter_Student(options);
        adapter_student.startListening();
        vRV.setAdapter(adapter_student);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_student.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter_student.stopListening();
    }
}