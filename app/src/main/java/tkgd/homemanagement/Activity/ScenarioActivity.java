package tkgd.homemanagement.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import tkgd.homemanagement.Adapter.MultiAdapter;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

public class ScenarioActivity extends AppCompatActivity {
    private RecyclerView scenariorecycleview;
    static Context mContext;
    private ImageButton btnExpand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_scenario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.account_outline));

        scenariorecycleview = (RecyclerView) findViewById(R.id.scenario_recycleview);
        final LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(ScenarioActivity.this, LinearLayoutManager.HORIZONTAL, false);
        scenariorecycleview.setItemAnimator(new DefaultItemAnimator());
        scenariorecycleview.setLayoutManager(layoutManagerScenarios);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter multiAdapter = new MultiAdapter(ScenarioActivity.this, 2);
        scenariorecycleview.setAdapter(multiAdapter);
        scenariorecycleview.addItemDecoration(itemDecoration);

        btnExpand = (ImageButton) findViewById(R.id.imgExpand);
        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnExpand.setSelected(!btnExpand.isSelected());
                if (btnExpand.isSelected())
                {
                    scenariorecycleview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                }
                else
                    scenariorecycleview.setLayoutManager(layoutManagerScenarios);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_notification);
        menuItem.setActionView(R.layout.menu_item_action_badge);
        View actionView = menuItem.getActionView();
        final TextView textBadge = (TextView) actionView.findViewById(R.id.item_badge);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textBadge.setVisibility(View.INVISIBLE);
                Intent myIntent = new Intent(mContext, NotificationActivity.class);
                startActivity(myIntent);
            }
        });
        return true;
    }

}
