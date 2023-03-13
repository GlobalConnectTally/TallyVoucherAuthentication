package tallyadmin.gp.gpcropcare.Activities;

import androidx.appcompat.app.ActionBar;
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
import java.util.Map;

import tallyadmin.gp.gpcropcare.Adapter.NCRDueBillsAdapter;
import tallyadmin.gp.gpcropcare.Model.NCRBill;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import tallyadmin.gp.gpcropcare.utils.VolleyErrors;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_REPORTNrc;

//----limitactivity----//
public class LimitActivity extends AppCompatActivity {
    Companysave companydata;
    private VolleyErrors volleyErrors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit);

        Toolbar toolbar = findViewById(R.id.toolbarSoD);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            getSupportActionBar().setTitle("Limit");
        }



        companydata = new Companysave(getApplicationContext());

        TextView partyname = findViewById(R.id.lim_partyname);
        partyname.setText(companydata.getKeyPartyName());
        TextView billvalue = findViewById(R.id.lim_billvalue);
        billvalue.setText(companydata.getKeyBillAmount());
        TextView reportdate = findViewById(R.id.lim_reportdate);
        reportdate.setText(companydata.getKeyVocherdate());

        volleyErrors = new VolleyErrors(this);

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
        final KProgressHUD Hhdprogress = KProgressHUD.create(LimitActivity.this)
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


                            JSONArray dataArray = obj.getJSONArray("ReportLimit");
                            System.out.println("jison12:"+dataArray);

                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LimitActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No Limit found for the selected Bill/Party")
                                        .setCancelable(false)
                                        .setTitle("Limit Report")
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
                                TextView pgrl = findViewById(R.id.pgrt);
                                BigDecimal cl = new BigDecimal(dataobj.getString("PGRTotalLimit"));
                                DecimalFormat formatter93 = new DecimalFormat("##,##,###.###");
                                String clo = formatter93.format(cl);
                                pgrl.setText(clo);
                                TextView pgrU = findViewById(R.id.pgru);
                                BigDecimal c2 = new BigDecimal(dataobj.getString("PGRuses"));
                                DecimalFormat formatter94 = new DecimalFormat("##,##,###.###");
                                String cl2 = formatter94.format(c2);
                                pgrU.setText(cl2);
                                TextView pgra = findViewById(R.id.pgra);
                                BigDecimal c3 = new BigDecimal(dataobj.getString("PGRavilabel"));
                                DecimalFormat formatter95 = new DecimalFormat("##,##,###.###");
                                String cl3 = formatter95.format(c3);
                                pgra.setText(cl3);

                                TextView pst1 = findViewById(R.id.psdli);
                                BigDecimal c4 = new BigDecimal(dataobj.getString("PesticidesTotalLimit"));
                                DecimalFormat formatter96 = new DecimalFormat("##,##,###.###");
                                String cl4 = formatter96.format(c4);
                                pst1.setText(cl4);
                                TextView pst2 = findViewById(R.id.psdu);
                                BigDecimal c5 = new BigDecimal(dataobj.getString("Pesticidesuses"));
                                DecimalFormat formatter97 = new DecimalFormat("##,##,###.###");
                                String cl5 = formatter97.format(c5);
                                pst2.setText(cl5);
                                TextView pst3 = findViewById(R.id.psda);
                                BigDecimal c6 = new BigDecimal(dataobj.getString("Pesticidesavilabel"));
                                DecimalFormat formatter98 = new DecimalFormat("##,##,###.###");
                                String cl6 = formatter98.format(c6);
                                pst3.setText(cl6);

                                TextView ryl1 = findViewById(R.id.ryll);
                                BigDecimal c7 = new BigDecimal(dataobj.getString("RoyalTotalLimit"));
                                DecimalFormat formatter99 = new DecimalFormat("##,##,###.###");
                                String cl7 = formatter99.format(c7);
                                ryl1.setText(cl7);
                                TextView ryl2 = findViewById(R.id.rylu);
                                BigDecimal c8 = new BigDecimal(dataobj.getString("Royaluses"));
                                DecimalFormat formatter100 = new DecimalFormat("##,##,###.###");
                                String cl8 = formatter100.format(c8);
                                ryl2.setText(cl8);
                                TextView ryl3 = findViewById(R.id.rylav);
                                BigDecimal c9 = new BigDecimal(dataobj.getString("Royalavilabel"));
                                DecimalFormat formatter110 = new DecimalFormat("##,##,###.###");
                                String cl9 = formatter110.format(c9);
                                ryl3.setText(cl9);

                                TextView prm1 = findViewById(R.id.prml);
                                BigDecimal c10 = new BigDecimal(dataobj.getString("PrimiumTotalLimit"));
                                DecimalFormat formatter111 = new DecimalFormat("##,##,###.###");
                                String cl10 = formatter111.format(c10);
                                prm1.setText(cl10);
                                TextView prm2 = findViewById(R.id.prmu);
                                BigDecimal c11 = new BigDecimal(dataobj.getString("Primiumuses"));
                                DecimalFormat formatter112 = new DecimalFormat("##,##,###.###");
                                String cl11 = formatter112.format(c11);
                                prm2.setText(cl11);
                                TextView prm3 = findViewById(R.id.prma);
                                BigDecimal c12 = new BigDecimal(dataobj.getString("Primiumavilabel"));
                                DecimalFormat formatter113 = new DecimalFormat("##,##,###.###");
                                String cl12 = formatter113.format(c12);
                                prm3.setText(cl12);

                                TextView clsc1 = findViewById(R.id.clsl);
                                BigDecimal c14 = new BigDecimal(dataobj.getString("ClassicTotalLimit"));
                                DecimalFormat formatter114 = new DecimalFormat("##,##,###.###");
                                String cl14 = formatter114.format(c14);
                                clsc1.setText(cl14);
                                TextView clsc2 = findViewById(R.id.clsu);
                                BigDecimal c15 = new BigDecimal(dataobj.getString("Classicuses"));
                                DecimalFormat formatter115 = new DecimalFormat("##,##,###.###");
                                String cl15 = formatter115.format(c15);
                                clsc2.setText(cl15);
                                TextView clsc3 = findViewById(R.id.clsav);
                                BigDecimal c16 = new BigDecimal(dataobj.getString("Classicavilabel"));
                                DecimalFormat formatter116 = new DecimalFormat("##,##,###.###");
                                String cl16 = formatter116.format(c16);
                                clsc3.setText(cl16);

                                TextView dst1 = findViewById(R.id.dstl);
                                BigDecimal c17 = new BigDecimal(dataobj.getString("DustTotalLimit"));
                                DecimalFormat formatter117 = new DecimalFormat("##,##,###.###");
                                String cl17 = formatter117.format(c17);
                                dst1.setText(cl17);
                                TextView dst2 = findViewById(R.id.dstu);
                                BigDecimal c18 = new BigDecimal(dataobj.getString("Dustuses"));
                                DecimalFormat formatter118 = new DecimalFormat("##,##,###.###");
                                String cl18 = formatter118.format(c18);
                                dst2.setText(cl18);
                                TextView dst3 = findViewById(R.id.dstav);
                                BigDecimal c19 = new BigDecimal(dataobj.getString("Dustavilabel"));
                                DecimalFormat formatter119 = new DecimalFormat("##,##,###.###");
                                String cl19 = formatter119.format(c19);
                                dst3.setText(cl19);

                            }

                            Hhdprogress.dismiss();


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




    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}