package tallyadmin.gp.gpcropcare.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import tallyadmin.gp.gpcropcare.Adapter.SalesTransaAdapter;
import tallyadmin.gp.gpcropcare.Adapter.SendAllAdapter;
import tallyadmin.gp.gpcropcare.Model.SalesTransactionDetails;
import tallyadmin.gp.gpcropcare.Model.SendAllDetailsLed;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_AUTHORIZE;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_SALESTRANS;

public class Fragement_OrderActivity extends AppCompatActivity {
    RecyclerView recycler, recyl_trans;
    ArrayList<SalesTransactionDetails> Saleslist;

    ArrayList<SendAllDetailsLed> Sendalldetailld;
    private SendAllAdapter sendAllAdapterm;
    private SalesTransaAdapter salesOrderAdapter;
    private static ProgressDialog mProgressDialog;
    Companysave companydata;
    UserInfo userInfo;
    int masterId;

    Button approve_btn, rejectbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_salesorder);
        companydata = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());


        Toolbar toolbar = findViewById(R.id.toolbarSof);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle(null);

        TextView tooltext = findViewById(R.id.voucher_tool);
        tooltext.setText(companydata.getVoucher());


        approve_btn = findViewById(R.id.btn_authorfn);
        rejectbtn = findViewById(R.id.btn_rejectf);
        recycler = findViewById(R.id.recyl_transaction);
        recyl_trans = findViewById(R.id.recyl_transdisc);

        approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authorize();
            }
        });

        rejectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rejectinvo();
            }
        });

        fetchingJSON();
    }

    private void Rejectinvo() {
        //rejectmethod
        final AlertDialog.Builder settingdialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View settinview = inflater.inflate(R.layout.rejectmarks_layoutb, null);
        Button Okbtn = settinview.findViewById(R.id.btn_ok);
        Button rejct = settinview.findViewById(R.id.btn_cancel);

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
                                    if (result == 1) {
                                        Toast.makeText(Fragement_OrderActivity.this, "Authorized success full", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Fragement_OrderActivity.this, SalesOrderActivity.class));
                                        finish();


                                    } else {
                                        Toast.makeText(Fragement_OrderActivity.this, "Authorization Failed" + "  " + result, Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Fragement_OrderActivity.this, error.getMessage() == null ? "" : error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                        params.put("CmpGUID", companydata.getKeyCmpnGid());
                        params.put("MasterID", String.valueOf(companydata.getKeyMasterId()));
                        params.put("AuthenticationFlag", "R");
                        params.put("Remark", remark);
                        params.put("TransactionType", "1");
                        return params;
                    }
                };

                VolleySingleton.getInstance(Fragement_OrderActivity.this).addToRequestQueue(stringRequest);

            }
        });


        alertDialog.show();
    }

    private void Authorize() {
        //authorize method

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AUTHORIZE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);

                            int result = obj.getInt("Status");
                            if (result == 1) {
                                Toast.makeText(Fragement_OrderActivity.this, "Authorized success full", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Fragement_OrderActivity.this, SalesOrderActivity.class));
                                finish();


                            } else {
                                Toast.makeText(Fragement_OrderActivity.this, "Authorization Failed" + "  " + result, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Fragement_OrderActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                params.put("CmpGUID", companydata.getKeyCmpnGid());
                params.put("MasterID", String.valueOf(companydata.getKeyMasterId()));
                params.put("AuthenticationFlag", "A");
                params.put("Remark", ".");
                params.put("TransactionType", "1");
                return params;
            }
        };

        VolleySingleton.getInstance(Fragement_OrderActivity.this).addToRequestQueue(stringRequest);

    }


    private void fetchingJSON() {


        final KProgressHUD Hhdprogress = KProgressHUD.create(Fragement_OrderActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        Hhdprogress.setCancellable(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();

            }
        });
        Hhdprogress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SALESTRANS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            Saleslist = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("SalesTransactionsInv");

                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Fragement_OrderActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No data added for sales yet")
                                        .setCancelable(false)
                                        .setTitle("Sales Order")
                                        .setNegativeButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int id) {
                                                        dialog.dismiss();
                                                        Hhdprogress.dismiss();

                                                    }
                                                });
                                AlertDialog alert = builder.create();

                                alert.show();


                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                SalesTransactionDetails playerModel = new SalesTransactionDetails();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                playerModel.setItemName(dataobj.getString("ItemName"));
                                playerModel.setSalesLedgerName(dataobj.getString("SalesLedgerName"));
                                playerModel.setInvBatchName(dataobj.getString("InvBatchName"));
                                playerModel.setInvGodownName(dataobj.getString("InvGodownName"));
                                playerModel.setInvQty(dataobj.getString("InvQty"));
                                playerModel.setInvAmount(dataobj.getString("InvAmount"));
                                playerModel.setInvRate(dataobj.getString("InvRate"));
                                playerModel.setVoucherNumber(dataobj.getString("VoucherNumber"));
                                playerModel.setInvMfgDate(dataobj.getString("InvMfgDate"));
                                playerModel.setInvExpDate(dataobj.getString("InvExpDate"));
                                playerModel.setInvDiscPerc(dataobj.getString("InvDiscPerc"));
                               Saleslist.add(playerModel);
                            }                            Hhdprogress.dismiss();
                            setupRecycler();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ///get send all orders

                        try {


                            JSONObject obj = new JSONObject(response);
                            Sendalldetailld = new ArrayList<>();
                            JSONArray dataArray2 = obj.getJSONArray("SendAllDetailsLed");


                            if (dataArray2.length() != 0) {

                                for (int i = 0; i < dataArray2.length(); i++) {

                                    SendAllDetailsLed playerModeld = new SendAllDetailsLed();
                                    JSONObject dataobj = dataArray2.getJSONObject(i);
                                  playerModeld.setLedgerName(dataobj.getString("LedgerName"));
                                    playerModeld.setLedgerAmount(dataobj.getString("LedgerAmount"));
                                    playerModeld.setLedgerPerc(dataobj.getString("LedgerPerc"));
                                    playerModeld.setVoucherNumber(dataobj.getString("VoucherNumber"));
                                    Sendalldetailld.add(playerModeld);                                }

                                setupRecycler2m();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                params.put("CmpGUID", companydata.getKeyCmpnGid());
                params.put("MasterID", String.valueOf(companydata.getKeyMasterId()));
                return params;
            }
        };

        VolleySingleton.getInstance(Fragement_OrderActivity.this).addToRequestQueue(stringRequest);


    }

    private void setupRecycler() {
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(Fragement_OrderActivity.this, LinearLayoutManager.VERTICAL, false));
        salesOrderAdapter = new SalesTransaAdapter(Saleslist, Fragement_OrderActivity.this);
        recycler.setAdapter(salesOrderAdapter);

    }

    private void setupRecycler2m() {
        recyl_trans.setHasFixedSize(true);
        recyl_trans.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        sendAllAdapterm = new SendAllAdapter(Sendalldetailld, getApplicationContext());
        recyl_trans.setAdapter(sendAllAdapterm);
    }
}
