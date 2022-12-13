package tallyadmin.gp.gpcropcare.Activities;


import static tallyadmin.gp.gpcropcare.Common.Common.URL_ITEMS;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_LOGIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tallyadmin.gp.gpcropcare.Adapter.StateAdapter;
import tallyadmin.gp.gpcropcare.LoginActivity;
import tallyadmin.gp.gpcropcare.Model.Company;
import tallyadmin.gp.gpcropcare.Model.Item;
import tallyadmin.gp.gpcropcare.Model.State;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.ThreadManager;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import tallyadmin.gp.gpcropcare.room.RoomRepository;

public class StockReportActivity extends AppCompatActivity implements StateAdapter.OnStateAdapterListener
{
    private RoomRepository roomRepository;
    Companysave companyData;
    ArrayList<Item> items;
    ArrayList<State> states;
    Context context;
    RecyclerView stateRecyclerView;
    LinearLayoutManager linearLayoutManager;
    StateAdapter stateAdapter;
    SearchView searchView;
    String[] CmpShortNameArray = {"GS","GJ","AS","CH","BI","MA","MP","OD","RJ","WB","UP"};



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_report);
        context = this;


        Toolbar toolbar = findViewById(R.id.toolbarStockReport);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Stock Report");

        searchView = findViewById(R.id.searchViewId);

        states = new ArrayList<>();

        roomRepository = new RoomRepository(this);
        companyData = new Companysave(this);
        System.out.println("CmpShortName-1: "+companyData.getCmpShortName());


        getItemsFromApi();

        getStatesByCompany();

        linearLayoutManager = new LinearLayoutManager(this);
        stateRecyclerView = findViewById(R.id.stateRecyclerView);
        stateRecyclerView.setLayoutManager(linearLayoutManager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                //stateAdapter.getFilter().filter(query);
                filterByItemParent(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                //stateAdapter.getFilter().filter(newText);
                filterByItemParent(newText);
                return false;
            }
        });

    }

    private void filterByItemParent(String query) {

        ThreadManager.getInstance(this).executeTask(new Runnable() {
            @Override
            public void run() {
                System.out.println(query);
                String cmpShortName = companyData.getCmpShortName();
                List<Item> itemList = roomRepository.getItemByParentName(query);

                System.out.println(itemList);

                if (itemList.size() == 0){

                    runOnUiThread(() -> {
                         states.clear();
                         initializeAdapter(states);
                    });

                }else {
                    manipulateTheDatas(itemList,cmpShortName);
                }
            }
        });
    }

    private void getStates()
    {
        for(String s:CmpShortNameArray)
        {
            Log.d("cname: ",s);
            getStatesByCompany();
        }
    }

    private void initializeAdapter(ArrayList<State> states){
        stateAdapter = new StateAdapter(states, this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stateRecyclerView.setAdapter(stateAdapter);
            }
        });
    }

    private void getItemsFromApi()
    {

        final KProgressHUD Hhdprogress = KProgressHUD.create(StockReportActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);


        Hhdprogress.setCancellable(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                finish();
            }
        });

        Hhdprogress.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_ITEMS,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject obj = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("StockDetails");
                            Log.d("Items Response::", dataArray.toString());

                            if (dataArray.length() != 0)
                            {
                                Hhdprogress.dismiss();

                                ThreadManager.getInstance(context).executeTask(new Runnable() {
                                    @Override
                                    public void run()
                                    {
                                        for (int i = 0; i < dataArray.length(); i++)
                                        {
                                            Item item = new Item();
                                            JSONObject itemJson = null;

                                            try {
                                                itemJson = dataArray.getJSONObject(i);
                                                item.setCmpShortName(itemJson.getString("CmpShortName"));
                                                item.setItemName(itemJson.getString("ItemName"));
                                                item.setItemParent(itemJson.getString("ItemParent"));
                                                item.setItemOpening(itemJson.getString("ItemOpening"));
                                                item.setItemInwards(itemJson.getString("ItemInwards"));
                                                item.setItemOutwards(itemJson.getString("ItemOutwards"));
                                                item.setItemClosing(itemJson.getString("ItemClosing"));
                                                items.add(item);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        roomRepository.deleteItems();

                                        long[] isInserted = roomRepository.insertItemsToRoom(items);
                                        getStatesByCompany();

                                        if (isInserted != null)
                                        {
                                            getStatesByCompany();
                                            Log.d("Result Message: " ,"Data Successful Loaded to Room Database");
                                        }
                                        else
                                        {
                                            Log.d("Result Message: " ,"Fail to Load Data to Room Database");
                                        }
                                    }
                                });
                            }
                            else
                            {
                                ThreadManager.getInstance(context).executeTask(new Runnable() {
                                    @Override
                                    public void run() {
                                        roomRepository.deleteItems();
                                    }
                                });

                                Hhdprogress.dismiss();
                                Toast.makeText(StockReportActivity.this, "No items Found", Toast.LENGTH_LONG).show();
                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.getMessage() == null ? "" : error.getMessage(), Toast.LENGTH_SHORT).show();
                        Hhdprogress.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                //cmpShortName
                String cmpShortName = companyData.getCmpShortName();
                Map<String, String> params = new HashMap<>();
                params.put("cmpShortName", cmpShortName);
                return params;
            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getAllItemsFromApi()
    {
        final KProgressHUD Hhdprogress = KProgressHUD.create(StockReportActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);


        Hhdprogress.setCancellable(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                finish();
            }
        });

        Hhdprogress.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_ITEMS,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject obj = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("StockDetails");
                            Log.d("Items Response::", dataArray.toString());

                            if (dataArray.length() != 0)
                            {
                                Hhdprogress.dismiss();

                                for (int i = 0; i < dataArray.length(); i++)
                                {

                                    Item item = new Item();

                                    JSONObject itemJson = dataArray.getJSONObject(i);
                                    item.setCmpShortName(itemJson.getString("CmpShortName"));
                                    item.setItemName(itemJson.getString("ItemName"));
                                    item.setItemParent(itemJson.getString("ItemParent"));
                                    item.setItemOpening(itemJson.getString("ItemOpening"));
                                    item.setItemInwards(itemJson.getString("ItemInwards"));
                                    item.setItemOutwards(itemJson.getString("ItemOutwards"));
                                    item.setItemClosing(itemJson.getString("ItemClosing"));
                                    items.add(item);
                                }

                                ThreadManager.getInstance(context).executeTask(new Runnable() {
                                    @Override
                                    public void run()
                                    {
                                        roomRepository.deleteItems();
                                        long[] isInserted = roomRepository.insertItemsToRoom(items);
                                        if (isInserted != null)
                                        {
                                            Log.d("Result Message: " ,"Data Successful Loaded to Room Database");
                                        }
                                        else
                                        {
                                            Log.d("Result Message: " ,"Fail to Load Data to Room Database");
                                        }
                                    }
                                });
                            }
                            else
                            {
                                ThreadManager.getInstance(context).executeTask(new Runnable() {
                                    @Override
                                    public void run() {
                                        roomRepository.deleteItems();
                                    }
                                });

                                Hhdprogress.dismiss();
                                Toast.makeText(StockReportActivity.this, "No items From Api", Toast.LENGTH_LONG).show();
                            }

                        }
                        catch (JSONException e)
                        {
                            Log.d("INSIDE","4");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.getMessage() == null ? "" : error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("cmpShortName", "All");
                return params;
            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getStatesByCompany()
    {
        ThreadManager.getInstance(context).executeTask(new Runnable() {
            @Override
            public void run()
            {
                String cmpShortName = companyData.getCmpShortName();
                List<Item> itemsByCompany = roomRepository.getItemsByCompany(cmpShortName);
                if (itemsByCompany.size() == 0){
                     states.clear();
                     initializeAdapter(states);
                }else {
                    manipulateTheDatas(itemsByCompany,cmpShortName);
                }
            }
        });

    }

    private void manipulateTheDatas(List<Item> itemsByCompany,String cmpShortName) {

           /*Lamda function */
        runOnUiThread( () -> {
            /* Clear ArrayList t Avoid Duplication*/
            states.clear();
            initializeAdapter(states);
        });

        System.out.println("Thread:: " + Thread.currentThread().getName());
        int totalValue =0;
        State state = new State();
        state.setCmpShortName(cmpShortName);
        if (itemsByCompany != null && itemsByCompany.size()!=0)
        {
            for (Item item:itemsByCompany)
            {
                if (item.getItemName().equalsIgnoreCase("1LTR"))
                {
                    state.setOneLtr(item.getItemOpening());
                    Log.d("INSIDE 5: ",item.getItemOpening());
                    totalValue+=Integer.valueOf(item.getItemOpening());
                }
                else if (item.getItemName().equalsIgnoreCase("500 ML"))
                {
                    state.setFiveHundredMl(item.getItemOpening());
                    totalValue+=Integer.valueOf(item.getItemOpening());
                }
                else if (item.getItemName().equalsIgnoreCase("250 ML"))
                {
                    state.setTwoFiftyMl(item.getItemOpening());
                    totalValue+=Integer.valueOf(item.getItemOpening());
                }
                else if (item.getItemName().equalsIgnoreCase("100 ML"))
                {
                    state.setOneHundredMl(item.getItemOpening());
                    totalValue+=Integer.valueOf(item.getItemOpening());
                }
                else if (item.getItemName().equalsIgnoreCase("20 ML"))
                {
                    state.setTwentyMl(item.getItemOpening());
                    totalValue+=Integer.valueOf(item.getItemOpening());
                }
                else
                {

                }
            }
            state.setTotalMl(String.valueOf(totalValue));

            runOnUiThread(() -> {

                states.clear();

                System.out.println("Thread:: - 1 " + Thread.currentThread().getName());
                states.add(state);

                System.out.println("Data:: - 1 " + states.toString());
                System.out.println("Total:: - 1 " + String.valueOf(states.size()));

                initializeAdapter(states);

            });

        }
        else
        {
            Log.d("Error Message:: " ,"No items for "+cmpShortName+"From Room Database");
        }
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

    @Override
    public void onClickState(int position, List<State> states)
    {
        Intent intent = new Intent(StockReportActivity.this, StateReportActivity.class);
        intent.putExtra("CmpShortName",states.get(position).getCmpShortName());
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        super.onPointerCaptureChanged(hasCapture);
    }
}