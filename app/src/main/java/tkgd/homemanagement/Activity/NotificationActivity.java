package tkgd.homemanagement.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import tkgd.homemanagement.Adapter.MultiAdapter;
import tkgd.homemanagement.R;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView notificaionRecycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Notification");
        toolbar.setTitleTextColor(Color.WHITE);
        notificaionRecycleview = (RecyclerView) findViewById(R.id.notification_recycleview);
        LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, true);
        notificaionRecycleview.setItemAnimator(new DefaultItemAnimator());
        notificaionRecycleview.setLayoutManager(layoutManagerScenarios);

        MultiAdapter multiAdapter = new MultiAdapter(NotificationActivity.this, 3);
        notificaionRecycleview.setAdapter(multiAdapter);
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
