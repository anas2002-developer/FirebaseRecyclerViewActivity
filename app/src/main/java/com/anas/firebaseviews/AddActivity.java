package com.anas.firebaseviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText eStudent_img,eStudent_name,eStudent_course,eStudent_email;
    Button btnAddStudent,btnGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        eStudent_img=findViewById(R.id.eStudent_img);
        eStudent_name=findViewById(R.id.eStudent_name);
        eStudent_course=findViewById(R.id.eStudent_course);
        eStudent_email=findViewById(R.id.eStudent_email);
        btnAddStudent=findViewById(R.id.btnAddStudent);
        btnGoBack=findViewById(R.id.btnGoBack);

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object> map = new HashMap<>();
                map.put("Pimage",eStudent_img.getText().toString());
                map.put("Name",eStudent_name.getText().toString());
                map.put("Course",eStudent_course.getText().toString());
                map.put("Email",eStudent_email.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("Students")
                        .push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                eStudent_img.setText("");
                                eStudent_name.setText("");
                                eStudent_course.setText("");
                                eStudent_email.setText("");
                                Toast.makeText(AddActivity.this, "Student Added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                eStudent_img.setText("");
                                eStudent_name.setText("");
                                eStudent_course.setText("");
                                eStudent_email.setText("");
                                Toast.makeText(AddActivity.this, "Student not added", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AddActivity.this,MainActivity.class));


            }
        });
    }
}