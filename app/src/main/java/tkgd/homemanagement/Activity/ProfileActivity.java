package tkgd.homemanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import io.supercharge.shimmerlayout.ShimmerLayout;
import tkgd.homemanagement.Adapter.SystemAdapter;
import tkgd.homemanagement.Model.System;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView systemrecycler;
    private TextView txtUsername;
    private ImageView imgUserphoto;
    private TextView txtLogout;
    GoogleApiClient mGoogleApiClient;
    private ArrayList<System> systems;
    private ShimmerLayout shimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, null)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        imgUserphoto = (ImageView) findViewById(R.id.imgUserPhoto);
        txtLogout = (TextView) findViewById(R.id.txtLogout);
        Log.d("Username", "Username: " + user.getDisplayName());
        if (user.getDisplayName() != null)
            txtUsername.setText(user.getDisplayName());
        else
            txtUsername.setText(user.getEmail());

        if (user.getPhotoUrl() != null) {
            String photoUrl = user.getPhotoUrl().toString().replace("/s96-c/", "/s720-c/");
            Glide.with(this).load(photoUrl).into(imgUserphoto);
        }
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myIntent);
                finish();
            }
        });
        shimmerLayout = findViewById(R.id.shimmer_layout);
        shimmerLayout.startShimmerAnimation();
        shimmerLayout.setVisibility(View.VISIBLE);
        systemrecycler = (RecyclerView) findViewById(R.id.system_recycleview);
        final LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false);
        systemrecycler.setItemAnimator(new DefaultItemAnimator());
        systemrecycler.setLayoutManager(layoutManagerScenarios);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        systemrecycler.addItemDecoration(itemDecoration);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        systems = new ArrayList<>(0);
        firebaseFirestore.collection("systems")
                .whereEqualTo("userid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    System tempsystem = document.toObject(System.class);
                                    tempsystem.setPhotoID(R.drawable.livingroom);
                                    tempsystem.setId(document.getId());
                                    systems.add(tempsystem);
                                }
                                SystemAdapter scenarioAdapter = new SystemAdapter(systems, ProfileActivity.this, 1);
                                systemrecycler.setAdapter(scenarioAdapter);
                                shimmerLayout.setVisibility(View.GONE);
                                systemrecycler.setVisibility(View.VISIBLE);

                            } else {
                                SystemAdapter scenarioAdapter = new SystemAdapter(systems, ProfileActivity.this, 1);
                                systemrecycler.setAdapter(scenarioAdapter);
                                shimmerLayout.setVisibility(View.GONE);
                                systemrecycler.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.d("Firebase", "Error getting system: ", task.getException());
                        }
                    }
                });
        Spinner dropdown = findViewById(R.id.spinLanguage);
        String[] languages = new String[]{"English", "Tiếng Việt"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        dropdown.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

