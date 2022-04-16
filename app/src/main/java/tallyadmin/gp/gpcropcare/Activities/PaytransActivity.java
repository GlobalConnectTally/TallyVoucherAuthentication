package tallyadmin.gp.gpcropcare.Activities;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

import tallyadmin.gp.gpcropcare.Adapter.PaybILAllAdapter;
import tallyadmin.gp.gpcropcare.Adapter.Paylist;
import tallyadmin.gp.gpcropcare.Model.Paybillwise;
import tallyadmin.gp.gpcropcare.Model.PaymentTrans;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_AUTHORIZE;
import static tallyadmin.gp.gpcropcare.Common.Common.URL_SENDPAYMENTDETAILS;

public class PaytransActivity extends AppCompatActivity {
    RecyclerView recycler, recyl_trans;
    ArrayList<PaymentTrans> Saleslist;
    ArrayList<Paybillwise> Sendalldetailld;
    private PaybILAllAdapter sendpayment;
    private Paylist paylist;
    Companysave companydata;
    UserInfo userInfo;
    int masterId;
    JSONObject obj;
    JSONArray dataArray2;
    Button approve_btn, rejectbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytrans);

        companydata = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());
        masterId = getIntent().getIntExtra("MasterId", 0);

        Toolbar toolbar = findViewById(R.id.toolbarSof);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle(null);

        recycler = findViewById(R.id.recyl_transactionP);
        recyl_trans = findViewById(R.id.recyl_transdiscP);
        approve_btn = findViewById(R.id.btn_authorfnp);
        rejectbtn = findViewById(R.id.btn_rejectfp);

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
        TextView tooltext = findViewById(R.id.voucher_tool);
        tooltext.setText(companydata.getVoucher());
        fetchingJSON();
    }

    private void fetchingJSON() {


        final KProgressHUD Hhdprogress = KProgressHUD.create(PaytransActivity.this)
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SENDPAYMENTDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            obj = new JSONObject(response);

                            Saleslist = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("PaymentBankDetails");
                            dataArray2 = obj.getJSONArray("PaymentBillWiseDetails");

                            if (dataArray.length() == 0 & dataArray2.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PaytransActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No data added for Payment yet")
                                        .setCancelable(false)
                                        .setTitle("Payment")
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

                                PaymentTrans playerModel = new PaymentTrans();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                playerModel.setPaidTo(dataobj.getString("PaidTo"));
                                playerModel.setPaidBy(dataobj.getString("PaidBy"));
                                playerModel.setTransactionType(dataobj.getString("TransactionType"));
                                playerModel.setAccountNo(dataobj.getString("AccountNo"));
                                playerModel.setBankName(dataobj.getString("BankName"));
                                playerModel.setIFSCCode(dataobj.getString("IFSCCode"));
                                playerModel.setInstrumentDate(dataobj.getString("InstrumentDate"));
                                playerModel.setInstrumentNo(dataobj.getString("InstrumentNo"));
                                Saleslist.add(playerModel);

                            }
                            Hhdprogress.dismiss();
                            setupRecycler();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ///get send all orders

                        try {


                            dataArray2 = obj.getJSONArray("PaymentBillWiseDetails");
                            Sendalldetailld = new ArrayList<>();
                 for (int i = 0; i < dataArray2.length(); i++) {

                                Paybillwise playerModeld = new Paybillwise();
                                JSONObject dataobj = dataArray2.getJSONObject(i);
                           playerModeld.setPartyName(dataobj.getString("PartyName"));
                                playerModeld.setAmount(dataobj.getString("Amount"));
                                playerModeld.setBillDate(dataobj.getString("BillDate"));
                                playerModeld.setBillReff(dataobj.getString("BillReff"));
                                Sendalldetailld.add(playerModeld);
                            }

                            setupRecycler2m();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaytransActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                params.put("CmpGUID", companydata.getKeyCmpnGid());
                params.put("MasterID", String.valueOf(masterId));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void setupRecycler() {
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        paylist = new Paylist(getApplicationContext(), Saleslist);
        recycler.setAdapter(paylist);

    }

    private void setupRecycler2m() {
        recyl_trans.setHasFixedSize(true);
        recyl_trans.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        sendpayment = new PaybILAllAdapter(Sendalldetailld, getApplicationContext());
        recyl_trans.setAdapter(sendpayment);

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
                                        Toast.makeText(PaytransActivity.this, "Authorized success full", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(PaytransActivity.this, "Authorization Failed" + "  " + result, Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(PaytransActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                        params.put("CmpGUID", companydata.getKeyCmpnGid());
                        params.put("MasterID", String.valueOf(masterId));
                        params.put("AuthenticationFlag", "R");
                        params.put("Remark", remark);
                        params.put("TransactionType", "2");
                        return params;
                    }
                };

                VolleySingleton.getInstance(PaytransActivity.this).addToRequestQueue(stringRequest);

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
                                Toast.makeText(PaytransActivity.this, "Authorized success full", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PaytransActivity.this, PaymentActivity.class));
                                finish();
                            } else {
                                Toast.makeText(PaytransActivity.this, "Authorization Failed" + "  " + result, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                   }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaytransActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                params.put("CmpGUID", companydata.getKeyCmpnGid());
                params.put("MasterID", String.valueOf(masterId));
                params.put("AuthenticationFlag", "A");
                params.put("Remark", ".");
                params.put("TransactionType", "2");
                return params;
            }
        };

        VolleySingleton.getInstance(PaytransActivity.this).addToRequestQueue(stringRequest);

    }


}

