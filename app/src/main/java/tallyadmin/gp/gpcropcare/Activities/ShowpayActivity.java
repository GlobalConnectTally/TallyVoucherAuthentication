package tallyadmin.gp.gpcropcare.Activities;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_AUTHORIZE;

public class ShowpayActivity extends AppCompatActivity {
    TextView createdp,partname,narration,date,voucheno,vouchertype,amount,reffno;
    Button authorize,reject,showfrag;
    Companysave companysave;
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpay);

        companysave = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbarSoD);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            getSupportActionBar().setTitle(getIntent().getStringExtra("Vouchertype")+" Details");
        }

        narration = findViewById(R.id.txt_narrationp);
        date = findViewById(R.id.txdateP);
        createdp = findViewById(R.id.txt_createdp);
        amount = findViewById(R.id.txt_amountp);
        voucheno = findViewById(R.id.txt_VnumberP);
        vouchertype = findViewById(R.id.txt_vtype2p);
        reffno = findViewById(R.id.txt_masterp);

        authorize = findViewById(R.id.btn_authorP);
        reject = findViewById(R.id.btn_rejectP);
        showfrag = findViewById(R.id.txt_showP);


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
        final int masterId = getIntent().getIntExtra("MasterId",0);
        showfrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowpayActivity.this,PaytransActivity.class);
                companysave.setMasterId(masterId);
                startActivity(intent);
            }
        });
        getData();
    }




    private void getData() {
        createdp.setText(getIntent().getStringExtra("Createdby"));
        reffno.setText(String.valueOf(getIntent().getIntExtra("MasterId",0)));
        amount.setText(getIntent().getStringExtra("Amount"));
        date.setText(getIntent().getStringExtra("Date"));
       narration.setText(getIntent().getStringExtra("Narration"));
        voucheno.setText(getIntent().getStringExtra("VoucherNo"));
        vouchertype.setText(getIntent().getStringExtra("Vouchertype"));
    }

    private void Rejectinvo() {
        //rejectmethod
        final AlertDialog.Builder settingdialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View settinview= inflater.inflate(R.layout.rejectmarks_layoutb, null);
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
                                Toast.makeText(ShowpayActivity.this,"Success full",Toast.LENGTH_SHORT).show();
                           }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ShowpayActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                        params.put("CmpGUID",companysave.getKeyCmpnGid());
                        params.put("MasterID",String.valueOf(companysave.getKeyMasterId()));
                        params.put("AuthenticationFlag","R");
                        params.put("Remark",remark);
                        params.put("TransactionType","2");
                        return params;
                    }
                };

                VolleySingleton.getInstance(ShowpayActivity.this).addToRequestQueue(stringRequest);

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
                            if (result==1){
                                Toast.makeText(ShowpayActivity.this,"Authorized success full",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ShowpayActivity.this,"Authorization Failed"+ "  "+result,Toast.LENGTH_SHORT).show();
                            }

  } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowpayActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID",userInfo.getAppLoginUserID());
                params.put("CmpGUID",companysave.getKeyCmpnGid());
                params.put("MasterID",String.valueOf(companysave.getKeyMasterId()));
                params.put("AuthenticationFlag","A");
                params.put("Remark",".");
                params.put("TransactionType","2");
                return params;
            }
        };

        VolleySingleton.getInstance(ShowpayActivity.this).addToRequestQueue(stringRequest);

    }
}
