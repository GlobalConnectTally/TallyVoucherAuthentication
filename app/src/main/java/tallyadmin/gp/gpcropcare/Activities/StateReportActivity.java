package tallyadmin.gp.gpcropcare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tallyadmin.gp.gpcropcare.Adapter.ItemAdapter;
import tallyadmin.gp.gpcropcare.Adapter.StateAdapter;
import tallyadmin.gp.gpcropcare.Model.Item;
import tallyadmin.gp.gpcropcare.Model.State;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.ThreadManager;
import tallyadmin.gp.gpcropcare.room.RoomRepository;

public class StateReportActivity extends AppCompatActivity
{
    private RoomRepository roomRepository;
    String CmpShortNameValue;
    TextView stateNameTextView, totalOpeningTextView,totalInwardTextView,totalOutwardTextView,totalClosingTextView;
    Context context;
    List<Item> items;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ItemAdapter itemAdapter;
    String ItemParent;

    int totalOpening =0;
    int totalInward  =0;
    int totalOutward =0;
    int totalClosing =0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_report);
        context = this;

        Toolbar toolbar = findViewById(R.id.toolbarStateReport);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("State Report");

        if (getIntent().getStringExtra("CmpShortName")== null || getIntent().getStringExtra("CmpShortName").equals("") )
        {
            CmpShortNameValue = "NO Name";
        } else {
            CmpShortNameValue = getIntent().getStringExtra("CmpShortName");
        }

        ItemParent = getIntent().getStringExtra("ItemParent");

        stateNameTextView = findViewById(R.id.stateNameId);
        stateNameTextView.setText(CmpShortNameValue);

        roomRepository = new RoomRepository(this);
        getItemsByCompany(CmpShortNameValue,ItemParent);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.stateReportRecyclerView);

        totalOpeningTextView = findViewById(R.id.totalOpen);
        totalInwardTextView  = findViewById(R.id.totalInward);
        totalOutwardTextView = findViewById(R.id.totalOutward);
        totalClosingTextView = findViewById(R.id.totalClosing);

    }

    public void populateData(List<Item> items)
    {
        itemAdapter = new ItemAdapter(items);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemAdapter);

        totalOpeningTextView.setText(String.valueOf(totalOpening));
        totalInwardTextView.setText(String.valueOf(totalInward));
        totalOutwardTextView.setText(String.valueOf(totalOutward));
        totalClosingTextView.setText(String.valueOf(totalClosing));
    }

    private void getItemsByCompany(String cmpShortName, String ItemParent)
    {
        items = new ArrayList<Item>();

        ThreadManager.getInstance(context).executeTask(new Runnable() {
            @Override
            public void run()
            {
                items = roomRepository.getItemsByCompanyAndParent(cmpShortName,ItemParent);

                if (items != null)
                {
                    for (Item item:items)
                    {
                         totalOpening += Integer.valueOf(item.getItemOpening());
                         totalInward  += Integer.valueOf(item.getItemInwards());
                         totalOutward += Integer.valueOf(item.getItemOutwards());
                         totalClosing += Integer.valueOf(item.getItemClosing());
                    }

                    populateData(items);

                }
                else
                {
                    Log.d("Error-isInserted::" ,"No items");
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}