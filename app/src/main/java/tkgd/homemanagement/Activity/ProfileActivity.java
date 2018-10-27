package tkgd.homemanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tkgd.homemanagement.Adapter.MultiAdapter;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView systemrecycler;
    private TextView txtUsername;
    private ImageView imgUserphoto;
    private TextView txtLogout;
    GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , null )
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        imgUserphoto= (ImageView) findViewById(R.id.imgUserPhoto);
        txtLogout = (TextView) findViewById(R.id.txtLogout);

        txtUsername.setText(user.getDisplayName());
        Glide.with(this).load(user.getPhotoUrl()).into(imgUserphoto);
        Log.d("avatar", "PhotoUrl: " + user.getPhotoUrl());
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(myIntent);
            }
        });
        systemrecycler = (RecyclerView) findViewById(R.id.system_recycleview);
        final LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        systemrecycler.setItemAnimator(new DefaultItemAnimator());
        systemrecycler.setLayoutManager(layoutManagerScenarios);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter multiAdapter = new MultiAdapter(ProfileActivity.this, 8);
        systemrecycler.setAdapter(multiAdapter);
        systemrecycler.addItemDecoration(itemDecoration);


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

