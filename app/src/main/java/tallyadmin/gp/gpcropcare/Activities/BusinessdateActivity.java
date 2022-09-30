package tallyadmin.gp.gpcropcare.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ListView;
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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tallyadmin.gp.gpcropcare.Adapter.BusinessdateAdapter;
import tallyadmin.gp.gpcropcare.Adapter.NCRDueBillsAdapter;
import tallyadmin.gp.gpcropcare.Model.Businessdate;
import tallyadmin.gp.gpcropcare.Model.NCRBill;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_REPORTNrc;


public class BusinessdateActivity extends AppCompatActivity {
    ListView listView;
    public static List<Businessdate> arraylist;
    String pgr, royal, premium, pestisides, classic, dust, total;
    private BusinessdateAdapter businessdateAdapter;
    Companysave companydata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessdate);

        Toolbar toolbar = findViewById(R.id.toolbarSoD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Business till date Current Year");

        //listView = (ListView) findViewById(R.id.ncrlist);


        companydata = new Companysave(getApplicationContext());
        TextView partyname = findViewById(R.id.busi_partyname);
        partyname.setText(companydata.getKeyPartyName());
        TextView billvalue = findViewById(R.id.busi_billvalue);
        billvalue.setText(companydata.getKeyBillAmount());
        TextView reportdate = findViewById(R.id.busi_date);
        reportdate.setText(companydata.getKeyVocherdate());


        //listView = (ListView) findViewById(R.id.ncrlist);
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


    public void fetchingJSON() {
        String urlno = "?CmpGUID=" + companydata.getKeyCmpnGid() + "&LedgerName="+companydata.getKeyPartyName()+ "&LedgerID="+companydata.getKeyLegId()+ "&VchNumber=" + companydata.getVoucher();
        final KProgressHUD Hhdprogress = KProgressHUD.create(BusinessdateActivity.this)
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REPORTNrc,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject obj = new JSONObject(response);

                            arraylist = new ArrayList<Businessdate>();
                            JSONArray dataArray = obj.getJSONArray("ReportCurrentYearBusiness");


                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BusinessdateActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No CurrentYearBusiness found for the selected Bill/Party")
                                        .setCancelable(false)
                                        .setTitle("CurrentYearBusiness Report")
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
                                TextView pgrv = findViewById(R.id.busi_pgr);
                                BigDecimal billamo = new BigDecimal(dataobj.getString("PGR"));
                                DecimalFormat formatter78 = new DecimalFormat("##,##,###.###");
                                String billamoun = formatter78.format(billamo);
                                pgrv.setText(billamoun);
                                TextView pestisi = findViewById(R.id.busi_pesti);
                                BigDecimal pesti = new BigDecimal(dataobj.getString("Pestisides"));
                                DecimalFormat formatter79 = new DecimalFormat("##,##,###.###");
                                String pestis = formatter79.format(pesti);
                                pestisi.setText(pestis);
                                TextView premi = findViewById(R.id.busi_premi);
                                BigDecimal p = new BigDecimal(dataobj.getString("Premium"));
                                DecimalFormat formatter80 = new DecimalFormat("##,##,###.###");
                                String prem = formatter80.format(p);
                                premi.setText(prem);
                                TextView royal = findViewById(R.id.busi_royal);
                                BigDecimal roy = new BigDecimal(dataobj.getString("Royal"));
                                DecimalFormat formatter81 = new DecimalFormat("##,##,###.###");
                                String royal1 = formatter81.format(roy);
                                royal.setText(royal1);
                                TextView classic = findViewById(R.id.busi_classi);
                                BigDecimal c = new BigDecimal(dataobj.getString("Classic"));
                                DecimalFormat formatter83 = new DecimalFormat("##,##,###.###");
                                String cla = formatter83.format(c);
                                classic.setText(cla);
                                TextView dusts = findViewById(R.id.busi_dust);
                                BigDecimal d = new BigDecimal(dataobj.getString("Dust"));
                                DecimalFormat formatter82 = new DecimalFormat("##,##,###.###");
                                String dust = formatter82.format(d);
                                dusts.setText(dust);
//                                playerModel.setTotal(dataobj.getString("BillAmount"));

                                //arraylist.add(playerModel);
                            }

                            Hhdprogress.dismiss();
                           // setupRecycler();

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
                params.put("CmpGUID", companydata.getKeyCmpnGid());
                params.put("LedgerName", companydata.getKeyPartyName());
                params.put("LedgerID", companydata.getKeyLegId());
                params.put("VchNumber", companydata.getVoucher());
                return params;
            }
    };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setupRecycler() {

        businessdateAdapter = new BusinessdateAdapter(this, arraylist);
        listView.setAdapter(businessdateAdapter);

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}