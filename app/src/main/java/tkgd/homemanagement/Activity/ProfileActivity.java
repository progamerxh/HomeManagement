package tkgd.homemanagement.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import tkgd.homemanagement.Adapter.MultiAdapter;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView systemrecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        systemrecycler = (RecyclerView) findViewById(R.id.system_recycleview);
        final LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        systemrecycler.setItemAnimator(new DefaultItemAnimator());
        systemrecycler.setLayoutManager(layoutManagerScenarios);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter multiAdapter = new MultiAdapter(getApplicationContext(), 8);
        systemrecycler.setAdapter(multiAdapter);
        systemrecycler.addItemDecoration(itemDecoration);


        Spinner dropdown = findViewById(R.id.spinLanguage);
        String[] languages = new String[]{"English", "Tiếng Việt"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        dropdown.setAdapter(adapter);

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

