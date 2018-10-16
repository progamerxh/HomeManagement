package tkgd.homemanagement.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import tkgd.homemanagement.R;

public class NewRoomActivity extends AppCompatActivity {
    private EditText txtRoomName;
    private ImageView btnAdd;
    private ImageView imgPhoto;
    private Button btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("New room");
        toolbar.setTitleTextColor(Color.WHITE);

        txtRoomName = (EditText) findViewById(R.id.txtRoomName);
        btnAdd = (ImageView) findViewById(R.id.btnAddPhoto);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPhoto.setBackground(getDrawable(R.drawable.kitchen));
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RoomActivity.class);
                intent.putExtra("ROOM_NAME", txtRoomName.getText().toString());
                intent.putExtra("ROOM_PHOTOID", R.drawable.livingroom);
                startActivity(intent);
            }
        });
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