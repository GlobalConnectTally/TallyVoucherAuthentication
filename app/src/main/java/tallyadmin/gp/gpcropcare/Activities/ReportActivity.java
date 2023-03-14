package tallyadmin.gp.gpcropcare.Activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.TextView;
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
import tallyadmin.gp.gpcropcare.Adapter.ReportAdapter;
import tallyadmin.gp.gpcropcare.Adapter.ReportExpandableListAdapter;
import tallyadmin.gp.gpcropcare.Model.Report;
import tallyadmin.gp.gpcropcare.Model.Reportchild;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.Session;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_AUTHORIZE;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_REPORT;



public class ReportActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ReportExpandableListAdapter expandableListAdapter;
    ArrayList<Report> Saleslist;

    Companysave companydata;
    UserInfo userInfo;
    Session session;
    EditText editsearch;
    ArrayList<Reportchild> listchildren;
    ReportAdapter reportAdapter;
    RecyclerView recyclerOrder;

    TextView authorize,reject,showfrag;
    String ordernn,tallymobileno;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportr);
        Toolbar toolbar = findViewById(R.id.toolbarSo);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Reports");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }


        recyclerOrder = findViewById(R.id.recyle_salerrrr);
        authorize = findViewById(R.id.btn_author);
        reject = findViewById(R.id.btn_reject);

        companydata = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());
        expandableListView = (ExpandableListView) findViewById(R.id.subcategoryty);



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
        else if (isNetworkConnected()) {
            fetchingJSON();
        }

        ordernn = companydata.getVoucher();
        tallymobileno = companydata.getTallyUsermobile();

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rejectinvo();
            }
        });

        authorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authorize();
            }
        });


    }


    public void fetchingJSON() {
        final KProgressHUD Hhdprogress = KProgressHUD.create(ReportActivity.this)
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

//
        Hhdprogress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Saleslist = new ArrayList<>();


                            JSONArray dataArray = obj.getJSONArray("AppGpcReportDetail");
                            System.out.println("reports:"+dataArray);

                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReportActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No data added for sales Report yet")
                                        .setCancelable(false)
                                        .setTitle("Sales Report")
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

                                Report playerModel = new Report();
                                listchildren = new ArrayList<>();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                playerModel.setPartyName(dataobj.getString("PartyName"));
                                playerModel.setCategoryName(dataobj.getString("CategoryName"));
                                playerModel.setAllowedSubGrp(dataobj.getString("AllowedSubGrp"));
                                playerModel.setCreditDays(dataobj.getString("CreditDays"));
                                playerModel.setCreditAmt(dataobj.getString("CreditAmt"));
                                playerModel.setSalesQuantity(dataobj.getString("SalesQuantity"));
                                playerModel.setSalesAmount(dataobj.getString("SalesAmount"));
//                                playerModel.setBuyerAddress(dataobj.getString("BuyerAddress"));
                                playerModel.setThe120Days(dataobj.getString("The120Days"));
                                playerModel.setThe180Days(dataobj.getString("The180Days"));
                                playerModel.setPartyMasterId(dataobj.getString("PartyMasterId"));

//

                                    JSONArray dataArraysub = dataobj.getJSONArray("AppSubCategory");



                                    if (dataArraysub.length() != 0) {

                                        for (int d = 0; d < dataArraysub.length(); d++) {

                                            companydata.setsize(dataArraysub.length());
                                            JSONObject dataobjsb = dataArraysub.getJSONObject(d);
                                            Reportchild playerModelch = new Reportchild();


                                            playerModelch.setSubcategoryname(dataobjsb.getString("Subcategoryname"));
                                            playerModelch.setCreditDayssb(dataobjsb.getString("CreditDays"));
                                            playerModelch.setPercentage(dataobjsb.getString("Percentage"));
                                            playerModelch.setCreditAmtsb(dataobjsb.getString("CreditAmt"));
                                            playerModelch.setSalesQuantitysb(dataobjsb.getString("SalesQuantity"));
                                            playerModelch.setSalesAmountsb(dataobjsb.getString("SalesAmount"));
                                            playerModelch.setThe120Days(dataobjsb.getString("The120Days"));
                                            playerModelch.setThe180Days(dataobjsb.getString("The180Days"));

                                            listchildren.add(playerModelch);

//
                                    }

                                }

                                    playerModel.setChildItemList(listchildren);

                                Saleslist.add(playerModel);

                            }
                            Hhdprogress.dismiss();
//                         setupExandable();
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("CmpGuid", companydata.getKeyCmpnGid());
                params.put("PartyName", companydata.getKeyPartyName());
                params.put("PartyMasterId",companydata.getKeyPartyName());
