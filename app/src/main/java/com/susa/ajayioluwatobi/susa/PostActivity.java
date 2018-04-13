package com.susa.ajayioluwatobi.susa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private DatabaseReference databaseReference;

    private EditText address, price;
    private Button post;
    private Button feed;
    private FirebaseAuth firebaseAuth;
    private Spinner location;
    private boolean location_assert= false;
    private String city;
    private Button mSelectImage;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT= 2;
    private ImageView image_1;
    private ImageView image_2;
    private ImageView image_3;

    Uri post_image;



    boolean image1= false;
    boolean image2= false;
    boolean image3= false;


    private ProgressDialog mProgressDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        address = (EditText) findViewById(R.id.address_Text);
        price= (EditText) findViewById(R.id.edit_Price);
        post= (Button) findViewById(R.id.Post_Button);
        feed= (Button) findViewById(R.id.feed_button);
        firebaseAuth= FirebaseAuth.getInstance();
        location = (Spinner) findViewById(R.id.location_spinner);
        location.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        mSelectImage= (Button) findViewById(R.id.upload_image) ;

        image_1= (ImageView) findViewById(R.id.image_1);
        image_2=  (ImageView)   findViewById(R.id.image_2);
        image_3=    (ImageView) findViewById(R.id.image_3);



        mProgressDialog = new ProgressDialog(this);


        mStorage= FirebaseStorage.getInstance().getReference();

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location.setAdapter(adapter);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postLease();
                if(location_assert) {
                    Intent intent = new Intent(PostActivity.this, FeedActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(PostActivity.this ,"Please select location" ,Toast.LENGTH_LONG).show();
                }
            }
        });


        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        city = parent.getItemAtPosition(position).toString();
        location_assert= true;

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setMessage("Uploading ...");
            Uri uri= data.getData();
            FirebaseUser user = firebaseAuth.getCurrentUser();

            StorageReference filepath= mStorage.child(user.getUid()).child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(PostActivity.this, "Upolad Done", Toast.LENGTH_LONG).show();

                    mProgressDialog.dismiss();
                    Uri mdownloadUri = taskSnapshot.getDownloadUrl();
                    if(!image1) {
                        Picasso.with(PostActivity.this).load(mdownloadUri).fit().centerCrop().into(image_1);
                      post_image= mdownloadUri;

                        image1= true;
                    }
                    else if (!image2){
                        Picasso.with(PostActivity.this).load(mdownloadUri).fit().centerCrop().into(image_2);

                        image2= true;
                    }
                    else if (!image3){
                        Picasso.with(PostActivity.this).load(mdownloadUri).fit().centerCrop().into(image_3);

                        image3=true;
                    }
                    else
                    {
                        Toast.makeText(PostActivity.this, "Upolad Limit", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void postLease(){

        String addy= address.getText().toString().trim();
        int value= Integer.parseInt(price.getText().toString().trim());
        String temp_image= post_image.toString();


        UserPost userPost= new UserPost(value,addy,city,temp_image);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userPost);

        Toast.makeText(this, "Posted", Toast.LENGTH_LONG).show();
        return;

    }
}
