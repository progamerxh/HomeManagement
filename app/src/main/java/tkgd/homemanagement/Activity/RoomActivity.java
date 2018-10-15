package tkgd.homemanagement.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
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
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tkgd.homemanagement.Model.Room;
import tkgd.homemanagement.Adapter.MultiAdapter;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Adapter.PageAdapter;
import tkgd.homemanagement.RoomPhotoFragment;
import tkgd.homemanagement.Utility.ExpandingViewPagerTransformer;
import tkgd.homemanagement.Utility.FixAppBarLayoutBehavior;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

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
                Toast.makeText(RoomActivity.this, "Devices clicked", Toast.LENGTH_SHORT).show();
            }
        });
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RoomActivity.this, "Manage clicked", Toast.LENGTH_SHORT).show();
            }
        });
        deviceReyclerView = (RecyclerView) findViewById(R.id.device_recyclerview);
        deviceReyclerView.setItemAnimator(new DefaultItemAnimator());
        deviceReyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        MultiAdapter multiAdapter = new MultiAdapter(RoomActivity.this, 0);
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
        differentDensityAndScreenSize(getApplicationContext());
        List<Fragment> fragments = getFragments();
        pager.setAdapter(obj_adapter);
        pager.setClipToPadding(false);

        if (str_device.equals("normal-hdpi")) {
            pager.setPadding(80, 0, 80, 0);
        } else if (str_device.equals("normal-mdpi")) {
            pager.setPadding(80, 0, 80, 0);
        } else if (str_device.equals("normal-xhdpi")) {
            pager.setPadding(80, 0, 80, 0);
        } else if (str_device.equals("normal-xxhdpi")) {
            pager.setPadding(90, 0, 90, 0);
        } else if (str_device.equals("normal-xxxhdpi")) {
            pager.setPadding(90, 0, 90, 0);
        } else if (str_device.equals("normal-unknown")) {
            pager.setPadding(80, 0, 80, 0);
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
                Toast.makeText(RoomActivity.this, "Page selected: " + position, Toast.LENGTH_SHORT).show();
                if (position == rooms.size()) {
                    collapsingToolbarLayout.setTitle(" New room");
                } else {
                    collapsingToolbarLayout.setTitle(rooms.get(position).getName());
                }
                MultiAdapter multiAdapter = new MultiAdapter(RoomActivity.this, position % 2);
                deviceReyclerView.setAdapter(multiAdapter);
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
                Toast.makeText(RoomActivity.this, "Notification clicked", Toast.LENGTH_SHORT).show();
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

    public int differentDensityAndScreenSize(Context context) {
        int value = 20;
        String str = "";
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            switch (context.getResources().getDisplayMetrics().densityDpi) {
                case DisplayMetrics.DENSITY_LOW:
                    str = "small-ldpi";
                    // Log.e("small 1","small-ldpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    str = "small-mdpi";
                    // Log.e("small 1","small-mdpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    str = "small-hdpi";
                    // Log.e("small 1","small-hdpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    str = "small-xhdpi";
                    // Log.e("small 1","small-xhdpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    str = "small-xxhdpi";
                    // Log.e("small 1","small-xxhdpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    str = "small-xxxhdpi";
                    //Log.e("small 1","small-xxxhdpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_TV:
                    str = "small-tvdpi";
                    // Log.e("small 1","small-tvdpi");
                    value = 20;
                    break;
                default:
                    str = "small-unknown";
                    value = 20;
                    break;
            }

        } else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            switch (context.getResources().getDisplayMetrics().densityDpi) {
                case DisplayMetrics.DENSITY_LOW:
                    str = "normal-ldpi";
                    // Log.e("normal-ldpi 1","normal-ldpi");
                    str_device = "normal-ldpi";
                    value = 82;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    // Log.e("normal-mdpi 1","normal-mdpi");
                    str = "normal-mdpi";
                    value = 82;
                    str_device = "normal-mdpi";
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    // Log.e("normal-hdpi 1","normal-hdpi");
                    str = "normal-hdpi";
                    str_device = "normal-hdpi";
                    value = 82;
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    //Log.e("normal-xhdpi 1","normal-xhdpi");
                    str = "normal-xhdpi";
                    str_device = "normal-xhdpi";
                    value = 90;
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    // Log.e("normal-xxhdpi 1","normal-xxhdpi");
                    str = "normal-xxhdpi";
                    str_device = "normal-xxhdpi";
                    value = 96;
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    //Log.e("normal-xxxhdpi","normal-xxxhdpi");
                    str = "normal-xxxhdpi";
                    str_device = "normal-xxxhdpi";
                    value = 96;
                    break;
                case DisplayMetrics.DENSITY_TV:
                    //Log.e("DENSITY_TV 1","normal-mdpi");
                    str = "normal-tvdpi";
                    str_device = "normal-tvmdpi";
                    value = 96;
                    break;
                default:
                    // Log.e("normal-unknown","normal-unknown");
                    str = "normal-unknown";
                    str_device = "normal-unknown";
                    value = 82;
                    break;
            }
        } else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            switch (context.getResources().getDisplayMetrics().densityDpi) {
                case DisplayMetrics.DENSITY_LOW:
                    str = "large-ldpi";
                    // Log.e("large-ldpi 1","normal-ldpi");
                    value = 78;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    str = "large-mdpi";
                    //Log.e("large-ldpi 1","normal-mdpi");
                    value = 78;
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    //Log.e("large-ldpi 1","normal-hdpi");
                    str = "large-hdpi";
                    value = 78;
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    // Log.e("large-ldpi 1","normal-xhdpi");
                    str = "large-xhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    //Log.e("large-ldpi 1","normal-xxhdpi");
                    str = "large-xxhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    // Log.e("large-ldpi 1","normal-xxxhdpi");
                    str = "large-xxxhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_TV:
                    //Log.e("large-ldpi 1","normal-tvdpi");
                    str = "large-tvdpi";
                    value = 125;
                    break;
                default:
                    str = "large-unknown";
                    value = 78;
                    break;
            }

        } else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            switch (context.getResources().getDisplayMetrics().densityDpi) {
                case DisplayMetrics.DENSITY_LOW:
                    // Log.e("large-ldpi 1","normal-ldpi");
                    str = "xlarge-ldpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    // Log.e("large-ldpi 1","normal-mdpi");
                    str = "xlarge-mdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    //Log.e("large-ldpi 1","normal-hdpi");
                    str = "xlarge-hdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    // Log.e("large-ldpi 1","normal-hdpi");
                    str = "xlarge-xhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    // Log.e("large-ldpi 1","normal-xxhdpi");
                    str = "xlarge-xxhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    // Log.e("large-ldpi 1","normal-xxxhdpi");
                    str = "xlarge-xxxhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_TV:
                    //Log.e("large-ldpi 1","normal-tvdpi");
                    str = "xlarge-tvdpi";
                    value = 125;
                    break;
                default:
                    str = "xlarge-unknown";
                    value = 125;
                    break;
            }
        }

        return value;
    }

}

