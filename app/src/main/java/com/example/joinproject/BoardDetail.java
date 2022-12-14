package com.example.joinproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BoardDetail extends AppCompatActivity {

    private FirebaseAuth mauth = FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    Button delete;
    Button complete;

    TextView phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_detail);
        getIncomingIntent();
        delete = findViewById(R.id.delete);
        phone=findViewById(R.id.board_phone);
        complete = findViewById(R.id.complete);
        if (mauth.getUid().equals(getIntent().getStringExtra("board_writer"))) {
            delete.setVisibility(View.VISIBLE);
            complete.setVisibility(View.VISIBLE);
        }
        if(mauth.getUid().equals("0m4drrIAiYO3rB9f9BQhoQhpKBT2") || mauth.getUid().equals("IEsTpGZkeXeTxaXbSwmBhoTHvf02") ||
                mauth.getUid().equals("Z7qoREwkuPagtxYJLw6yjONf2QH2")|| mauth.getUid().equals("EM95VCkH10Rqat7BxLLop0WFERJ2")){
            phone.setVisibility(View.VISIBLE);
        }

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query contentFind = ref.child("Board").orderByChild("content").equalTo(getIntent().getStringExtra("board_content"));

                contentFind.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot contentFind : snapshot.getChildren()) {
                            contentFind.getRef().child("complete").setValue(1);
                        }
                        Toast.makeText(BoardDetail.this, "?????? ????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(BoardDetail.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query contentFind = ref.child("Board").orderByChild("content").equalTo(getIntent().getStringExtra("board_content"));

                contentFind.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot contentFind : snapshot.getChildren()) {
                            contentFind.getRef().removeValue();

                        }
                        Toast.makeText(BoardDetail.this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(BoardDetail.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void getIncomingIntent() {
//???????????? name ??? Adpater??? ?????? ?????? ???????????? ???????????????
        if (getIntent().hasExtra("board_title")) {
            String boardTitle = getIntent().getStringExtra("board_title");
            String boardDate = getIntent().getStringExtra("board_date");
            String boardContent = getIntent().getStringExtra("board_content");
            String boardImage = getIntent().getStringExtra("board_image");
            String boardPhone = "?????? ??????: "+getIntent().getStringExtra("board_phone");

            setIntent(boardTitle, boardContent, boardDate, boardImage,boardPhone);
        }
    }

    private void setIntent(String boardTitle, String boardContent, String boardDate, String boardImage, String boardPhone) {
        //R.id.    ??? Detail ?????? ?????? ????????? ?????????
        TextView title = findViewById(R.id.board_title);
        TextView content = findViewById(R.id.board_content);
        TextView date = findViewById(R.id.board_date);
        TextView phone= findViewById(R.id.board_phone);
        ImageView image = findViewById(R.id.board_image);
        title.setText(boardTitle);
        content.setText(boardContent);
        date.setText(boardDate);
        phone.setText(boardPhone);
        Glide.with(this)
                .asBitmap()
                .load(boardImage)
                .into(image);
    }
}