package com.anas.firebaseviews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Student extends FirebaseRecyclerAdapter<model,Adapter_Student.ViewHolder_Student> {

    Context context;

    public Adapter_Student(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder_Student holder, final int position, @NonNull model model) {
        holder.txtStudent_name.setText(model.getName());
        holder.txtStudent_course.setText(model.getCourse());
        holder.txtStudent_email.setText(model.getEmail());
        Glide.with(holder.imgStudent.getContext()).load(model.getPimage()).into(holder.imgStudent);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.imgStudent.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_plus))
                        .setExpanded(true,900)
                        .setCancelable(true)
                        .setGravity(Gravity.CENTER)
                        .create();

//                dialogPlus.show();
                View view_dialog=dialogPlus.getHolderView();
                EditText eStudent_img,eStudent_name,eStudent_course,eStudent_email;
                Button btnUpdate;
                eStudent_img=view_dialog.findViewById(R.id.eStudent_img);
                eStudent_name=view_dialog.findViewById(R.id.eStudent_name);
                eStudent_course=view_dialog.findViewById(R.id.eStudent_course);
                eStudent_email=view_dialog.findViewById(R.id.eStudent_email);
                btnUpdate=view_dialog.findViewById(R.id.btnUpdate);

                eStudent_img.setText(model.getPimage());
                eStudent_name.setText(model.getName());
                eStudent_course.setText(model.getCourse());
                eStudent_email.setText(model.getEmail());

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("Pimage",eStudent_img.getText().toString());
                        map.put("Name",eStudent_name.getText().toString());
                        map.put("Course",eStudent_course.getText().toString());
                        map.put("Email",eStudent_email.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Students").child(getRef(position).getKey())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogPlus.dismiss();
                                        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });

                dialogPlus.show();

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.imgStudent.getContext());
                alertDialog.setTitle("Delete Student");
                alertDialog.setMessage("Are you sure ?");
                alertDialog.setCancelable(true);

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("Students").child(getRef(position).getKey()).removeValue();
                        Toast.makeText(context, "Student Record deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();
            }
        });
    }



    @NonNull
    @Override
    public ViewHolder_Student onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row,parent,false);
        return new ViewHolder_Student(view);
    }

    public class ViewHolder_Student extends RecyclerView.ViewHolder {

        CircleImageView imgStudent;
        TextView txtStudent_name,txtStudent_email,txtStudent_course;
        Button btnEdit,btnDelete;

        public ViewHolder_Student(@NonNull View itemView) {
            super(itemView);

            imgStudent=itemView.findViewById(R.id.imgStudent);
            txtStudent_name=itemView.findViewById(R.id.txtStudent_name);
            txtStudent_course=itemView.findViewById(R.id.txtStudent_course);
            txtStudent_email=itemView.findViewById(R.id.txtStudent_email);

            btnEdit=itemView.findViewById(R.id.btnEdit);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }
    }


}
