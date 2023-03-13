package tallyadmin.gp.gpcropcare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
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
import java.util.Map;

import tallyadmin.gp.gpcropcare.Adapter.BusinessdateAdapter;
import tallyadmin.gp.gpcropcare.Adapter.LastBusinessAdapter;
import tallyadmin.gp.gpcropcare.Model.Businessdate;
import tallyadmin.gp.gpcropcare.Model.LastBusiness;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import tallyadmin.gp.gpcropcare.utils.VolleyErrors;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_REPORTNrc;

public class LastYearbusinessActivity extends AppCompatActivity {

    ListView listView;
    public static ArrayList<LastBusiness> arraylist;
    String pgr, royal, premium, pestisides, classic, dust, total;
    private LastBusinessAdapter lastBusinessAdapter;
    Companysave companydata;
    private VolleyErrors volleyErrors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_yearbusiness);


        Toolbar toolbar = findViewById(R.id.toolbarSoD);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            getSupportActionBar().setTitle("Last Year Business");
        }



       // listView = (ListView) findViewById(R.id.ncrlist);




        companydata = new Companysave(getApplicationContext());
        TextView partyname = findViewById(R.id.value_partyname);
        partyname.setText(companydata.getKeyPartyName());
        TextView billvalue = findViewById(R.id.bill_value);
        billvalue.setText(companydata.getKeyBillAmount());
        TextView reportdate = findViewById(R.id.report_date);
        reportdate.setText(companydata.getKeyVocherdate());

        volleyErrors = new VolleyErrors(this);

       // listView = (ListView) findViewById(R.id.ncrlist);
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
        final KProgressHUD Hhdprogress = KProgressHUD.create(LastYearbusinessActivity.this)
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

                            arraylist = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("ReportLastYearBusiness");


                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LastYearbusinessActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No LastYearBusiness found for the selected Bill/Party")
                                        .setCancelable(false)
                                        .setTitle("LastYearBusiness Report")
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
                                TextView pgra = findViewById(R.id.pgr_bs);
                                BigDecimal pg = new BigDecimal(dataobj.getString("PGR"));
                                DecimalFormat formatter82 = new DecimalFormat("##,##,###.###");
                                String pgr = formatter82.format(pg);
                                pgra.setText(pgr);
                                TextView pestis = findViewById(R.id.pesti_bs);
                                BigDecimal pes = new BigDecimal(dataobj.getString("Pestisides"));
                                DecimalFormat formatter85 = new DecimalFormat("##,##,###.###");
                                String pesti = formatter85.format(pes);
                                pestis.setText(pesti);
                                TextView premi = findViewById(R.id.premium_bs);
                                BigDecimal pre = new BigDecimal(dataobj.getString("Premium"));
                                DecimalFormat formatter86 = new DecimalFormat("##,##,###.###");
                                String prem = formatter86.format(pre);
                                premi.setText(prem);
                                TextView roya = findViewById(R.id.royal_bs);
                                BigDecimal roy = new BigDecimal(dataobj.getString("Royal"));
                                DecimalFormat formatter87 = new DecimalFormat("##,##,###.###");
                                String royal = formatter87.format(roy);
                                roya.setText(royal);
                                TextView classi = findViewById(R.id.classic_bs);
                                BigDecimal cla = new BigDecimal(dataobj.getString("Classic"));
                                DecimalFormat formatter88 = new DecimalFormat("##,##,###.###");
                                String clas = formatter88.format(cla);
                                classi.setText(clas);
                                TextView dustsw = findViewById(R.id.dust_bs);
                                BigDecimal dus = new BigDecimal(dataobj.getString("Dust"));
                                DecimalFormat formatter89 = new DecimalFormat("##,##,###.###");
                                String dust = formatter89.format(dus);
                                dustsw.setText(dust);
//                                playerModel.setTotal(dataobj.getString("BillAmount"));

                                //arraylist.add(playerModel);
                            }

                            Hhdprogress.dismiss();
                            //setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                volleyErrors.exceptionMessage(error).toString(),
                                Toast.LENGTH_SHORT).show();                      }
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

        lastBusinessAdapter = new LastBusinessAdapter(this, arraylist);
        listView.setAdapter(lastBusinessAdapter);
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
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

}
