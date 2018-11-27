package tkgd.homemanagement.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;
import tkgd.homemanagement.Adapter.DeviceAdapter;
import tkgd.homemanagement.Adapter.PageAdapter;
import tkgd.homemanagement.Model.Device;
import tkgd.homemanagement.Model.Room;
import tkgd.homemanagement.R;
import tkgd.homemanagement.RoomPhotoFragment;
import tkgd.homemanagement.Utility.CustomViewPager;
import tkgd.homemanagement.Utility.ExpandingViewPagerTransformer;
import tkgd.homemanagement.Utility.FixAppBarLayoutBehavior;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

import static tkgd.homemanagement.Utility.DeviceSetting.differentDensityAndScreenSize;

public class RoomActivity extends AppCompatActivity {
    private Button manage;
    private ArrayList<Room> rooms;
    private ArrayList<Device> devices;
    private RecyclerView deviceReyclerView;
    private DeviceAdapter deviceAdapter;
    private CustomViewPager pager;
    private PageAdapter obj_adapter;
    private ItemTouchHelper ith;
    private String str_device;
    private View mProgressView;
    static Context mContext;
    private TextView textBadge;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    static public String systemid;
    private FirebaseFirestore firebaseFirestore;
    private ShimmerLayout shimmerLayout;
    static public String curroomid;
    private ItemTouchHelper.Callback _ithCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        final Bundle extras = getIntent().getExtras();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        devices = new ArrayList<Device>(0);
        mContext = RoomActivity.this;
        mProgressView = findViewById(R.id.progress);
        pager = (CustomViewPager) findViewById(R.id.viewpager);
        shimmerLayout = findViewById(R.id.shimmer_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).setBehavior(new FixAppBarLayoutBehavior());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
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
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        str_device = differentDensityAndScreenSize(getApplicationContext());
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
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == rooms.size()) {
                    shimmerLayout.setVisibility(View.GONE);
                    collapsingToolbarLayout.setTitle(" New room");
                    deviceReyclerView.setVisibility(View.GONE);
                } else {
                    shimmerLayout.startShimmerAnimation();
                    shimmerLayout.setVisibility(View.VISIBLE);
                    deviceReyclerView.setVisibility(View.GONE);
                    collapsingToolbarLayout.setTitle(rooms.get(position).getName());
                    curroomid = rooms.get(position).getId();
                    pager.disableScroll(true);
                    FetchDevices();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        if (extras != null) {
            systemid = extras.getString("systemid");
        }
        rooms = new ArrayList<>(0);
        firebaseFirestore = FirebaseFirestore.getInstance();
        showProgress(true);
        shimmerLayout.startShimmerAnimation();
        shimmerLayout.setVisibility(View.VISIBLE);

        firebaseFirestore.collection("rooms")
                .whereEqualTo("systemid", systemid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                rooms = new ArrayList<>(0);
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Room temproom = document.toObject(Room.class);
                                    temproom.setPhotoID(R.drawable.livingroom);
                                    temproom.setId(document.getId());
                                    rooms.add(temproom);
                                    collapsingToolbarLayout.setTitle(rooms.get(pager.getCurrentItem()).getName());
                                    curroomid = rooms.get(0).getId();
                                }
                                FetchDevices();
                            } else {
                                collapsingToolbarLayout.setTitle("New room");
                                shimmerLayout.setVisibility(View.GONE);
                                pager.setCurrentItem(0);
                            }
                            List<Fragment> fragments = getFragments();
                            pager.setAdapter(new PageAdapter(getSupportFragmentManager(), fragments, 0));
                            if (extras != null) {
                                if (extras.getString("ROOM_NAME") != null)
                                    pager.setCurrentItem(rooms.size() - 1, true);
                            }
                            showProgress(false);

                        } else {
                            Log.d("Firebase", "Error getting system: ", task.getException());
                        }
                    }
                });

        deviceReyclerView = (RecyclerView) findViewById(R.id.device_recyclerview);
        deviceReyclerView.setItemAnimator(new DefaultItemAnimator());
        deviceReyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        deviceReyclerView.addItemDecoration(itemDecoration);


    }

    public ItemTouchHelper.Callback get_ithCallback() {
        return new ItemTouchHelper.Callback() {
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                // We only want the active item

                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    if (viewHolder.getAdapterPosition() == devices.size())
                        return;
                    viewHolder.itemView.findViewById(R.id.wrapLayout).setBackgroundColor(getColor(R.color.light_grey));
                }
                super.onSelectedChanged(viewHolder, actionState);

            }

            @Override
            public void clearView(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == devices.size())
                    return;
                super.clearView(recyclerView, viewHolder);

                viewHolder.itemView.findViewById(R.id.wrapLayout).setBackground(getDrawable(R.drawable.background_gradient));
            }

            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                if (target.getAdapterPosition() == devices.size() || viewHolder.getAdapterPosition() == devices.size())
                    return true;
                Collections.swap(devices, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                deviceAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //TODO
            }

            //defines the enabled move directions in each state (idle, swiping, dragging).
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }
        };
    }

    public void FetchDevices() {
        firebaseFirestore.collection("devices")
                .whereEqualTo("roomid", rooms.get(pager.getCurrentItem()).getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        shimmerLayout.setVisibility(View.GONE);
                        deviceReyclerView.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            pager.disableScroll(false);
                            devices.clear();
                            if (task.getResult().size() != 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Device device = document.toObject(Device.class);
                                    devices.add(device);
                                }

                            }
                            deviceAdapter = new DeviceAdapter(devices, curroomid, RoomActivity.this, 1);
                            deviceReyclerView.setAdapter(deviceAdapter);
                            ith = new ItemTouchHelper(get_ithCallback());
                            ith.attachToRecyclerView(deviceReyclerView);

                        } else {
                            Log.d("Firebase", "Error getting devices: ", task.getException());
                        }
                    }
                });

    }

    private RecyclerView.Adapter getCurentAdapter() {
        return deviceAdapter;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            pager.setVisibility(show ? View.GONE : View.VISIBLE);
            pager.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pager.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pager.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
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
        for (Room room : rooms) {
            fList.add(RoomPhotoFragment.newInstance(room.getName(), R.drawable.kitchen, systemid));
        }
        fList.add(RoomPhotoFragment.newInstance("NEW_ROOM", R.drawable.plus_circle_outline, systemid));
        return fList;
    }

}

