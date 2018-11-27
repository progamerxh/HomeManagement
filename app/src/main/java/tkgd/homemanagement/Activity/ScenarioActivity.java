package tkgd.homemanagement.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import io.supercharge.shimmerlayout.ShimmerLayout;
import tkgd.homemanagement.Adapter.CardViewAdapter;
import tkgd.homemanagement.Model.ScenarioItem;
import tkgd.homemanagement.R;
import tkgd.homemanagement.Utility.ItemOffsetDecoration;

public class ScenarioActivity extends AppCompatActivity {
    private RecyclerView scenariorecycleview;
    static Context mContext;
    private ImageButton btnExpand;
    private FrameLayout roomsnav;
    static public String systemid;
    private ShimmerLayout shimmerLayout;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<ScenarioItem> finalScenarioItems;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mContext = getApplicationContext();
        setContentView(R.layout.activity_scenario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.account_outline));

        roomsnav = (FrameLayout) findViewById(R.id.roomFrame);


        shimmerLayout = findViewById(R.id.shimmer_layout);
        shimmerLayout.startShimmerAnimation();
        shimmerLayout.setVisibility(View.VISIBLE);
        scenariorecycleview = (RecyclerView) findViewById(R.id.scenario_recycleview);
        final LinearLayoutManager layoutManagerScenarios = new LinearLayoutManager(ScenarioActivity.this, LinearLayoutManager.HORIZONTAL, false);
        scenariorecycleview.setItemAnimator(new DefaultItemAnimator());
        scenariorecycleview.setLayoutManager(layoutManagerScenarios);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.room_device_cardview_margin);
        scenariorecycleview.addItemDecoration(itemDecoration);
        finalScenarioItems = new ArrayList<>(0);
        firebaseFirestore = FirebaseFirestore.getInstance();
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            systemid = extras.getString("systemid");
            roomsnav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(mContext, RoomActivity.class);
                    myIntent.putExtra("systemid", systemid);
                    startActivity(myIntent);
                }
            });
            FetchScenarios();
        } else
            FetchSystemId();

        btnExpand = (ImageButton) findViewById(R.id.imgExpand);
        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnExpand.setSelected(!btnExpand.isSelected());
                if (btnExpand.isSelected()) {
                    scenariorecycleview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                } else
                    scenariorecycleview.setLayoutManager(layoutManagerScenarios);

            }
        });

    }

    public void FetchScenarios() {
        firebaseFirestore.collection("scenarios")
                .whereEqualTo("systemid", systemid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ScenarioItem scenarioItem = document.toObject(ScenarioItem.class);
                                    scenarioItem.setSelected(false);
                                    scenarioItem.setId(document.getId());
                                    finalScenarioItems.add(scenarioItem);
                                }
                                CardViewAdapter scenarioAdapter = new CardViewAdapter(finalScenarioItems, ScenarioActivity.this, 1);
                                scenariorecycleview.setAdapter(scenarioAdapter);
                                shimmerLayout.setVisibility(View.GONE);
                                scenariorecycleview.setVisibility(View.VISIBLE);

                            } else {
                                CardViewAdapter scenarioAdapter = new CardViewAdapter(finalScenarioItems, ScenarioActivity.this, 1);
                                scenariorecycleview.setAdapter(scenarioAdapter);
                                shimmerLayout.setVisibility(View.GONE);
                                scenariorecycleview.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.d("Firebase", "Error getting system: ", task.getException());
                        }
                    }
                });
    }

    public void FetchSystemId() {
        firebaseFirestore.collection("systems")
                .whereEqualTo("userid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                systemid = task.getResult().getDocuments().get(0).getId();
                                roomsnav.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent myIntent = new Intent(mContext, RoomActivity.class);
                                        myIntent.putExtra("systemid", systemid);
                                        startActivity(myIntent);
                                    }
                                });
                                FetchScenarios();
                            } else {
                                CardViewAdapter scenarioAdapter = new CardViewAdapter(finalScenarioItems, ScenarioActivity.this, 1);
                                scenariorecycleview.setAdapter(scenarioAdapter);
                                shimmerLayout.setVisibility(View.GONE);
                                scenariorecycleview.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.d("Firebase", "Error getting system: ", task.getException());
                        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent myIntent = new Intent(mContext, ProfileActivity.class);
                startActivity(myIntent);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

     public String getSystemid()
    {
        return systemid;
    }


}
