package tallyadmin.gp.gpcropcare.Activities;

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

import tallyadmin.gp.gpcropcare.Adapter.Paymentdapter;

import tallyadmin.gp.gpcropcare.Model.Payment;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.Session;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_PAYMENTGET;

public class PaymentActivity extends AppCompatActivity {
    private Paymentdapter salesOrderAdapter;
    ArrayList<Payment> Saleslist;
    RecyclerView recyclerpayment;
    Companysave companydata;
    UserInfo userInfo;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = findViewById(R.id.toolbarp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        companydata = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());
        session = new Session(getApplicationContext());

        recyclerpayment = findViewById(R.id.recyle_payment);

        if (!isNetworkConnected()){
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
        else  if (isNetworkConnected()){
            fetchingJSON();
        }

    }

    public void fetchingJSON() {
        final KProgressHUD Hhdprogress = KProgressHUD.create(PaymentActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);


        Hhdprogress.setCancellable(new  DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                finish();
            }
        });


        Hhdprogress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PAYMENTGET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Saleslist = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("PaymentTransactions");

                            if (dataArray.length()==0){
                                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
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

                                Payment playerModel = new Payment();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                playerModel.setNarration(dataobj.getString("Narration"));
                                playerModel.setTallyUserName(dataobj.getString("TallyUserName"));
                                playerModel.setAmount(dataobj.getString("Amount"));
                                playerModel.setVoucherDate(dataobj.getString("VoucherDate"));
                                playerModel.setVoucherTypeName(dataobj.getString("VoucherTypeName"));
                                playerModel.setMasterID(dataobj.getInt("MasterID"));
                                playerModel.setVoucherNumber(dataobj.getString("VoucherNumber"));
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
       {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                params.put("CmpGUID",companydata.getKeyCmpnGid());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void setupRecycler(){
        recyclerpayment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerpayment.setHasFixedSize(true);

        salesOrderAdapter = new Paymentdapter(Saleslist,getApplicationContext(),PaymentActivity.this);
        recyclerpayment.setAdapter(salesOrderAdapter);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
