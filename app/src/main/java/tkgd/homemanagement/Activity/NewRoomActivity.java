package tkgd.homemanagement.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tkgd.homemanagement.R;

public class NewRoomActivity extends AppCompatActivity {
    private EditText txtRoomName;
    private ImageView btnAdd;
    private ImageView imgPhoto;
    private Button btnDone;
    private String type;
    private String name;
    private String systemid;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtRoomName = (EditText) findViewById(R.id.txtRoomName);
        btnAdd = (ImageView) findViewById(R.id.btnAddPhoto);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnDone = (Button) findViewById(R.id.btnDone);

        firebaseFirestore = FirebaseFirestore.getInstance();

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString("TYPE");
            name = extras.getString("NAME");
            systemid = extras.getString("systemid");
        }
        toolbar.setTitleTextColor(Color.WHITE);
        if (type.equals("ROOM")) {
            if (name == null) {
                actionBar.setTitle("New room");
                txtRoomName.setHint("Room name");
            } else {
                actionBar.setTitle(name);
                txtRoomName.setHint(name);
            }
        } else {
            if (name == null) {
                actionBar.setTitle("New system");
                txtRoomName.setHint("System name");
            } else {
                actionBar.setTitle(name);
                txtRoomName.setHint(name);
            }
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPhoto.setBackground(getDrawable(R.drawable.kitchen));
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("ROOM")) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("name", txtRoomName.getText().toString());
                    data.put("systemid", systemid);
                    data.put("createdtime", new Date());
                    firebaseFirestore.collection("rooms")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("name", txtRoomName.getText().toString());
                    data.put("userid", user.getUid());
                    data.put("roomcount", 0);
                    data.put("createdtime", new Date());
                    firebaseFirestore.collection("systems")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                if (type.equals("ROOM")) {
                    builder.setTitle("Cancel create new room?");
                } else
                    builder.setTitle("Cancel create new system?");
                builder.setMessage("Are you sure to discard all?");
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        onBackPressed();

                    }
                });
                builder.show();
                return true;
            }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
