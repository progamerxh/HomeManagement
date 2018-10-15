package tkgd.homemanagement.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import tkgd.homemanagement.Adapter.MultiAdapter;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

public class NewDeviceActivity extends AppCompatActivity {
    static Context mContext;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RelativeLayout searchLayout;
    private RecyclerView deviceReyclerView;
    private EditText txtDevice;
    private ImageView btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device);
        mContext = NewDeviceActivity.this;
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchLayout = (RelativeLayout) findViewById(R.id.searchLayout);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle(" ");
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Search", Toast.LENGTH_SHORT).show();
            }
        });
        deviceReyclerView = (RecyclerView) findViewById(R.id.device_recyclerview);
        deviceReyclerView.setItemAnimator(new DefaultItemAnimator());
        deviceReyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter multiAdapter = new MultiAdapter(mContext, 0);
        deviceReyclerView.setAdapter(multiAdapter);
        deviceReyclerView.addItemDecoration(itemDecoration);

        btnScan = (ImageView) findViewById(R.id.btnScan);
        txtDevice = (EditText) findViewById(R.id.txtDevice);
        txtDevice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                    btnScan.setImageResource(R.drawable.camera);
                else
                    btnScan.setImageResource(R.drawable.close);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtDevice.getText().length() > 0)
                    txtDevice.setText("");
                else
                    Toast.makeText(getApplicationContext(), "Scan" , Toast.LENGTH_SHORT).show();
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
