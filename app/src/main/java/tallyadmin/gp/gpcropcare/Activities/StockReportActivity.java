package tallyadmin.gp.gpcropcare.Activities;


import static tallyadmin.gp.gpcropcare.Common.Common.URL_ITEMS;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_LOGIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import tallyadmin.gp.gpcropcare.Adapter.ItemsListAdapter;
import tallyadmin.gp.gpcropcare.Adapter.StateAdapter;
import tallyadmin.gp.gpcropcare.HomeActivity;
import tallyadmin.gp.gpcropcare.LoginActivity;

import tallyadmin.gp.gpcropcare.Model.Company;
import tallyadmin.gp.gpcropcare.Model.Item;
import tallyadmin.gp.gpcropcare.Model.ItemListModel;
import tallyadmin.gp.gpcropcare.Model.ListOfCompanyShortName;
import tallyadmin.gp.gpcropcare.Model.ListOfItemParents;
import tallyadmin.gp.gpcropcare.Model.State;
import tallyadmin.gp.gpcropcare.Model.StateModel;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.ThreadManager;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import tallyadmin.gp.gpcropcare.room.RoomRepository;
import tallyadmin.gp.gpcropcare.utils.VolleyErrors;

public class StockReportActivity extends AppCompatActivity implements StateAdapter.OnStateAdapterListener, ItemsListAdapter.OnItemClickListenaer {
    private RoomRepository roomRepository;
    private Companysave companyData;
    private ArrayList<Item> items;
    public static ArrayList<StateModel> states;
    private Context context;
    private RecyclerView stateRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StateAdapter stateAdapter;
    private TextInputEditText searchView;
    private LinearLayout horizontalScrollView;
    private LinearLayout linearLayoutError,containerlay;
    private List<ListOfCompanyShortName> cmpShortNameList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ItemsListAdapter adapter;
    private ArrayList<ListOfItemParents> itemsList;
    private AlertDialog alertDialogItem;
    private TextInputLayout searchLayout;
    private UserInfo userInfo;
    private VolleyErrors volleyErrors;
    private HorizontalScrollView dynamicHorizontalView;
    public  int max = 0;
    public int takeIndex = 0;

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


        volleyErrors = new VolleyErrors(this);

        searchView = findViewById(R.id.searchViewId);
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        dynamicHorizontalView = findViewById(R.id.dynamicHorizontalView);

        searchLayout = findViewById(R.id.searchLayout);

        containerlay = findViewById(R.id.containerlay);

        states = new ArrayList<>();
        itemsList =new  ArrayList<ListOfItemParents>();

        cmpShortNameList = new ArrayList<>();

        roomRepository = new RoomRepository(this);
        companyData = new Companysave(this);
        userInfo = new UserInfo(this);

        System.out.println("AppLoginUserID: "+userInfo.getAppLoginUserID());

        horizontalScrollView = findViewById(R.id.horizontalView);
        linearLayoutError = findViewById(R.id.errorMessage);

        horizontalScrollView.setVisibility(View.GONE);
        linearLayoutError.setVisibility(View.VISIBLE);


        getItemsFromApi();


        linearLayoutManager = new LinearLayoutManager(this);
        stateRecyclerView = findViewById(R.id.stateRecyclerView);
        stateRecyclerView.setLayoutManager(linearLayoutManager);


