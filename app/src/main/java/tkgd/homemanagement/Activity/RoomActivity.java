package tkgd.homemanagement.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tkgd.homemanagement.Adapter.MultiAdapter;
import tkgd.homemanagement.Adapter.PageAdapter;
import tkgd.homemanagement.Model.Room;
import tkgd.homemanagement.R;
import tkgd.homemanagement.RoomPhotoFragment;
import tkgd.homemanagement.Utility.ExpandingViewPagerTransformer;
import tkgd.homemanagement.Utility.FixAppBarLayoutBehavior;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

import static tkgd.homemanagement.Utility.DeviceSetting.differentDensityAndScreenSize;

public class RoomActivity extends AppCompatActivity {
    private Button devices, manage;
    private ArrayList<Room> rooms;
    private RecyclerView deviceReyclerView;
    private ViewPager pager;
    private PageAdapter obj_adapter;
    private String str_device;
    static Context mContext;
    private TextView textBadge;
    private TextView txtScenario;
    private int pageselected;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        mContext = RoomActivity.this;
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).setBehavior(new FixAppBarLayoutBehavior());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        devices = findViewById(R.id.txtDevices);
        manage = findViewById(R.id.txtManage);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    pager.setVisibility(View.INVISIBLE);
                } else {
                    pager.setVisibility(View.VISIBLE);
                }
            }
        });
        devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        deviceReyclerView = (RecyclerView) findViewById(R.id.device_recyclerview);
        deviceReyclerView.setItemAnimator(new DefaultItemAnimator());
        deviceReyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter multiAdapter = new MultiAdapter(RoomActivity.this, 1);
        deviceReyclerView.setAdapter(multiAdapter);
        deviceReyclerView.addItemDecoration(itemDecoration);

        Bundle extras = getIntent().getExtras();

        rooms = new ArrayList<>();
        rooms.add(new Room("Living Room", R.drawable.livingroom));
        rooms.add(new Room("Kitchen", R.drawable.kitchen));
        rooms.add(new Room("Bed Room", R.drawable.bedroom));
        if (extras != null) {
            rooms.add(new Room(extras.getString("ROOM_NAME"), extras.getInt("ROOM_PHOTOID")));
        }
        pager = (ViewPager) findViewById(R.id.viewpager);
        str_device = differentDensityAndScreenSize(getApplicationContext());
        List<Fragment> fragments = getFragments();
        pager.setAdapter(obj_adapter);
        pager.setClipToPadding(false);
        switch (str_device) {
            case "normal-hdpi":
                pager.setPadding(80, 0, 80, 0);
                break;
            case "normal-mdpi":
                pager.setPadding(80, 0, 80, 0);
                break;
            case "normal-xhdpi":
                pager.setPadding(80, 0, 80, 0);
                break;
            case "normal-xxhdpi":
                pager.setPadding(90, 0, 80, 0);
                break;
            case "normal-xxxhdpi":
                pager.setPadding(90, 0, 80, 0);
                break;
            case "normal-unknown":
                pager.setPadding(80, 0, 80, 0);
                break;
            default:
                pager.setPadding(80, 0, 80, 0);
                break;
        }
        pager.setPageTransformer(true, new ExpandingViewPagerTransformer());
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), fragments, 0));
        if (extras != null) {
            pager.setCurrentItem(rooms.size() - 1, true);
        }
        collapsingToolbarLayout.setTitle(rooms.get(pager.getCurrentItem()).getName());
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == rooms.size()) {
                    collapsingToolbarLayout.setTitle(" New room");
                    deviceReyclerView.setAdapter(new RecyclerView.Adapter() {
                        @NonNull
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            return null;
                        }

                        @Override
                        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                        }

                        @Override
                        public int getItemCount() {
                            return 0;
                        }
                    });
                } else {
                    collapsingToolbarLayout.setTitle(rooms.get(position).getName());
                    MultiAdapter multiAdapter = new MultiAdapter(RoomActivity.this, 1);
                    deviceReyclerView.setAdapter(multiAdapter);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_notification);
        menuItem.setActionView(R.layout.menu_item_action_badge);
        View actionView = menuItem.getActionView();
        textBadge = (TextView) actionView.findViewById(R.id.item_badge);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_notification:
                textBadge.setVisibility(View.VISIBLE);

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Fragment> getFragments() {

        List<Fragment> fList = new ArrayList<Fragment>();
        for (Room item : rooms) {
            fList.add(RoomPhotoFragment.newInstance(item.getName(), item.getPhotoID()));
        }
        fList.add(RoomPhotoFragment.newInstance("NEW_ROOM", R.drawable.plus_circle_outline));
        return fList;
    }


}

