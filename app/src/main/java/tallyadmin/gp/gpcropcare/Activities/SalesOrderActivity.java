package tallyadmin.gp.gpcropcare.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
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
import java.util.Locale;
import java.util.Map;
import tallyadmin.gp.gpcropcare.Adapter.SalesOrderAdapter;

import tallyadmin.gp.gpcropcare.Model.SalesOrder;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.Session;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_SALES;


//------Sales order activity-----//
public class SalesOrderActivity extends AppCompatActivity
{
    ArrayList<SalesOrder> Saleslist;
    private SalesOrderAdapter salesOrderAdapter;
    private static ProgressDialog mProgressDialog;
    RecyclerView recyclerOrder;
    Companysave companydata;
    UserInfo userInfo;
    Session session;
    EditText editsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order);

        Toolbar toolbar = findViewById(R.id.toolbarSo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sales Invoice");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        editsearch = findViewById(R.id.edt_seac);
        companydata = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());
        session = new Session(getApplicationContext());
        mProgressDialog = new ProgressDialog(SalesOrderActivity.this);
        recyclerOrder = findViewById(R.id.recyle_salesorder);

        if (!isNetworkConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Set the Alert Dialog Message
            builder.setMessage("Internet connection is required, Please turn on Wifi or Mobile Data")
                    .setCancelable(false)
                    .setPositiveButton("Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // Restart the Activity
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if (isNetworkConnected())
        {
            fetchingJSON();
        }
        seachcust();
    }

    public void fetchingJSON()
    {
        final KProgressHUD Hhdprogress = KProgressHUD.create(SalesOrderActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);


        Hhdprogress.setCancellable(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                finish();
            }
        });


        Hhdprogress.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_SALES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            System.out.println("response"+obj);
                            Saleslist = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("SalesTransactions");

                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SalesOrderActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No data added for sales yet")
                                        .setCancelable(false)
                                        .setTitle("Sales")
                                        .setNegativeButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int id) {
                                                        dialog.dismiss();
                                                        Hhdprogress.dismiss();
                                                        finish();
                                                    }
                                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);
                                SalesOrder playerModel = new SalesOrder();
                                playerModel.setBuyerName(dataobj.getString("BuyerName"));
                                playerModel.setPartyName(dataobj.getString("PartyName"));
                                playerModel.setTallyUserName(dataobj.getString("TallyUserName"));
                                playerModel.setTotalAmt(dataobj.getString("TotalAmt"));
                                playerModel.setReffNo(dataobj.getString("ReffNo"));
                                playerModel.setDate(dataobj.getString("Date"));
                                playerModel.setVoucherTypeName(dataobj.getString("VoucherTypeName"));
                                playerModel.setMasterID(dataobj.getInt("MasterID"));
                                playerModel.setBuyerAddress(dataobj.getString("BuyerAddress"));
                                playerModel.setVoucherNumber(dataobj.getString("VoucherNumber"));
                                playerModel.setNarration(dataobj.getString("Narration"));
                                playerModel.setLedgerMasterId(dataobj.getString("LedgerMasterId"));
                                playerModel.setTallyUsermobileno(dataobj.getString("TallyUserMobNo"));

                                /*----  FROM SHAREPREFERENCE  -------*/
                                playerModel.setAllowApprove(userInfo.getAllowApprove());
                                playerModel.setAllowReject(userInfo.getAllowReject());

                                playerModel.setAuthenticationFlag(dataobj.getString("AuthenticationFlag"));

                                Saleslist.add(playerModel);

                            }

                            Log.d("SALESORDER",Saleslist.toString());

                            Hhdprogress.dismiss();
                            setupRecycler();
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
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage() == null ? "" : error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                params.put("CmpGUID", companydata.getKeyCmpnGid());
                params.put("TransactionType", "Sales");

                /*---   USER-LEVELS ------------*/
                String accessLevel = " ";
                if (  userInfo.getFirstLevel().equalsIgnoreCase("Yes") &&
                         userInfo.getSecondLevel().equalsIgnoreCase("Yes")){

                    //B - For Both P & A1
                    accessLevel = "B";

                 }else if (  userInfo.getFirstLevel().equalsIgnoreCase("Yes")
                             && userInfo.getSecondLevel().equalsIgnoreCase("No")
                   ){

                    //P - Pending
                    accessLevel = "P";

                }else if (  userInfo.getFirstLevel().equalsIgnoreCase("No")
                             && userInfo.getSecondLevel().equalsIgnoreCase("Yes")){

                    //A1 - Approved By First Level
                    accessLevel = "A1";

                }else if (  userInfo.getFirstLevel().equalsIgnoreCase("No")
                         && userInfo.getFirstLevel().equalsIgnoreCase("No")){

                    //Unknown - Returns Empty
                    accessLevel = "P";

                }

                params.put("AuthenticationFlag", accessLevel);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setupRecycler()
    {
        recyclerOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerOrder.setHasFixedSize(true);
        salesOrderAdapter = new SalesOrderAdapter(Saleslist, getApplicationContext(), SalesOrderActivity.this);
        recyclerOrder.setAdapter(salesOrderAdapter);
    }

    private void seachcust()
    {
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });
    }

    private void filter(String text)
    {

        text = text.toLowerCase();
        ArrayList<SalesOrder> filterdNames = new ArrayList<>();

        for (SalesOrder s : Saleslist) {

            if (s.getPartyName().toLowerCase(Locale.getDefault()).contains(text)||s.getDate().contains(text)||s.getVoucherNumber().toLowerCase().contains(text)) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        salesOrderAdapter.filterList(filterdNames);
    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
