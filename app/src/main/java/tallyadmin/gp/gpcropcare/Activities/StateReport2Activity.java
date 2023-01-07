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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import tallyadmin.gp.gpcropcare.Adapter.ItemAdapter;
import tallyadmin.gp.gpcropcare.Model.ItemListModel;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.ThreadManager;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.room.RoomRepository;

public class StateReport2Activity extends AppCompatActivity {

    private RoomRepository roomRepository;
    String CmpShortNameValue;
    TextView stateNameTextView, totalOpeningTextView,totalInwardTextView,totalOutwardTextView,totalClosingTextView;
    Context context;
    List<ItemListModel> items;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ItemAdapter itemAdapter;
    String ItemParent;

    double totalOpening = 0.0;
    double totalInward  = 0.0;
    double totalOutward = 0.0;
    double totalClosing = 0.0;

    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_report2);

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

        userInfo = new UserInfo(this);

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

    public void populateData(List<ItemListModel> items)
    {

        itemAdapter = new ItemAdapter(items);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemAdapter);

        totalOpeningTextView.setText(toTowDecimalPlaces(String.valueOf(totalOpening)));
        totalInwardTextView.setText(toTowDecimalPlaces(String.valueOf(totalInward)));
        totalOutwardTextView.setText(toTowDecimalPlaces(String.valueOf(totalOutward)));
        totalClosingTextView.setText(toTowDecimalPlaces(String.valueOf(totalClosing)));

    }

    private String toTowDecimalPlaces(String data)
    {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return  numberFormat.format(Double.parseDouble(data));
    }

    private void getItemsByCompany(String cmpShortName, String ItemParent)
    {

        items = new ArrayList<ItemListModel>();

        ThreadManager.getInstance(context).executeTask(new Runnable() {
            @Override
            public void run()
            {
                items = roomRepository.getItemsByCompanyAndParent(cmpShortName,ItemParent,userInfo.getAppLoginUserID());

                if (items != null)
                {
                    for (ItemListModel item:items) {
                        totalOpening += Double.parseDouble(item.getItemOpening().replace(",","").replace("(-)","-"));
                        totalInward  += Double.parseDouble(item.getItemInwards().replace(",","").replace("(-)","-"));
                        totalOutward += Double.parseDouble(item.getItemOutwards().replace(",","").replace("(-)","-"));
                        totalClosing += Double.parseDouble(item.getItemClosing().replace(",","").replace("(-)","-"));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateData(items);
                        }
                    });

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