package tallyadmin.gp.gpcropcare.Activities;


import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Sharepreference.UserInfo;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_AUTHORIZE;

public class ShowtransactionActivity extends AppCompatActivity {
    TextView bayername, partname, narration, date, voucheno, vouchertype, amount, reffno;
    Button authorize, reject, showfrag;
    Companysave companysave;
    UserInfo userInfo;
    String masterId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtransactionew);

        companysave = new Companysave(getApplicationContext());
        userInfo = new UserInfo(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbarSoD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Vouchertype") + " Details");

        //bayername = findViewById(R.id.text_bayer);
        partname = findViewById(R.id.txt_party);
        //narration = findViewById(R.id.txt_narration);
        date = findViewById(R.id.txdate);

        amount = findViewById(R.id.txt_amount);
        voucheno = findViewById(R.id.txt_Vnumber);
       // vouchertype = findViewById(R.id.txt_vtype2);
        reffno = findViewById(R.id.txt_ReffNo);

        authorize = findViewById(R.id.btn_author);
        reject = findViewById(R.id.btn_reject);
        showfrag = findViewById(R.id.txt_show);

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


        masterId = String.valueOf(getIntent().getIntExtra("MasterId", 0));

        showfrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowtransactionActivity.this, Fragement_managerActivity.class);
                companysave.setMasterId(getIntent().getIntExtra("MasterId", 44));
                startActivity(intent);
                finish();

            }
        });
        getData();
    }

    private void getData() {
        String names = getIntent().getStringExtra("Address");
        List<String> list = Arrays.asList(names.split(","));

        if (list != null) {
            LinearLayout linearLayout1 = findViewById(R.id.chip_container);
            for (int i = 0; i < list.size(); i++) {
                final View view = LayoutInflater.from(ShowtransactionActivity.this).inflate(R.layout.chips_view, null);
                ((TextView) view.findViewById(R.id.txt_address)).setText(new StringBuilder(list.get(i)).append(","));
                linearLayout1.addView(view);
            }
        } else {
            LinearLayout linearLayout = findViewById(R.id.chip_container);
            for (int i = 0; i < list.size(); i++) {
                final View view = LayoutInflater.from(ShowtransactionActivity.this).inflate(R.layout.chips_view, null);
                ((TextView) view.findViewById(R.id.txt_address)).setText("No address added");
                linearLayout.addView(view);
            }
        }

        //bayername.setText(getIntent().getStringExtra("BayerName"));
        partname.setText(getIntent().getStringExtra("PartName"));
        amount.setText(getIntent().getStringExtra("Amount"));
        date.setText(getIntent().getStringExtra("Date"));
        //narration.setText(getIntent().getStringExtra("Narration"));
        reffno.setText(getIntent().getStringExtra("Reffno"));
        voucheno.setText(getIntent().getStringExtra("VoucherNo"));
        //vouchertype.setText(getIntent().getStringExtra("Vouchertype"));

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
                                        Toast.makeText(ShowtransactionActivity.this, "Authorized success full", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ShowtransactionActivity.this, SalesActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(ShowtransactionActivity.this, "Authorization Failed" + "  " + result, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ShowtransactionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                        params.put("CmpGUID", companysave.getKeyCmpnGid());
                        params.put("MasterID", masterId);
                        params.put("AuthenticationFlag", "R");
                        params.put("Remark", remark);
                        params.put("TransactionType", "1");
                        return params;
                    }
                };

                VolleySingleton.getInstance(ShowtransactionActivity.this).addToRequestQueue(stringRequest);

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
                                Toast.makeText(ShowtransactionActivity.this, "Authorized success full", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ShowtransactionActivity.this, SalesActivity.class));
                                finish();
                            } else {
                                Toast.makeText(ShowtransactionActivity.this, "Authorization Failed" + "  " + result, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowtransactionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AppLoginUserID", userInfo.getAppLoginUserID());
                params.put("CmpGUID", companysave.getKeyCmpnGid());
                params.put("MasterID", masterId);
                params.put("AuthenticationFlag", "A");
                params.put("Remark", ".");
                params.put("TransactionType", "1");
                return params;
            }
        };

        VolleySingleton.getInstance(ShowtransactionActivity.this).addToRequestQueue(stringRequest);

    }
}
