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
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import tallyadmin.gp.gpcropcare.Adapter.StateAdapter;
import tallyadmin.gp.gpcropcare.LoginActivity;

import tallyadmin.gp.gpcropcare.Model.Company;
import tallyadmin.gp.gpcropcare.Model.Item;
import tallyadmin.gp.gpcropcare.Model.ListOfCompanyShortName;
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
    String[] CmpShortNameArray = new String[]{"GS","GJ","AS","CH","BI","MA","MP","OD","RJ","WB","UP"};
    HorizontalScrollView horizontalScrollView;
    LinearLayout linearLayoutError;
    List<ListOfCompanyShortName> cmpShortNameList;


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

        cmpShortNameList = new ArrayList<>();

        roomRepository = new RoomRepository(this);
        companyData = new Companysave(this);
        System.out.println("CmpShortName-1: "+companyData.getCmpShortName());

        horizontalScrollView = findViewById(R.id.horizontalView);
        linearLayoutError = findViewById(R.id.errorMessage);

        horizontalScrollView.setVisibility(View.GONE);
        linearLayoutError.setVisibility(View.VISIBLE);


        getItemsFromApi();
        //getItemsFromApiaLL();
        //getStatesByCompany();
        //getCompanyShortNames();

        linearLayoutManager = new LinearLayoutManager(this);
        stateRecyclerView = findViewById(R.id.stateRecyclerView);
        stateRecyclerView.setLayoutManager(linearLayoutManager);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showItemDialog();
            }
        });

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

    private void getCompanyShortNames(){

        ThreadManager.getInstance(this).executeTask(new Runnable() {
            @Override
            public void run() {
                cmpShortNameList =  roomRepository.getCompanyShortNames();
                System.out.println(cmpShortNameList.toString());
            }
        });
    }

    private void showItemDialog() {
    }

    private void filterByItemParent(String query) {

        ThreadManager.getInstance(this).executeTask(new Runnable() {
            @Override
            public void run() {

                System.out.println(query);
                JSONArray jsonArray = new JSONArray();
                String cmpShortName = companyData.getCmpShortName();

                if (cmpShortName.toUpperCase().equalsIgnoreCase("ALL"))
                {

                    List<Item> itemList1 = new ArrayList<>();
                    for (ListOfCompanyShortName list: cmpShortNameList) {
                          System.out.println(list.getCmpShortName());
                          itemList1 = roomRepository.getItemsByCompanyAndParent(
                                  list.getCmpShortName().toUpperCase(Locale.ROOT),
                                  query.toUpperCase(Locale.ROOT)
                          );
                          System.out.println(itemList1.toString());
                        try {

                            JSONObject data =  doSomeOperations(itemList1,list.getCmpShortName());;
                            if (data.length() != 0){
                                jsonArray.put(data);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {

                    List<Item> itemList = roomRepository.getItemByParentName(query);
                    try {
                       JSONObject data = doSomeOperations(itemList,cmpShortName);
                        if (data.length() != 0){
                            jsonArray.put(data);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                System.out.println("States-D");
                System.out.println(jsonArray);

                populateDataToRecycler(jsonArray);
            }
        });
    }

    private void populateDataToRecycler(JSONArray jsonArray) {

        if (jsonArray.length() != 0){

            for (int i = 0; i < jsonArray.length(); i++){

                State state = new State();

                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject != null){
                        if (jsonObject.has("CmpShortName")){
                            state.setCmpShortName(jsonObject.getString("CmpShortName"));
                        }

                        if (jsonObject.has("ItemParent")){
                            state.setItemParent(jsonObject.getString("ItemParent"));
                        }

                        if (jsonObject.has("FiveHundredMl")){
                            state.setFiveHundredMl(String.valueOf(jsonObject.getInt("FiveHundredMl")));
                        }

                        if (jsonObject.has("OneHundredMl")){
                            state.setOneHundredMl(String.valueOf(jsonObject.getString("OneHundredMl")));
                        }

                        if (jsonObject.has("OneLtr")){
                            state.setOneLtr(String.valueOf(jsonObject.getString("OneLtr")));
                        }

                        if (jsonObject.has("TwoFiftyMl")){
                            state.setTwoFiftyMl(String.valueOf(jsonObject.getString("TwoFiftyMl")));
                        }

                        if (jsonObject.has("TwentyMl")){
                            state.setTwentyMl(String.valueOf(jsonObject.getString("TwentyMl")));
                        }

                        if (jsonObject.has("TotalMl")){
                            state.setTotalMl(String.valueOf(jsonObject.getString("TotalMl")));
                        }
                    }

                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          states.add(state);
                      }
                  });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Datas::");
        //System.out.println(states.toString());

        runOnUiThread(() -> {
            initializeAdapter(states);
        });
    }

    private JSONObject doSomeOperations(List<Item> itemList , String cmpShortName) throws JSONException {

        if (itemList.size() == 0){

            runOnUiThread(() -> {
                horizontalScrollView.setVisibility(View.INVISIBLE);
                linearLayoutError.setVisibility(View.VISIBLE);
                states.clear();
                initializeAdapter(states);
            });

            return new JSONObject();

        }else {
            return manipulateTheDatas(itemList,cmpShortName);
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

       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               Hhdprogress.show();
           }
       });

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
                                                item.setItemParent(itemJson.getString("ItemParent").toUpperCase(Locale.ROOT));

                                                if (itemJson.getString("ItemOpening").isEmpty()){
                                                    item.setItemOpening("0");
                                                }else {
                                                    item.setItemOpening(itemJson.getString("ItemOpening"));
                                                }

                                                if (itemJson.getString("ItemInwards").isEmpty()){
                                                    item.setItemInwards("0");

                                                }else {
                                                    item.setItemInwards(itemJson.getString("ItemInwards"));
                                                }

                                                if (itemJson.getString("ItemOutwards").isEmpty()){
                                                    item.setItemOutwards( "0" );
                                                }else {
                                                    item.setItemOutwards( itemJson.getString("ItemOutwards"));
                                                }

                                                if (itemJson.getString("ItemClosing").isEmpty()){
                                                    item.setItemClosing("0" );
                                                }else {
                                                    item.setItemClosing( itemJson.getString("ItemClosing"));
                                                }

                                                items.add(item);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        System.out.println(items.toString());

                                        roomRepository.deleteItems();

                                        long[] isInserted = roomRepository.insertItemsToRoom(items);
                                       // getStatesByCompany();

                                        if (isInserted != null)
                                        {
                                           // getStatesByCompany();
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
                        }catch (Exception e){
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

    private void getItemsFromApiaLL()
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

       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               Hhdprogress.show();
           }
       });

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
                                                item.setItemParent(itemJson.getString("ItemParent").toUpperCase(Locale.ROOT));

                                                if (itemJson.getString("ItemOpening").isEmpty()){
                                                    item.setItemOpening("0");
                                                }else {
                                                    item.setItemOpening(itemJson.getString("ItemOpening"));
                                                }

                                                if (itemJson.getString("ItemInwards").isEmpty()){
                                                    item.setItemInwards("0");

                                                }else {
                                                    item.setItemInwards(itemJson.getString("ItemInwards"));
                                                }

                                                if (itemJson.getString("ItemOutwards").isEmpty()){
                                                    item.setItemOutwards( "0" );
                                                }else {
                                                    item.setItemOutwards( itemJson.getString("ItemOutwards"));
                                                }

                                                if (itemJson.getString("ItemClosing").isEmpty()){
                                                    item.setItemClosing("0" );
                                                }else {
                                                    item.setItemClosing( itemJson.getString("ItemClosing"));
                                                }

                                                items.add(item);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        System.out.println(items.toString());

                                        roomRepository.deleteItems();

                                        long[] isInserted = roomRepository.insertItemsToRoom(items);
                                        getCompanyShortNames();

                                        if (isInserted != null)
                                        {
                                            getCompanyShortNames();
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
                        }catch (Exception e){
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
                params.put("cmpShortName", "ALL");
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
                    try {
                        JSONObject i = manipulateTheDatas(itemsByCompany,cmpShortName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private JSONObject manipulateTheDatas(List<Item> itemsByCompany,String cmpShortName) throws JSONException {

           /*Lamda function */
        runOnUiThread( () -> {
            /* Clear ArrayList t Avoid Duplication*/
            horizontalScrollView.setVisibility(View.VISIBLE);
            linearLayoutError.setVisibility(View.GONE);
            states.clear();
            initializeAdapter(states);
        });

        int totalValue =0;
        JSONObject jsonObject = new JSONObject();;
        if (itemsByCompany != null && itemsByCompany.size() != 0)
        {
            for (Item item: itemsByCompany)
            {
                jsonObject.put("CmpShortName" , item.getCmpShortName());
                jsonObject.put("ItemParent" , item.getItemParent());

                if (item.getItemName().equalsIgnoreCase("1LTR"))
                {
                    jsonObject.put("OneLtr" , item.getItemClosing());
                    totalValue += Integer.valueOf(item.getItemClosing());
                }
                else if (item.getItemName().equalsIgnoreCase("500 ML"))
                {
                    jsonObject.put("FiveHundredMl" , item.getItemClosing());
                    totalValue += Integer.valueOf(item.getItemClosing());
                }
                else if (item.getItemName().equalsIgnoreCase("250 ML"))
                {
                    jsonObject.put("TwoFiftyMl" , item.getItemClosing());
                    totalValue += Integer.valueOf(item.getItemClosing());
                }
                else if (item.getItemName().equalsIgnoreCase("100 ML"))
                {
                    jsonObject.put("OneHundredMl" , item.getItemClosing());
                    totalValue += Integer.valueOf(item.getItemClosing());
                }
                else if (item.getItemName().equalsIgnoreCase("20 ML"))
                {
                    jsonObject.put("TwentyMl" , item.getItemClosing());
                    totalValue += Integer.valueOf(item.getItemClosing());
                }
                else {}
            }
            jsonObject.put("TotalMl" , String.valueOf(totalValue));

        }
        else
        {
            Log.d("Error Message:: " ,"No items for "+cmpShortName+"From Room Database");

            jsonObject.put("CmpShortName" , "N/A");
            jsonObject.put("ItemParent" , "N/A");
            jsonObject.put("OneLtr" , "0");
            jsonObject.put("FiveHundredMl" , "0");
            jsonObject.put("TwoFiftyMl" , "0");
            jsonObject.put("OneHundredMl" , "0");
            jsonObject.put("TwentyMl" , "0");
            jsonObject.put("TotalMl" , "0");
        }

        return  jsonObject;

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
        intent.putExtra("ItemParent",states.get(position).getItemParent());
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        super.onPointerCaptureChanged(hasCapture);
    }
}