//                params.put("PartyMasterId", "12345");
//                params.put("CmpGuid", "2d807426-3dff-4a12-8fa9-571a55bdb98f");

////

////                params.put("PartyName", "Parshad Joshi");

//                "CmpGuid":"2d807426-3dff-4a12-8fa9-571a55bdb98f",
//                        "PartyName":"Parshad Joshi",
//                        "PartyMasterId":"12345"
                return params;
            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setupExandable() {

        expandableListAdapter = new ReportExpandableListAdapter(this, Saleslist);
        expandableListView.setAdapter(expandableListAdapter);

    }

    private void setupRecycler() {

        recyclerOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerOrder.setHasFixedSize(true);
        reportAdapter = new ReportAdapter(Saleslist, getApplicationContext());
        recyclerOrder.setAdapter(reportAdapter);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }



    private void Rejectinvo() {
        //rejectmethod
        final AlertDialog.Builder settingdialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View settinview= inflater.inflate(R.layout.rejectmarks_layoutb, null);
        TextView Okbtn = settinview.findViewById(R.id.btn_ok);
        TextView rejct = settinview.findViewById(R.id.btn_cancel);

        final EditText remarkt = settinview.findViewById(R.id.remark);
        settingdialog.setView(settinview);
        final AlertDialog alertDialog = settingdialog.create();

        rejct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        Okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String remark = remarkt.getText().toString();

                if (TextUtils.isEmpty(remark)) {
                    remarkt.setError("Please enter reason for rejection");
                    remarkt.requestFocus();
                    return;
                }


                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AUTHORIZE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                alertDialog.dismiss();
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    int result = obj.getInt("Status");
                                    if (result==1){
                                        Toast.makeText(ReportActivity.this,"Authorized success full",Toast.LENGTH_SHORT).show();
                                        final String message =   "Dear User Your Transaction With OrderNo -" +ordernn+ "is then Rejected";

                                        sendmessager();
                                        startActivity(new Intent(ReportActivity.this,SalesOrderActivity.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(ReportActivity.this,"Authorization Failed"+ "  "+result,Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ReportActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                        params.put("CmpGUID",companydata.getKeyCmpnGid());
                        params.put("MasterID",String.valueOf(companydata.getKeyMasterId()));
                        params.put("AuthenticationFlag","R");
                        params.put("Remark",remark);
                        params.put("TransactionType","1");
                        return params;
                    }
                };

                VolleySingleton.getInstance(ReportActivity.this).addToRequestQueue(stringRequest);

            }
        });


        alertDialog.show();
    }


    private void Authorize() {
        //authorize method

        final String message =   "Dear User Your Transaction With OrderNo -" +ordernn+ "is Approved";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AUTHORIZE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            int result = obj.getInt("Status");
                            if (result==1){
                                Toast.makeText(ReportActivity.this,"Authorized success full",Toast.LENGTH_SHORT).show();


                                sendmessage();
                                startActivity(new Intent(ReportActivity.this,SalesOrderActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(ReportActivity.this,"Authorization Failed"+ "  "+result,Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                params.put("CmpGUID",companydata.getKeyCmpnGid());
                params.put("MasterID",String.valueOf(companydata.getKeyMasterId()));
                params.put("AuthenticationFlag","A");
                params.put("Remark",".");
                params.put("TransactionType","1");
                return params;
            }
        };

        VolleySingleton.getInstance(ReportActivity.this).addToRequestQueue(stringRequest);

    }


    private void sendmessage() {

        String msg = "Dear User Your Transaction With OrderNo -" +ordernn+ "  is been Approved";
        String mobilenumber = tallymobileno;

        String URLNA = "http://smsidea.co.in/SMS/API/SendTallySMS.aspx?mobile=9879518214&pass=9879518214&to="+mobilenumber+"&msg="+msg+"&senderid=GPCCPL&route=4";



        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLNA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(ReportActivity.this," "+response.toString(),Toast.LENGTH_SHORT).show();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

        };

        VolleySingleton.getInstance(ReportActivity.this).addToRequestQueue(stringRequest);

    }


    private void sendmessager() {

        String msg = "Dear User Your Transaction With OrderNo -" +ordernn+ "  is been Rejected";

        String mobilenumber = tallymobileno;


        String URLNA = "http://smsidea.co.in/SMS/API/SendTallySMS.aspx?mobile=9879518214&pass=9879518214&to="+mobilenumber+"&msg="+msg+"&senderid=GPCCPL&route=4";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLNA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(ReportActivity.this," "+response.toString(),Toast.LENGTH_SHORT).show();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

        };

        VolleySingleton.getInstance(ReportActivity.this).addToRequestQueue(stringRequest);

    }

}