         //searchView.onTex
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemDialog();
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //filterByItemParent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    filterByItemParent(s.toString());
                }
            }
        });

        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh() {
                getItemsFromApi();
                swipeRefreshLayout.setRefreshing(true);
            }
        });

    }

    private void findMinimumCount(JSONArray array) {

        System.out.println("Size::"+ String.valueOf(array.length()));
        System.out.println("Data::"+ String.valueOf(array.toString()));

        try {
            JSONArray jsonArray = new JSONArray();
            for (int x = 0; x < array.length(); x++){
                 JSONObject jsonObject1 = new JSONObject();
                 JSONObject jsonObject = array.getJSONObject(x);
                 jsonObject1.put("Count", jsonObject.getJSONArray("ItemData").length());
                 jsonObject1.put("Index", x);
                 jsonArray.put(jsonObject1);
                 System.out.println(jsonObject);
            }

            System.out.println("Count-Array");
            System.out.println(jsonArray);

            /*---------Calculate Greates -------------*/
            int temp = 0;
            int temIndex = 0;
            max = 0;

            for (int num = 0; num < jsonArray.length(); num++){
                  JSONObject jsonObject= jsonArray.getJSONObject(num);
                  int aNum = Integer.parseInt(jsonObject.getString("Count").toString());
                  int Index = Integer.parseInt(jsonObject.getString("Index").toString());

                  if (aNum > temp ){
                      temp = aNum;
                      temIndex = Index;
                  }else {
                      max = temp;
                      takeIndex = temIndex;
                  }
            }

            System.out.println("Maximum::" + String.valueOf(max));
            System.out.println("takeIndex::" + String.valueOf(takeIndex));

        }catch (JSONException e){
             e.printStackTrace();
        }
    }

    private void setHorizontalScrollView(ArrayList<StateModel> states1)
    {
        System.out.println("Called");
        System.out.println(String.valueOf(states1.size()));
        System.out.println(String.valueOf(states1));
        //System.out.println(String.valueOf(states.get(0).getItemData()));

         runOnUiThread(new Runnable() {
           @Override
           public void run() {

               new Handler().postDelayed(
                       new Runnable() {
                           @Override
                           public void run() {

                               System.out.println("Called-1");
                               System.out.println(String.valueOf(states.size()));
                               System.out.println(String.valueOf(states));

                               containerlay.removeAllViews();

                               TextView tv2 = new TextView(context);
                               tv2.setText(getResources().getString(R.string.state));
                               tv2.setTextColor(getResources().getColor(R.color.white));
                               tv2.setLayoutParams(new ViewGroup.LayoutParams(
                                       200,
                                       ViewGroup.LayoutParams.WRAP_CONTENT));
                               containerlay.addView(tv2);

                               if (states.size() > 0){

                                   for (int n = 0; n< max; n++) {

                                       System.out.println(String.valueOf(states.get(takeIndex).getItemData().get(n).getItemName()));

                                       TextView tv = new TextView(context);
                                       tv.setText(states.get(takeIndex).getItemData().get(n).getItemName().toString());
                                       tv.setTextColor(getResources().getColor(R.color.white));
                                       tv.setLayoutParams(new ViewGroup.LayoutParams(
                                               400,
                                               ViewGroup.LayoutParams.WRAP_CONTENT));

                                       containerlay.addView(tv,n + 1);
                                   }
                               }

                               TextView tv3 = new TextView(context);
                               tv3.setText(getResources().getString(R.string.total));
                               tv3.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                               tv3.setTextColor(getResources().getColor(R.color.white));
                               tv3.setLayoutParams(new ViewGroup.LayoutParams(
                                       300,
                                       ViewGroup.LayoutParams.WRAP_CONTENT));
                               containerlay.addView(tv3);
                           }
                       },100);
           }
       });
    }

    private void getCompanyShortNames()
    {
        ThreadManager.getInstance(this).executeTask(new Runnable() {
            @Override
            public void run() {
                cmpShortNameList =  roomRepository.getCompanyShortNames();
                System.out.println(cmpShortNameList.toString());
            }
        });
    }

    private void getCompanyItemParents()
    {
        ThreadManager.getInstance(this).executeTask(new Runnable() {
            @Override
            public void run() {

                List<ListOfItemParents> items = roomRepository.getAllCompanyItemParents();

                System.out.println("Count ::" + String.valueOf(items.size()));

                itemsList.clear();

                itemsList.addAll(items);

                if (itemsList.size() == 1){

                      runOnUiThread( () -> {

                          searchLayout.setHint("Item Name");
                          searchView.setText(itemsList.get(0).getItemParent().toUpperCase(Locale.ROOT));
                      });

                }else {

                      runOnUiThread( () -> {
                           searchLayout.setHint(getResources().getString(R.string.click_to_search));
                           searchView.setInputType(InputType.TYPE_NULL);
                           searchView.setEnabled(true);
                      });
                }

            }
        });
    }

    private void showItemDialog()
    {
        AlertDialog.Builder settingdialog = new AlertDialog.Builder(this,R.style.MyDialog);
        LayoutInflater inflater = this.getLayoutInflater();
        View settinview = inflater.inflate(R.layout.items_list_dialog, null);
        final TextInputEditText inputEditTextSearch = settinview.findViewById(R.id.searchEditText);
        final ImageButton imagecancel = settinview.findViewById(R.id.ib_close);
        adapter = new ItemsListAdapter(this, itemsList,this);
        final RecyclerView recyclerdialog = settinview.findViewById(R.id.recycleViewItems);
        recyclerdialog.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerdialog.setAdapter(adapter);
        recyclerdialog.setHasFixedSize(true);

        imagecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogItem.dismiss();
            }
        });

        inputEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  adapter.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });

        settingdialog.setView(settinview);
        alertDialogItem = settingdialog.create();
        alertDialogItem.show();
        alertDialogItem.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,900);
    }

    private void filterByItemParent(String query)
    {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ThreadManager.getInstance(this).executeTask(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONArray jsonArray = new JSONArray();
                    List<ItemListModel> itemList1 = new ArrayList<>();

                    for (ListOfCompanyShortName list: cmpShortNameList) {

                        System.out.println(list.getCmpShortName());

                        itemList1 = roomRepository.getItemsByCompanyAndParent(
                                list.getCmpShortName().toUpperCase(Locale.ROOT),
                                query.toUpperCase(Locale.ROOT),
                                userInfo.getAppLoginUserID().toUpperCase(Locale.ROOT)
                        );

                        System.out.println(itemList1.toString());
                        try {
                         if (itemList1.size() > 0){
                             JSONObject jsonObject = manipulateTheDatas(itemList1,list.getCmpShortName());
                             /* StateModel stateModel = gson.fromJson(jsonObject.toString(),StateModel.class);
                             states.add(stateModel);*/
                             jsonArray.put(jsonObject);
                         }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (Exception e){
                             e.printStackTrace();
                        }
                    }

                    states.clear();

                    findMinimumCount(jsonArray);

                    JSONArray jsonArray1 = addDynamicallyEmptyValues(jsonArray);
                    for (int res = 0 ; res < jsonArray1.length(); res ++){
                        JSONObject jsonObject = jsonArray1.getJSONObject(res);
                        StateModel stateModel = gson.fromJson(jsonObject.toString(),StateModel.class);
                        states.add(stateModel);
                    }

                    System.out.println("After-Adding");
                    System.out.println(states);


                    if (states.size()!= 0){

                         runOnUiThread(new Runnable() {
                           @Override
                           public void run() {

                               new Handler().postDelayed(
                                       new Runnable() {
                                   @Override
                                   public void run() {
                                       populateDataToRecycler(states);
                                       setHorizontalScrollView(states);
                                   }
                               },
                                       100
                               );

                           }
                       });
                    }

                    System.out.println("StateModel");
                    System.out.println(states);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private JSONArray addDynamicallyEmptyValues(JSONArray jsonArray) {

        JSONObject jsonObject1 = null;
        JSONArray tempJsonArray = new JSONArray();

        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (max > jsonObject.getJSONArray("ItemData").length()){
                    int diff = (max - jsonObject.getJSONArray("ItemData").length());
                    for (int j = 0; j < diff; j++ ){
                        System.out.println("Looping::" + String.valueOf(j));
                        jsonObject1  = new JSONObject();
                        jsonObject1.put("CmpShortName" , jsonObject.getString("CmpShortName"));
                        jsonObject1.put("ItemParent" , "N/A");
                        jsonObject1.put("ItemName" , "N/A");
                        jsonObject1.put("ItemClosing" , "0.00");
                        jsonObject.getJSONArray("ItemData").put(jsonObject1);
                    }
                    System.out.println("Changed On-0::");
                    System.out.println(jsonObject.toString());
                }
                System.out.println("Changed On-1::");
                System.out.println(jsonObject.getJSONArray("ItemData").toString());
            }

            System.out.println("Changed On::");
            System.out.println(jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tempJsonArray = new JSONArray(new ArrayList<String>());

        tempJsonArray = jsonArray;

        return  tempJsonArray;
    }

    private void populateDataToRecycler(ArrayList<StateModel> list)
    {
        /*
        JSONObject jsonObject = null;
        JSONObject jsonObject1 = null;
        JSONArray jsonArray1 = null;

        List<State> states1= new ArrayList<>();

        if (jsonArray.length() != 0){

            for (int i = 0; i < jsonArray.length(); i++){

                StateModel stateModel = new StateModel();

                try {
                     jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject != null){
                        if (jsonObject.has("CmpShortName"))
                        {
                            stateModel.setCmpShortName(jsonObject.getString("CmpShortName"));
                        }
                        else
                        {
                            stateModel.setCmpShortName("N/A");
                        }

                        jsonArray1 = jsonObject.getJSONArray("ItemData");
                        for (int y = 0; y < jsonArray1.length(); y++)
                        {

                            State state = new State();

                            jsonObject1 = jsonArray1.getJSONObject(y);

                            if (jsonObject1.has("CmpShortName")){
                                state.setCmpShortName(jsonObject1.getString("CmpShortName"));
                            }else {
                                state.setCmpShortName("N/A");
                            }

                            if (jsonObject1.has("ItemParent")){
                                state.setItemParent(jsonObject1.getString("ItemParent"));
                            }else {
                                state.setItemParent("N/A");
                            }

                            if (jsonObject1.has("ItemName")){
                                state.setItemName(jsonObject1.getString("ItemName"));
                            }else {
                                state.setItemName("N/A");
                            }

                            if (jsonObject1.has("ItemClosing")){
                                DecimalFormat numberFormat = new DecimalFormat("#.00");
                                String total = numberFormat.format(Double.parseDouble(String.valueOf(jsonObject1.getString("ItemClosing"))));
                                state.setItemClosing(total);
                            }else {
                                state.setItemClosing(String.valueOf("0"));
                            }
                            states1.add(state);
                        }
                        System.out.println("State-1");
                        System.out.println(states1.toString());
                        stateModel.setItemData(states1);
                        stateModel.setTotalClosing(jsonObject.getString("TotalClosing"));
                        jsonArray1 = new JSONArray(new ArrayList<String>());
                    }

                    System.out.println("State-Data");
                    System.out.println(jsonArray1);
                    System.out.println(stateModel.toString());

                    runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          states.add(stateModel);
                      }
                  });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }*/
        System.out.println("StateModel-1");
        System.out.println(list);
        initializeAdapter(list);

    }

    private JSONObject doSomeOperations(List<ItemListModel> itemList , String cmpShortName) throws JSONException
    {

        if (itemList.size() == 0 ){

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

    private void initializeAdapter(ArrayList<StateModel> stateModelArrayList)
    {

        System.out.println("StateModel-2");
        System.out.println(stateModelArrayList);

        stateAdapter = new StateAdapter(stateModelArrayList, this,this , max);

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
                                swipeRefreshLayout.setRefreshing(false);

                                ThreadManager.getInstance(context).executeTask(new Runnable() {
                                    @Override
                                    public void run()
                                    {
                                        for (int i = 0; i < dataArray.length(); i++) {
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
                                                    item.setItemOpening(itemJson.getString("ItemOpening").split(" ")[0]);
                                                }

                                                if (itemJson.getString("ItemInwards").isEmpty()){
                                                    item.setItemInwards("0");
                                                }else {
                                                    item.setItemInwards(itemJson.getString("ItemInwards").split(" ")[0]);
                                                }

                                                if (itemJson.getString("ItemOutwards").isEmpty()){
                                                    item.setItemOutwards( "0" );
                                                }else {
                                                    item.setItemOutwards( itemJson.getString("ItemOutwards").split(" ")[0]);
                                                }

                                                if (itemJson.getString("ItemClosing").isEmpty()){
                                                    item.setItemClosing("0" );
                                                }else {
                                                    item.setItemClosing( itemJson.getString("ItemClosing").split(" ")[0]);
                                                }

                                                item.setAppLoginUserID(userInfo.getAppLoginUserID().toUpperCase(Locale.ROOT).toString());

                                                items.add(item);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }catch (Exception e1){
                                                e1.printStackTrace();
                                            }
                                        }

                                        System.out.println(items.toString());

                                        roomRepository.deleteItems();

                                        long[] isInserted = roomRepository.insertItemsToRoom(items);
                                        getCompanyItemParents();
                                        getCompanyShortNames();

                                        if (isInserted != null) {
                                            getCompanyItemParents();
                                            getCompanyShortNames();
                                            Log.d("Result Message: " ,"Data Successful Loaded to Room Database");
                                        } else {
                                            Log.d("Result Message: " ,"Fail to Load Data to Room Database");
                                        }
                                    }
                                });

                            } else {
                                ThreadManager.getInstance(context).executeTask(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            roomRepository.deleteItems();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                Hhdprogress.dismiss();
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(StockReportActivity.this, "No items Found", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),
                                volleyErrors.exceptionMessage(error).toString(),
                                Toast.LENGTH_SHORT).show();

                        Hhdprogress.dismiss();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                //userId
                String userId = userInfo.getAppLoginUserID();
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID", userId);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private JSONObject manipulateTheDatas(List<ItemListModel> itemsByCompany,String cmpShortName) throws JSONException
    {

        /*Lamda function */
        runOnUiThread( () -> {
            /* Clear ArrayList t Avoid Duplication*/
            horizontalScrollView.setVisibility(View.VISIBLE);
            linearLayoutError.setVisibility(View.GONE);
            //initializeAdapter(states);
        });

        double totalValue  = 0.0;
        JSONObject jsonObject = null;
        JSONObject jsonObject1 = new JSONObject();

        JSONArray jsonArray= new JSONArray();
        JSONArray jsonArray1= new JSONArray();

        if (itemsByCompany != null && itemsByCompany.size() != 0) {

            for (ItemListModel item: itemsByCompany) {

                jsonObject = new JSONObject();

                jsonObject.put("CmpShortName" , item.getCmpShortName());
                jsonObject.put("ItemParent" , item.getItemParent());
                jsonObject.put("ItemName" , item.getItemName());

                if (!item.getItemClosing().equalsIgnoreCase(" ")){
                    totalValue  += Double.parseDouble(String.valueOf(item.getItemClosing().toString()).replace(",",""));
                }
                /*
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
                else {

                }*/
                jsonObject.put("ItemClosing" , item.getItemClosing());

                jsonArray.put(jsonObject);
            }
            jsonObject1.put("CmpShortName" , cmpShortName);
            jsonObject1.put("ItemData",jsonArray);
            jsonObject1.put("TotalClosing" , String.valueOf(totalValue));
            //jsonArray1.put(jsonObject1);

        } else {

            for (int x = 0; x < 3 ; x++) {

                jsonObject = new JSONObject();

                jsonObject.put("CmpShortName" , cmpShortName);
                jsonObject.put("ItemParent" , "N/A");
                jsonObject.put("ItemName" , "N/A");
                jsonObject.put("ItemClosing" , "0.00");

                jsonArray.put(jsonObject);
            }
            jsonObject1.put("CmpShortName" , cmpShortName);
            jsonObject1.put("ItemData",jsonArray);
            jsonObject1.put("TotalClosing" , "0.00");
            //jsonArray1.put(jsonObject1);

        }

        System.out.println("Let-See");
        System.out.println(jsonArray1);

        return  jsonObject1;

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
    public void onClickState(int position, List<StateModel> states)
    {
        System.out.println("Clicked");
        System.out.println(states.get(position).getCmpShortName());
        System.out.println(states.get(position).getItemData().get(0).getItemParent());
        Intent intent = new Intent(StockReportActivity.this, StateReportActivity.class);
        intent.putExtra("CmpShortName",states.get(position).getCmpShortName());
        intent.putExtra("ItemParent",states.get(position).getItemData().get(0).getItemParent());
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void OnItemClick(ListOfItemParents list)
    {
        //searchView.setText(itemsList.get(position).getItemParent().toUpperCase(Locale.ROOT));
        searchView.setText(list.getItemParent().toString());
        alertDialogItem.dismiss();

        System.out.println(list);
    }

}