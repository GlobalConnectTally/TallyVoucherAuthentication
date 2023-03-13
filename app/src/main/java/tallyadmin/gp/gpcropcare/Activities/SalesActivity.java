package tallyadmin.gp.gpcropcare.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
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
import java.util.Map;
import tallyadmin.gp.gpcropcare.Adapter.SalesAdapter;
import tallyadmin.gp.gpcropcare.Model.SalesOrder;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.Session;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_SALES;


public class SalesActivity extends AppCompatActivity {
    ArrayList<SalesOrder> Saleslist;
    private SalesAdapter salesOrderAdapter;
    private static ProgressDialog mProgressDialog;
    RecyclerView recyclerOrder;
    Companysave companydata;
    UserInfo userInfo;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        Toolbar toolbar = findViewById(R.id.toolbarSo);
        setSupportActionBar(toolbar);




        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            getSupportActionBar().setTitle("Sales Order");
        }

        companydata = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());
        session = new Session(getApplicationContext());
        mProgressDialog = new ProgressDialog(SalesActivity.this);

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
        } else if (isNetworkConnected()) {
            fetchingJSON();
        }


    }

    public void fetchingJSON()
    {

        final KProgressHUD Hhdprogress = KProgressHUD.create(SalesActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
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

                            Saleslist = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("SalesTransactions");
                            System.out.println("salesorder:"+dataArray);

                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SalesActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No data added for sales Order yet")
                                        .setCancelable(false)
                                        .setTitle("Sales Order")
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

                                SalesOrder playerModel = new SalesOrder();
                                JSONObject dataobj = dataArray.getJSONObject(i);
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
                                Saleslist.add(playerModel);
                            }

                            Hhdprogress.dismiss();
                            setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage() == null ? "" : error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                params.put("CmpGUID", companydata.getKeyCmpnGid());
                params.put("TransactionType", "Sales Order");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setupRecycler()
    {
        recyclerOrder.setHasFixedSize(true);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        salesOrderAdapter = new SalesAdapter(Saleslist, getApplicationContext(), SalesActivity.this);
        salesOrderAdapter.notifyDataSetChanged();
        recyclerOrder.setAdapter(salesOrderAdapter);
    }


    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


}
