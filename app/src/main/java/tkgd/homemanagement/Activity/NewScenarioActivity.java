package tkgd.homemanagement.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tkgd.homemanagement.Adapter.PageAdapter;
import tkgd.homemanagement.R;
import tkgd.homemanagement.RoomScheduleFragment;

public class NewScenarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_scenario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("New scenario");
        toolbar.setTitleTextColor(Color.WHITE);
        ViewPager viewPager = (ViewPager) findViewById(R.id.roomPager);
        List<Fragment> fragments = getFragments();
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), fragments, 1));
    }

    private List<Fragment> getFragments() {

        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(RoomScheduleFragment.newInstance("Living room", 1));
        fList.add(RoomScheduleFragment.newInstance("Kitchen", 2));
        fList.add(RoomScheduleFragment.newInstance("Bed room", 3));
        return fList;
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
