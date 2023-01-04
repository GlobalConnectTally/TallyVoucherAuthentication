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
import java.util.Map;

import tallyadmin.gp.gpcropcare.Adapter.CurrentstatusAdapter;
import tallyadmin.gp.gpcropcare.Adapter.NCRDueBillsAdapter;
import tallyadmin.gp.gpcropcare.Model.Currentstatus;
import tallyadmin.gp.gpcropcare.Model.NCRBill;
import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;
import tallyadmin.gp.gpcropcare.Volley.VolleySingleton;
import tallyadmin.gp.gpcropcare.utils.VolleyErrors;

import static tallyadmin.gp.gpcropcare.Common.Common.URL_REPORTNrc;


public class CurrentStatusActivity extends AppCompatActivity {
    ListView listView;
    public static ArrayList<Currentstatus> arraylist;
    String opening,debit,credit,closing;
    private CurrentstatusAdapter currentstatusAdapter;
    Companysave companydata;
    private VolleyErrors volleyErrors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_status);

        Toolbar toolbar = findViewById(R.id.toolbarSoD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Current Status");
        companydata = new Companysave(getApplicationContext());

        listView = (ListView)findViewById(R.id.current_list);

        TextView partyname = findViewById(R.id.curr_partyname);
        partyname.setText(companydata.getKeyPartyName());
        TextView billvalue = findViewById(R.id.curr_billvalue);
        billvalue.setText(companydata.getKeyBillAmount());
        TextView reportdate = findViewById(R.id.curr_reportdate);
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
    }
        else if (isNetworkConnected())
    {
        fetchingJSON();
    }
}



    public void fetchingJSON() {
        String urlno = "?CmpGUID=" + companydata.getKeyCmpnGid() + "&LedgerName="+companydata.getKeyPartyName()+ "&LedgerID="+companydata.getKeyLegId()+ "&VchNumber=" + companydata.getVoucher();
        final KProgressHUD Hhdprogress = KProgressHUD.create(CurrentStatusActivity.this)
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
                            JSONArray dataArray = obj.getJSONArray("ReportLedgerAccount");
                            System.out.println("salesorder:"+dataArray);

                            if (dataArray.length() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentStatusActivity.this);
                                // Set the Alert Dialog Message
                                builder.setMessage("No CurrentStatus found for the selected Bill/Party")
                                        .setCancelable(false)
                                        .setTitle("CurrentStatus Report")
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


                                Currentstatus playerModel = new Currentstatus();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                BigDecimal o = new BigDecimal(dataobj.getString("Opening"));
                                DecimalFormat formatter90 = new DecimalFormat("##,##,###.###");
                                String open = formatter90.format(o);
                                playerModel.setOpening(open);
                                BigDecimal d = new BigDecimal(dataobj.getString("Debit"));
                                DecimalFormat formatter91 = new DecimalFormat("##,##,###.###");
                                String deb = formatter91.format(d);
                                playerModel.setDebit(deb);
                                BigDecimal c = new BigDecimal(dataobj.getString("Credit"));
                                DecimalFormat formatter92 = new DecimalFormat("##,##,###.###");
                                String cre = formatter92.format(c);
                                playerModel.setCredit(cre);
                                BigDecimal cl = new BigDecimal(dataobj.getString("Closing"));
                                DecimalFormat formatter93 = new DecimalFormat("##,##,###.###");
                                String clo = formatter93.format(cl);
                                playerModel.setClosing(clo);

                                arraylist.add(playerModel);
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



        currentstatusAdapter = new CurrentstatusAdapter(this,arraylist);
        listView.setAdapter(currentstatusAdapter);

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}