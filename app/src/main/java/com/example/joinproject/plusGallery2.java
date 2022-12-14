package com.example.joinproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class plusGallery2 extends BasicActivity {
    private static int PICK_IMAGE_REQUEST = 1;
    ImageView imageView;
    private EditText editText;
    private EditText editText2;
    private Button button;
    private Uri imageUri;
    private DatabaseReference root= FirebaseDatabase.getInstance().getReference("Gallery2");
    private StorageReference reference= FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mauth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_plus);

        editText = (EditText) findViewById(R.id.write_title_gallery);
        editText2 = (EditText) findViewById(R.id.write_content_gallery);
        button = (Button) findViewById(R.id.check_gallery);


    }
    protected void onStart() {
        super.onStart();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebase(imageUri);
            }
        });



    }

    public void loadImagefromGallery(View view) {
        //intent ??????
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //intent?????? - ??????????????? ????????? ????????? ???????????? ????????? ??? ??????.
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //????????? ???????????? ?????? ????????????
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //???????????? ?????? ????????? ???
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data?????? ??????????????? ???????????? ?????????
                imageUri=data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                //???????????? ???????????? ?????? ???????????? ???????????? ???????????? ????????????.
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                imageView = (ImageView) findViewById(R.id.imageView_gallery);
                imageView.setImageBitmap(scaled);

            } else {
                Toast.makeText(this, "?????? ???????????????.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "????????? ????????? ????????????.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private void uploadToFirebase(Uri uri){
        final StorageReference fileRef=reference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Date currentTime = Calendar.getInstance().getTime();
                        String date = new SimpleDateFormat("yyyy??? MM??? dd??? EE??????", Locale.getDefault()).format(currentTime);

                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                        Gallery2 gallery2 = new Gallery2(editText.getText().toString(), editText2.getText().toString(),uri.toString(),mauth.getUid(),date);
                        database.child("Gallery2").push().setValue(gallery2);
                        Toast.makeText(plusGallery2.this, "????????? ??????.", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(plusGallery2.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        startActivity(new Intent(plusGallery2.this,CompanyMenu2.class));
                        startActivity(new Intent(plusGallery2.this,companyGallery2.class));
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(plusGallery2.this, "????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(mUri));
    }

}