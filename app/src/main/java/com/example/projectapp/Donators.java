package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Donators extends AppCompatActivity implements Adapter.OnNoteListener {

    private RecyclerView rView;
    FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    FirestoreRecyclerOptions<User> options;
    FirestoreRecyclerAdapter<User, ViewHolderUsers> adapter;
    ImageView button3;
    DatabaseReference dReference;
    List<User> users;
    CallbackManager callbackManager;
    Target t = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            SharePhoto sPhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();

            if (ShareDialog.canShow(SharePhotoContent.class)) {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sPhoto)
                        .build();
                shareDialog.show(content);
            }

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };


    ShareDialog shareDialog = new ShareDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donators2);
        rView = findViewById(R.id.recyclerView1);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        users = new ArrayList<>();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        button3 = findViewById(R.id.button3);




        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(Donators.this, "Uspjesno ste podijelili", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Donators.this, "Nespjesno ste podijelili", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {

                        Toast.makeText(Donators.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });


                Picasso.with(getBaseContext()).load("https://u-static.haozhaopian.net/assets/projects/pages/6a143150-f9b0-11ea-af28-431164c5cec4_6a147f70-f9b0-11ea-af28-431164c5cec4_thumb.jpg?_v=460453").into(t);

            }


        });


        rView.setHasFixedSize(true);
        LinearLayoutManager LManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rView.setLayoutManager(LManager);
        dReference = FirebaseDatabase.getInstance().getReference("Donation");

        final CollectionReference collectionReference = fStore.collection("users");

        options = new FirestoreRecyclerOptions.Builder<User>().setQuery(collectionReference, User.class).build();
        adapter = new FirestoreRecyclerAdapter<User, ViewHolderUsers>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderUsers holder, int position, @NonNull final User model) {

                holder.email.setText(model.email);
                holder.flName.setText(model.firstName+ " " + model.lastName);
                dReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int sumOfDonation = 0;

                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            Donation don = snap.getValue(Donation.class);
                            if (don.email.equals(model.email)) {

                                sumOfDonation = sumOfDonation + don.getNumOfDonation();
                                holder.sumOfMeals.setText(sumOfDonation + "." + "obroka");

                            }


                        }
                        if (sumOfDonation < 50)
                        {
                            button3.setVisibility(View.INVISIBLE);
                        } else
                        {
                            button3.setVisibility(View.VISIBLE);
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                printKeyHash();


            }

            @NonNull
            @Override
            public ViewHolderUsers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
                return new ViewHolderUsers(view);
            }
        };

        adapter.startListening();
        rView.setAdapter(adapter);


    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.projectapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onNoteClick(int position) {


    }
}